package cn.com.dhcc.platform.filestorage.processor;

import cn.com.dhcc.credit.platform.util.RedissonUtil;
import cn.com.dhcc.platform.filestorage.info.*;
import cn.com.dhcc.platform.util.DateUtils;
import cn.com.dhcc.platform.util.KeyProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class AmazonFileStorageProcessor extends FileStorageProcessor {
    private static final Logger log = LoggerFactory.getLogger(AmazonFileStorageProcessor.class);
    static final String ERR_CODE_QCFQ07 = "QCFQ07";
    static final String ERR_MSG_QCFQ07 = "文件路径参数为空";
    private static final String FILE_PATH_SPLIT;
    private static final String ENCODEING = "UTF-8";
    private static final String FILE_DIFF_SPLIT = ".";
    private static RedissonClient redisson;
    private static final String FILE_STOR_FLAG = "FILE_STOR_FLAG";
    private static FileStorageProcessor process;
    ThreadPoolExecutor executor;
    private HelpCrcbAmazonConfig config = null;

    public FileStorageProcessor getFileStorageProcessor() {
        return process;
    }

    public AmazonFileStorageProcessor() {
        this.executor = new ThreadPoolExecutor(16, 16, 30L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque());
        this.register();

    }
    public HelpCrcbAmazonClient getAmazonClient(){
        if(config==null){
            HelpCrcbAmazonConfig config = new HelpCrcbAmazonConfig();
            String accessKey = KeyProperties.getProperty("amazonaws.accessKey","config.properties");
            String secretKey = KeyProperties.getProperty("amazonaws.secretKey","config.properties");
            String url = KeyProperties.getProperty("amazonaws.url","config.properties");
            String bucketName = KeyProperties.getProperty("amazonaws.bucketName","config.properties");
            config.setAccessKey(accessKey);
            config.setSecretKey(secretKey);
            config.setUrl(url);
            config.setBucketName(bucketName);
        }
		log.info("HelpCrcbAmazonClient config=[{}]",config);
        HelpCrcbAmazonClient hcac= new HelpCrcbAmazonClient(config);
		log.info("HelpCrcbAmazonClient=[{}]!!!!",config);
       return hcac;
    }
    private static String buildFileUri(StorageRequest request,String fileName) {
        String uri = request.getUrl();
        String fullFileURI = null;
        if (StringUtils.isEmpty(uri)) {
            String rootUri = request.getRootUri();
            String bussModelEN = request.getBussModelEN();
            String sourceSys = request.getSourceSystem();
            String fileSuffix = request.getFileType();
            String middleFilePath = DateUtils.getCurrentDay();
            fullFileURI = rootUri + FILE_PATH_SPLIT + sourceSys + FILE_PATH_SPLIT + bussModelEN + FILE_PATH_SPLIT
                    + middleFilePath + FILE_PATH_SPLIT + fileName + FILE_DIFF_SPLIT + fileSuffix;
        }else {
        	return uri;
        }
        return fullFileURI;
    }


    @Override
    public StorageResponse saveFile(StorageRequest request) {
        log.info("saveFile method begin ,param StorageRequest = {}", request);
        StorageResponse response = StorageResponse.sucessStorageResponse();
        response.setSerialNumber(request.getSerialNumber());
        String storageFileName = UUID.randomUUID().toString();
        String url  = buildFileUri(request,storageFileName);
        try {
            RSet<Object> fileStorFlag = redisson.getSet(FILE_STOR_FLAG);
            fileStorFlag.add(url);
            AmazonFileStorageProcessor.WriteFileTask writeFileTask = new AmazonFileStorageProcessor.WriteFileTask(request, url);
            this.executor.execute(writeFileTask);
        } catch (Exception var7) {
            log.error("保存文件异常! 文件的全路径为[{}]。异常信息={}", url, var7);
            response.setErrorCode("QCFQ04");
            response.setErrorMsg("保存文件异常! 文件的全路径为[{}]。异常信息={}");
            return response;
        }

        response.setUri(url);
        String fileName = request.getFileName();
        if (!StringUtils.isBlank(fileName)) {
            response.setFileName(fileName);
        } else {
            response.setFileName(storageFileName);
        }

        log.info("saveFile method end ,param StorageRequest = {},return = {}", request, response);
        return response;
    }

    private void saveFileContentStr(StorageRequest request, String url) throws Exception {
        String content = request.getContent();
        byte[] contentBytes = request.getContentBytes();
        if (null != content) {
            this.writeFilesToAmazon(request, url, content);
        } else if (null != contentBytes && contentBytes.length > 0) {
            this.writeBytesToAmazon(request, url, contentBytes);
        } else {
            log.error("saveFile error !!!  the content is null! , request={}", request);
        }

    }

    private void writeBytesToAmazon(StorageRequest request, String url, byte[] arr) throws IOException {
//        String compressEncrypt = CommonProperties.getCommonConfig().getCompressEncryptFlag();
//        if (!"1".equals(compressEncrypt)) {
//            log.debug("readFile openCompressEncrypt={}", compressEncrypt);
//            if (request.isCompression()) {
//                contentBytes = CommonsGZIPCompress.gZip(contentBytes);
//            }
//
//            if (request.isEncrypt()) {
//            }
//        }
    	if (null != arr && arr.length > 0) {
            //亚马逊云对象存储保存--
            HelpAmazonRequest har = new HelpAmazonRequest();
            har.setOriginalFileName(request.getFileName());
            har.setMime(StringUtils.lowerCase(request.getFileType()));
            har.setFileId(url);
            har.setBucketName(KeyProperties.getProperty("amazonaws.bucketName","config.properties"));
            har.setData(arr);
            HelpCrcbAmazonClient hcac=getAmazonClient();
            log.info("saveHelpAmazonRequestFile  begin  ,param HelpAmazonRequest = {}", "FileId:"+har.getFileId()+",bucketName:"+har.getBucketName()+",dataLength:"+har.getData().length+",mime:"+har.getMime());
            hcac.save(har);
        }

        //亚马逊云对象存储保存--
        //FileGoogleUtil.writeContentBytesToFile(contentBytes, url);
//        log.info("saveFileContewriteContentBytesToFilentStr writeContentBytesToFile(contentBytes, url)  ok ! contentBytes={} , url={}", contentBytes.length, url);
    }

    private void writeFilesToAmazon(StorageRequest request, String url, String content) throws Exception {
        byte[] arr = content.getBytes();
//        String compressEncrypt = CommonProperties.getCommonConfig().getCompressEncryptFlag();
//        if (!"1".equals(compressEncrypt)) {
//            log.debug("readFile openCompressEncrypt={}", compressEncrypt);
//            if (request.isCompression()) {
//                arr = CommonsGZIPCompress.gZip(arr);
//            }
//
//            if (request.isEncrypt()) {
//            }
//        }

        if (null != arr && arr.length > 0) {
            //亚马逊云对象存储保存--
            HelpAmazonRequest har = new HelpAmazonRequest();
            har.setOriginalFileName(request.getFileName());
            har.setMime(StringUtils.lowerCase(request.getFileType()));
            har.setFileId(url);
            har.setBucketName(KeyProperties.getProperty("amazonaws.bucketName","config.properties"));
            har.setData(arr);
            HelpCrcbAmazonClient hcac=getAmazonClient();
            log.info("saveHelpAmazonRequestFile  begin  ,param HelpAmazonRequest = {}", "FileId:"+har.getFileId()+",bucketName:"+har.getBucketName()+",dataLength:"+har.getData().length+",mime:"+har.getMime());
            hcac.save(har);
        }

        log.info("saveFileContentStr writeFile(content, url)  ok ! content={} , url={}", content.length(), url);
    }

    public static void main(String[] args) {
        String originalFileName = "zd6297f09-5b22-45c4-bd18-2bde1ee6c6bd.html";
        System.out.println("FILE_PATH_SPLIT = [" + FILE_PATH_SPLIT + "]");
        String FileId =originalFileName.substring(0, originalFileName.lastIndexOf("."));
        System.out.println("originalFileName = [" + originalFileName + "]");
        System.out.println("FileId = [" + StringUtils.substringBeforeLast(originalFileName,FILE_DIFF_SPLIT) + "]");

    }

    @Override
    public String getType() {
        return STORAGE_MEDIUM_TYPE_CLOUND;
    }

    @Override
    public String readFile(String url) throws Exception {
        log.info("readFile method begin ,param url = {}", url);
        String fileContent = null;
        HelpCrcbAmazonClient hcac=getAmazonClient();
        try {
            fileContent = new String(hcac.get(url));
        } catch (Exception var6) {
            Thread.sleep(100L);
            try {
                fileContent = new String(hcac.get(url));
            } catch (Exception var5) {
                log.error("读取文件异常！文件的全路径为[{}]。异常信息={}", url, var5);
                throw var5;
            }
        }

        log.info("readFile method end ,param url = {}, return = {}", url, fileContent == null ? null : fileContent.length());
        return fileContent;
    }

    @Override
    public String readFile(String url, String charset) throws Exception {
        log.info("readFile method begin ,param url = {}", url);
        String fileContent = null;
        HelpCrcbAmazonClient hcac=getAmazonClient();
        //亚马逊云对象存储下载--
        try {
            if (StringUtils.isBlank(charset)) {
                charset = ENCODEING;
            }
            fileContent = new String(hcac.get(url));
        } catch (Exception var8) {
            Thread.sleep(100L);
            try {
                //亚马逊云对象存储下载--
                fileContent = new String(hcac.get(url));
            } catch (Exception var7) {
                log.error("读取文件异常！文件的全路径为[{}]。异常信息={}", url, var7);
                throw var7;
            }
        }

        log.info("readFile method end ,param url = {}, return = {}", url, fileContent == null ? null : fileContent.length());
        return fileContent;
    }


    @Override
    public boolean deleteFile(String url) {
        log.info("deleteFile method begin ,param url = {}", url);
        boolean flag = false;

        try {
            FileUtils.deleteDirectory(new File(url));
            flag = true;
        } catch (IOException var4) {
            log.error("删除文件异常! 文件的全路径为[{}]。异常信息={}", url, var4);
        }

        log.info("saveFile method end ,param url = {}, return = {}", url, flag);
        return flag;
    }



    @Override
    public byte[] readFileBytes(String uri) throws Exception {
        log.info("readFile method begin ,param uri = {}", uri);
        Object var2 = null;
        byte[] fileContentBytes;
        HelpCrcbAmazonClient hcac=getAmazonClient();
        try {
            log.info("hcac.get parms fileId={}============={}",uri);
            fileContentBytes = hcac.get(uri);
        } catch (Exception var6) {
            Thread.sleep(100L);
            try {
                fileContentBytes = hcac.get(uri);
            } catch (Exception var5) {
                log.error("读取文件异常！文件的全路径为[{}]。异常信息={}", uri, var5);
                throw var5;
            }
        }

        log.info("readFileBytes method end ,param url = {}, return = {}", uri, fileContentBytes == null ? null : fileContentBytes.length);
        return fileContentBytes;
    }

    @Override
    public FileReadResponse readFile(FileReadRequest request) throws Exception {
    	log.info("AmazonFileStorageProcessor readFile start ={}"+request);
        FileReadResponse response = FileReadResponse.getSuccessReadResponse();
        try {
			if (null != request) {
			    String fileUri = request.getUri();
			    if (StringUtils.isBlank(fileUri)) {
			        response.setErrorCode(ERR_CODE_QCFQ07);
			        response.setErrorMsg(ERR_MSG_QCFQ07);
			        return response;
			    }

			    RSet<Object> fileStorFlag = redisson.getSet("FILE_STOR_FLAG");
			    boolean contains = fileStorFlag.contains(fileUri);
			    String property = "50";
				log.info("AmazonFileStorageProcessor property start ={}"+property);
				log.info("AmazonFileStorageProcessor contains1 start ={}"+contains);
			    int retryCount = 5;
			    if (StringUtils.isNotBlank(property)) {
			        retryCount = Integer.parseInt(property);
			        if (retryCount <= 0) {
			            retryCount = 5;
			        }
			    }

			    while (contains) {
			    	log.info("AmazonFileStorageProcessor contains start ={}"+contains);
			        if (0 >= retryCount) {
			            response.setErrorCode("QCFQ12");
			            response.setErrorMsg("读取文件时，该文件尚未写入，请稍后重试!");
			            return response;
			        }

			        Thread.sleep(100L);
			        fileStorFlag = redisson.getSet("FILE_STOR_FLAG");
			        contains = fileStorFlag.contains(fileUri);
			        --retryCount;
			    }
			    System.out.println("contains++++++++++"+contains);
			    System.out.println("fileUri++++++++++"+fileUri);
			    log.info("readFile contains={}", contains);
			    this.readFile(request, response, fileUri);
			    response = this.repeatRead(request, response);
			}
		} catch (Exception e) {
			log.info("AmazonFileStorageProcessor error ={}"+e);
			e.printStackTrace();
		}

        return response;
    }

    private FileReadResponse repeatRead(FileReadRequest request, FileReadResponse response) {
        log.info("repeatRead start...");

        try {
            String reqResultType = request.getResultType();
            log.info("repeatRead reqResultType={}", reqResultType);

            for (int i = 0; i < 100; ++i) {
                if ("0".equals(reqResultType)) {
                    byte[] contentBytes = response.getContentBytes();
                    if (null != contentBytes && contentBytes.length != 0) {
                        break;
                    }

                    Thread.sleep(100L);
                    this.readFile(request, response, request.getUri());
                } else if ("1".equals(reqResultType)) {
                    String content = response.getContent();
                    if (!StringUtils.isBlank(content)) {
                        break;
                    }

                    Thread.sleep(100L);
                    this.readFile(request, response, request.getUri());
                }
            }
        } catch (Exception var6) {
            log.error("repeatRead error e=", var6);
        }

        log.info("repeatRead end response={}", response);
        return response;
    }

    private void readFile(FileReadRequest request, FileReadResponse response, String fileUri) throws Exception {
    	log.info("FileReadRequest request ResultType ={}",request.getResultType());
    	log.info("FileReadRequest request SerialNumber={}",request.getSerialNumber());
        String reqResultType = request.getResultType();
        String serialNumber = request.getSerialNumber();
        if (!StringUtils.isBlank(serialNumber)) {
            response.setSerialNumber(serialNumber);
        }

//        String compressEncrypt = CommonProperties.getCommonConfig().getCompressEncryptFlag();
        byte[] contentBytes;
//        boolean compress;
        if ("0".equals(reqResultType)) {
            contentBytes = this.readFileBytes(fileUri);
//            if (!"1".equals(compressEncrypt)) {
//                log.debug("readFile openCompressEncrypt={}", compressEncrypt);
//                if (request.isDecrypt()) {
//                }
//
//                compress = request.isZipFlag();
//                if (compress) {
//                    contentBytes = CommonsGZIPCompress.unGZip(contentBytes);
//                }
//            }

            response.setContentBytes(contentBytes);
        } else if ("1".equals(reqResultType)) {
            contentBytes = this.readFileBytes(fileUri);
//            if (!"1".equals(compressEncrypt)) {
//                log.debug("readFile openCompressEncrypt={}", compressEncrypt);
//                if (request.isDecrypt()) {
//                }
//
//                compress = request.isZipFlag();
//                if (compress) {
//                    contentBytes = CommonsGZIPCompress.unGZip(contentBytes);
//                }
//            }

            response.setContent(new String(contentBytes));
        } else {
            response.setErrorCode("QCFQ08");
            response.setErrorCode("文件读取请求中的返回类型错误，非String 和Byte[] 这两种。");
            log.error("readFile failed ! errCode={},errMsg={},serialNumber={}", new Object[]{"QCFQ08", "文件读取请求中的返回类型错误，非String 和Byte[] 这两种。", serialNumber});
        }

    }

    static {
        FILE_PATH_SPLIT = File.separator;
        redisson = RedissonUtil.getLocalRedisson();
        process = new AmazonFileStorageProcessor();
    }

    class WriteFileTask extends Thread {
        private StorageRequest request;
        private String url;

            public WriteFileTask(StorageRequest request, String url) {
            this.request = request;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                AmazonFileStorageProcessor.this.saveFileContentStr(this.request, this.url);
                RSet<Object> fileStorFlag = AmazonFileStorageProcessor.redisson.getSet("FILE_STOR_FLAG");
                fileStorFlag.remove(this.url);
            } catch (Exception var2) {
                AmazonFileStorageProcessor.log.error("保存文件异常! 文件的全路径为[{}]。异常信息={}", this.url, var2);
            }

        }
    }
}
