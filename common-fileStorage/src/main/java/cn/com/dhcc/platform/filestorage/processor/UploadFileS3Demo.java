/**
 *  Copyright (c)  2018-2028 DHCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of DHCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with DHCC.
 */
package cn.com.dhcc.platform.filestorage.processor;

import java.io.File;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

/**
 * 
 * @author madrid
 * @date 2020年6月19日
 */
public class UploadFileS3Demo {

    /**
     * access_key_id 你的亚马逊S3服务器访问密钥ID
     */
    private static final String ACCESS_KEY = "xxxxx";
    /**
     * secret_key 你的亚马逊S3服务器访问密钥
     */
    private static final String SECRET_KEY = "xxxxxxxxxxxx";
    /**
     * end_point 你的亚马逊S3服务器连接路径和端口(新版本不再需要这个,直接在创建S3对象的时候根据桶名和Region自动获取)
     *
     * 格式: https://桶名.s3-你的Region名称.amazonaws.com
     * 示例: https://xxton.s3-cn-north-1.amazonaws.com
     */
//    private static final String END_POINT = "https://xxton.s3-cn-north-1.amazonaws.com";
    /**
     * bucketname 你的亚马逊S3服务器创建的桶名
     */
    private static final String BUCKET_NAME = "xxxx";

    /**
     * 创建访问凭证对象
     */
    private static final BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    /**
     * 创建s3对象
     */
    private static final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            //设置服务器所属地区
            .withRegion(Regions.CN_NORTH_1)
            .build();

    /**
     * 上传文件示例
     *
     * @param uploadPath 上传路径
     */
    private static String uploadFile(File file, String uploadPath) {
        try {
            if (file == null) {
                return null;
            }
            //设置文件目录
            if(StringUtils.isNotEmpty(uploadPath)){
                uploadPath= "/".equals(uploadPath.substring(uploadPath.length()-1))?uploadPath:uploadPath+"/";
            }else{
                uploadPath="default/";
            }
            //生成随机文件名
            String expandedName= file.getName().substring(file.getName().lastIndexOf("."));
            String key = uploadPath + UUID.randomUUID().toString() +expandedName;
            // 设置文件上传对象
            PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, key, file);
            // 设置公共读取
            request.withCannedAcl(CannedAccessControlList.PublicRead);
            // 上传文件
            PutObjectResult putObjectResult = s3.putObject(request);
            if (null != putObjectResult) {
                return key;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件下载路径
     * @param key 文件的key
     * @return
     */
    private static String downloadFile(String key){
        try {
            if(StringUtils.isEmpty(key)){
                return null;
            }
            GeneratePresignedUrlRequest httpRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, key);
            return s3.generatePresignedUrl(httpRequest).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args)
    {
        //上传文件测试
        File file = new File("D:\\\\document\\API.CHM");
        String filePath = uploadFile(file,  "upload/");
        System.out.println("【文件上传结果】:"+filePath);
        System.out.println("\n");
        //下载文件测试
        String downUrl = downloadFile(filePath);
        System.out.println("【下载文件路径】:"+downUrl);
    }

}
