package com.yangxcc.gulimall.product;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.yangxcc.gulimall.product.entity.SkuInfoEntity;
import com.yangxcc.gulimall.product.service.SkuInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    SkuInfoService skuInfoService;

    @Test
    void contextLoads() {
        SkuInfoEntity entity = new SkuInfoEntity();
        entity.setPrice(new BigDecimal("12.0"));
        skuInfoService.save(entity);
        System.out.println("插入成功");
    }

    @Test
    void aliyunOOS() {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        // TODO: 可以通过创建一个类，使用@Value注解，将accessKeyId和accessKeySecret写入到nacos配置文件中然后读取
        String accessKeyId = "LTAI5tBVvHABVHfi5m98ifsB";
        String accessKeySecret = "ieXn2ejl6QIbQx3r9QzdXUYGMhCe7n";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "yangxcc-gulimall";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = "school.jpg";

        String filePath = "E:\\Desktop\\1111.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = new FileInputStream(filePath);
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("file not found:" + e.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
