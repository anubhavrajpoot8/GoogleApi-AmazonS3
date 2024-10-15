package com.assignment.FreightFox.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public List<String> searchFiles(String userName, String searchTerm) {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(userName + "/")
                .build();

        ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);

        return listObjectsResponse.contents().stream()
                .map(S3Object::key)
                .filter(key -> key.toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    public byte[] downloadFile(String userName, String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(userName + "/" + fileName)
                .build();

        ResponseInputStream<GetObjectResponse> obj = s3Client.getObject(getObjectRequest);
        try {
            return obj.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error downloading file", e);
        }
    }

    public void uploadFile(String userName, MultipartFile file) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(userName + "/" + file.getOriginalFilename())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }
}