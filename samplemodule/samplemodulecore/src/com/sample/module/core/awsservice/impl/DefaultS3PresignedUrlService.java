package com.sample.module.core.awsservice.impl;

import com.sample.module.core.awsservice.S3PresignedUrlService;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;

public class DefaultS3PresignedUrlService implements S3PresignedUrlService {

    private String awsAccessKey;
    private String awsSecretKey;
    private Region awsRegion;

    @Override
    public String generatePresignedUrl(String bucketName, String objectKey, Duration expiry) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(awsAccessKey, awsSecretKey);

        S3Presigner presigner = S3Presigner.builder()
                .region(awsRegion)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(expiry)
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest signedRequest = presigner.presignGetObject(presignRequest);

        return signedRequest.url().toString();
    }

    // Setters for spring bean
    public void setAwsAccessKey(String awsAccessKey) { this.awsAccessKey = awsAccessKey; }
    public void setAwsSecretKey(String awsSecretKey) { this.awsSecretKey = awsSecretKey; }
    public void setAwsRegion(String region) { this.awsRegion = Region.of(region); }
}
