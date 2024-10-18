package cz.fencl.minicrm.application.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class S3Service {
    @Autowired
	private S3Client s3Client;

    String bucketName = "mini-crm";

    public void saveData(String path, String data) {
        s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(path).build(), RequestBody.fromBytes(data.getBytes(StandardCharsets.UTF_8)));
    }

    public void saveMultipleData(Map<String, String> data) {
        for (String path : data.keySet()) {
            saveData(path, data.get(path));
        }
    }

    @SneakyThrows
    public String getData(String path) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .build();
        return new String(s3Client.getObject(getObjectRequest).readAllBytes(), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public Map<String, String> getMultipleData(List<String> paths) {
        Map<String, String> objects = new HashMap<>();

        for (String path : paths) {
            objects.put(path, getData(path));
        }

        return objects;
    }

    @SneakyThrows
    public void deleteData(String path) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    @SneakyThrows
    public void listObjects(String path) {
        ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .bucket(bucketName)
                .build();

        ListObjectsResponse res = s3Client.listObjects(listObjects);
        List<S3Object> objects = res.contents();
        for (S3Object myValue : objects) {
            if (myValue.key().startsWith(path)) {
                System.out.print("\n The name of the key is " + myValue.key());
                System.out.print("\n The object is " + (myValue.size()/1024) + " KBs");
                System.out.print("\n The owner is " + myValue.owner());
            }
        }
        System.out.println("\n\n");
    }
}
