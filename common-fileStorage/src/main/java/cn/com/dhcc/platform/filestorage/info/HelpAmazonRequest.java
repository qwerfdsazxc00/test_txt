package cn.com.dhcc.platform.filestorage.info;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * 常熟农商行OSS请求信息
 *
 * @author: YuBin-002726
 * @Date: 2019/8/9 15:00
 */
public class HelpAmazonRequest {

    /**
     * 文件Bucket
     * 空则使用默认
     */
    private String bucketName;

    /**
     * 文件编号
     * 空则自动生成
     */
    private String fileId;

    /**
     * 原始文件�?
     * 空则使用默认
     */
    private String originalFileName;

    /**
     * 文件MIME
     * 空则根据文件名自动识�?
     */
    private String mime;

    /**
     * 文件输入�?
     */
    private InputStream inputStream;

    /**
     * 文件数据
     */
    private byte[] data;

    /**
     * 文件对象
     */
    private File file;

    /**
     * 自定义元数据
     */
    private Map<String, String> metadata;

    public HelpAmazonRequest withBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public HelpAmazonRequest withFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public HelpAmazonRequest withOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
        return this;
    }

    public HelpAmazonRequest withMime(String mime) {
        this.mime = mime;
        return this;
    }

    public HelpAmazonRequest withData(byte[] data) {
        if (inputStream != null || file != null) {
            throw new IllegalArgumentException("只允许同时提供文件流/文件对象/文件数据之一");
        }
        this.data = data;
        return this;
    }

    public HelpAmazonRequest withFile(File file) {
        if (inputStream != null || data != null) {
            throw new IllegalArgumentException("只允许同时提供文件流/文件对象/文件数据之一");
        }
        this.file = file;
        return this;
    }

    public HelpAmazonRequest withInputStream(InputStream inputStream) {
        if (data != null || file != null) {
            throw new IllegalArgumentException("只允许同时提供文件流/文件对象/文件数据之一");
        }
        this.inputStream = inputStream;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        if (data != null || file != null) {
            throw new IllegalArgumentException("只允许同时提供文件流/文件对象/文件数据之一");
        }
        this.inputStream = inputStream;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        if (inputStream != null || file != null) {
            throw new IllegalArgumentException("只允许同时提供文件流/文件对象/文件数据之一");
        }
        this.data = data;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        if (inputStream != null || data != null) {
            throw new IllegalArgumentException("只允许同时提供文件流/文件对象/文件数据之一");
        }
        this.file = file;
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

    public void withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
