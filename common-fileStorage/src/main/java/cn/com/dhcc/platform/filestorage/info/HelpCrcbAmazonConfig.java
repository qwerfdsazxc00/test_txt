package cn.com.dhcc.platform.filestorage.info;

/**
 * 常熟农商行OSS文件存储配置
 *
 * @author: YuBin-002726
 * @Date: 2019/4/29 15:21
 */
public class HelpCrcbAmazonConfig {

    /**
     * 农商行S3服务器地址
     */
    private String url;

    /**
     * S3服务访问密钥
     */
    private String accessKey;

    /**
     * S3服务加密密钥
     */
    private String secretKey;

    /**
     * 存储桶名称(生产环境由S3运维人员创建)
     */
    private String bucketName;

    /**
     * 连接池大小
     */
    private int poolSize = 10;

    /**
     * 连接超时时间(毫秒)
     */
    private int connectionTimeout = 2000;

    /**
     * 请求超时时间(毫秒)
     */
    private int requestTimeout = 30000;

    /**
     * Socket超时时间(毫秒)
     */
    private int socketTimeout = 50000;

    /**
     * 连接TTL,在连接池中的连接空闲当前时间后将主动释放
     */
    private int connectionTtl = 120000;

    /**
     * 是否启用(此配置只在配置文件中生效)
     */
    private boolean enable = true;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectionTtl() {
        return connectionTtl;
    }

    public void setConnectionTtl(int connectionTtl) {
        this.connectionTtl = connectionTtl;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
