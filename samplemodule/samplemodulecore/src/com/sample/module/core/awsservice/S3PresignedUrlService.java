package com.sample.module.core.awsservice;

import java.time.Duration;

public interface S3PresignedUrlService {
    String generatePresignedUrl(String bucketName, String objectKey, Duration expiry);
}
