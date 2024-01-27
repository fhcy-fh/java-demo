package top.fhcy.utils;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinIOUtils {

    private static String url = "http://localhost:9000";
    private static String accessKey = "OCOkpklSNKoQNgNpgBmq";
    private static String secret = "gxULIcRzGtB20rRH4EBlbWzqxSHJGbnLZnQSBflY";
    private static String bucket = "test";

    public static void upload(MultipartFile file, String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, secret)
                    .build();

            // 判断bucket是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                // bucket不存在则创建
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            // Object大小不能超过5 GB
            // 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }

    public static ResponseEntity<InputStreamResource> download(String fileName) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secret)
                .build();

        // 2. 获取对象的InputStream,并保存为文件
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(fileName).build());
            // 构建 InputStreamResource
            assert inputStream != null;
            InputStreamResource resource = new InputStreamResource(inputStream);
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 构建 ResponseEntity 返回资源和头部信息
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
