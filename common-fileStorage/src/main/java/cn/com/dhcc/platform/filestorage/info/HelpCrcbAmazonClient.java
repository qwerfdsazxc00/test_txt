package cn.com.dhcc.platform.filestorage.info;

import cn.com.dhcc.platform.filestorage.exception.AamazonReadNotFoundException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.*;
//import com.help.config.HelpCrcbAmazonConfig;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
public class HelpCrcbAmazonClient {

    private static final String FILE_NAME_META_KEY = "file-name";

    public static Logger logger = LoggerFactory.getLogger(HelpCrcbAmazonClient.class);

    private HelpCrcbAmazonConfig config;

    public AmazonS3 amazonS3;

    public HelpCrcbAmazonClient(HelpCrcbAmazonConfig config) {

        this.config = config;
        String accessKey = config.getAccessKey();
        String secretKey = config.getSecretKey();

        String bucketName = config.getBucketName();
        //System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withClientConfiguration(new ClientConfiguration()
                        .withProtocol(Protocol.HTTP)
                        .withConnectionTimeout(2000)
                        .withRequestTimeout(30000)
                        .withSocketTimeout(config.getSocketTimeout())
                        .withSignerOverride("S3SignerType")
                        .withUseExpectContinue(true)
                        .withMaxConnections(config.getPoolSize())
                        .withConnectionTTL(config.getConnectionTtl())
                )
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(config.getUrl(), ""))
                .withPathStyleAccessEnabled(true)
                .build();

        boolean b = this.amazonS3.doesBucketExistV2(bucketName);
        if (!b) {
            logger.warn("OSS对象存储Bucket[{}]不存在?", config.getBucketName());
           this.amazonS3.createBucket(bucketName);
        }
    }

    /**
     * @param fileId Amazon S3 文件对应的fileId
     * @return 文件mime
     */
    public String getMime(String fileId) {
        return getMime(fileId, config.getBucketName());
    }

    /**
     * 获取文件MIME
     *
     * @param fileId     Amazon S3 文件对应的fileId
     * @param bucketName 文件�?在Bucket
     * @return
     */
    public String getMime(String fileId, String bucketName) {
        boolean b = judgeKey(fileId, bucketName);
        if (!b) {
            throw new AamazonReadNotFoundException("文件服务器无文件");
        }
        GetObjectMetadataRequest request = new GetObjectMetadataRequest(bucketName, fileId);
        return amazonS3.getObjectMetadata(request).getContentType();
    }

    /**
     * @param file             上传文件的字节数�?
     * @param originalFileName 上传文件的文件名
     * @return 文件ID
     */
    public String save(byte[] file, String originalFileName) {
        return save(new HelpAmazonRequest().withData(file).withOriginalFileName(originalFileName));
    }

    /**
     * @param is               上传文件的对应文件流
     * @param originalFileName 上传文件的文件名
     * @return 文件ID
     */
    public String save(InputStream is, String originalFileName) {
        return save(new HelpAmazonRequest().withInputStream(is).withOriginalFileName(originalFileName));
    }

    /**
     * @param file 文件
     * @return 文件编号
     */
    public String save(File file) {
        return save(new HelpAmazonRequest().withFile(file));
    }

    /**
     * 文件保存
     *
     * @param request
     * @return
     */
    public String save(HelpAmazonRequest request) {
        if (StringUtil.isEmpty(request.getFileId())) {
            request.setFileId(UUID.randomUUID().toString().replace("-", ""));
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        if (StringUtil.isNotEmpty(request.getOriginalFileName())) {
            objectMetadata.addUserMetadata(FILE_NAME_META_KEY, request.getOriginalFileName());
        }
        if (request.getMetadata() != null) {
            for (String k : request.getMetadata().keySet()) {
                objectMetadata.addUserMetadata(k, request.getMetadata().get(k));
            }
        }
        if (StringUtil.isEmpty(request.getMime())) {
            if (StringUtil.isNotEmpty(request.getOriginalFileName())) {
                objectMetadata.setContentType(Mimetypes.getInstance().getMimetype(request.getOriginalFileName()));
            } else {
                objectMetadata.setContentType("application/octet-stream");
            }
        } else {
            objectMetadata.setContentType(request.getMime());
        }
        String id = request.getFileId();
        if (StringUtil.isEmpty(id)) {
            id = UUID.randomUUID().toString().replace("-", "");
        }
        String bucketName = request.getBucketName();
        if (StringUtil.isEmpty(bucketName)) {
            bucketName = config.getBucketName();
        }
        if (request.getData() != null) {
            amazonS3.putObject(bucketName, id, new ByteArrayInputStream(request.getData()), objectMetadata);
        } else if (request.getInputStream() != null) {
            try {
                amazonS3.putObject(bucketName, id, request.getInputStream(), objectMetadata);
                request.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                try {
                    if(null != request.getInputStream()){
                        request.getInputStream().close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (request.getFile() != null) {
            if (StringUtil.isNotEmpty(request.getMime())) {
                try (InputStream is = new FileInputStream(request.getFile())) {
                    amazonS3.putObject(bucketName, id, is, objectMetadata);
                } catch (FileNotFoundException e) {
                    throw new AamazonReadNotFoundException("文件不存在");
                } catch (IOException e) {
                    throw new AamazonReadNotFoundException("文件读取失败");
                } 
            } else {
                amazonS3.putObject(bucketName, id, request.getFile());
            }
        } else {
            throw new IllegalArgumentException("文件数据不存�?");
        }
        return id;
    }

    /**
     * @param fileId Amazon S3 文件对应的fileId
     */
    public void delete(String fileId) {
        this.delete(fileId, config.getBucketName());
    }

    public void delete(String fileId, String bucketName) {
        amazonS3.deleteObject(bucketName, fileId);
    }

    /**
     * @param fileId Amazon S3 文件对应的fileId
     * @return
     */
    public byte[] get(String fileId) {
        logger.info("hcac.get parms fileId={},bucketName={}============={}",fileId,config.getBucketName());
        return get(fileId, config.getBucketName());
    }

    public byte[] get(String fileId, String bucketName) {
        try (InputStream is = getInputStream(fileId, bucketName)) {
            return StreamUtils.copyToByteArray(is);
        } catch (IOException e) {
            throw new AamazonReadNotFoundException("文件[" + fileId + "]读取失败");
        }
    }

    /**
     * @param fileId Amazon S3 文件对应的fileId
     * @return
     */
    public InputStream getInputStream(String fileId) {
        return getInputStream(fileId, config.getBucketName());
    }

    /**
     * 获取文件对应输入�?
     *
     * @param fileId     文件ID
     * @param bucketName 文件�?在Bucket
     * @return
     */
    public InputStream getInputStream(String fileId, String bucketName) {
        boolean b = judgeKey(fileId, bucketName);
        logger.info("文件服务器是否存在文件={}",b);
        if (!b) {
            throw new AamazonReadNotFoundException("文件服务器无文件");
        }
        GetObjectRequest request = new GetObjectRequest(bucketName, fileId);
        S3Object object = amazonS3.getObject(request);
        return object.getObjectContent();
    }

    /**
     * @param fileId Amazon S3 文件对应的fileId
     * @return
     */
    public long getSize(String fileId) {
        return getSize(fileId, config.getBucketName());
    }

    /**
     * 获取文件大小
     *
     * @param fileId     文件ID
     * @param bucketName 文件�?在Bucket
     * @return
     */
    public long getSize(String fileId, String bucketName) {
        boolean b = judgeKey(fileId, bucketName);
        if (!b) {
            throw new AamazonReadNotFoundException( "文件服务器无文件");
        }
        GetObjectMetadataRequest request = new GetObjectMetadataRequest(bucketName, fileId);
        return amazonS3.getObjectMetadata(request).getContentLength();
    }

    /**
     * 获取原始文件�?
     *
     * @param fileId
     * @return
     */
    public String getFileName(String fileId) {
        return getFileName(fileId, config.getBucketName());
    }

    /**
     * 获取原始文件�?
     *
     * @param fileId
     * @param bucketName
     * @return
     */
    public String getFileName(String fileId, String bucketName) {
        boolean b = judgeKey(fileId, bucketName);
        if (!b) {
            throw new AamazonReadNotFoundException("文件服务器无文件");
        }
        GetObjectMetadataRequest request = new GetObjectMetadataRequest(bucketName, fileId);
        return amazonS3.getObjectMetadata(request).getUserMetaDataOf(FILE_NAME_META_KEY);
    }

    /**
     * 获取文件MIME
     *
     * @param fileId
     * @return
     */
    public HelpAmazonFileInfo getFileInfo(String fileId) {
        return getFileInfo(fileId, config.getBucketName());
    }

    /**
     * 获取文件MIME
     *
     * @param fileId
     * @param bucketName
     * @return
     */
    public HelpAmazonFileInfo getFileInfo(String fileId, String bucketName) {
        boolean b = judgeKey(fileId, bucketName);
        if (!b) {
            throw new AamazonReadNotFoundException( "文件服务器无文件");
        }
        GetObjectMetadataRequest request = new GetObjectMetadataRequest(bucketName, fileId);
        ObjectMetadata mt = amazonS3.getObjectMetadata(request);

        HelpAmazonFileInfo metadata = new HelpAmazonFileInfo();
        metadata.setFileId(fileId);
        metadata.setBucket(bucketName);
        metadata.setFileName(mt.getUserMetaDataOf(FILE_NAME_META_KEY));
        metadata.setMime(mt.getContentType());
        metadata.setSize(mt.getContentLength());

        Map<String, String> userMetadata = mt.getUserMetadata();
        if (userMetadata == null) {
            userMetadata = new HashMap<>();
        } else {
            userMetadata.remove(FILE_NAME_META_KEY);
        }
        metadata.setMetadata(userMetadata);

        return metadata;
    }

    /**
     * 获取原始AmazonS3连接客户�?
     *
     * @return
     */
    public AmazonS3 getOriginalClient() {
        return amazonS3;
    }

    /**
     * @param fileId Amazon S3 文件对应的fileId
     * @return
     */
    public boolean judgeKey(String fileId, String bucketName) {
        return amazonS3.doesObjectExist(bucketName, fileId);
    }
}
