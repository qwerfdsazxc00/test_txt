package cn.com.dhcc.platform.filestorage.info;

import java.util.Map;

/**
 * 亚马逊S3文件信息
 * @author: YuBin-002726
 * @Date: 2019/10/10 9:00
 */
public class HelpAmazonFileInfo {

    /**
     * 文件编号
     */
    private String fileId;

    /**
     * 文件Bucket
     */
    private String bucket;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件�?
     */
    private String fileName;

    /**
     * 文件MIME
     */
    private String mime;

    /**
     * 自定义元数据
     */
    private Map<String, String> metadata;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
