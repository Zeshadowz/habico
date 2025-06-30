package com.liberis.habico;

import io.findify.s3mock.S3Mock;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

public class S3TestExecutionListener implements TestExecutionListener {

    private S3Mock s3Mock;
    private S3Client s3Client;

    @Override
    public void beforeTestClass(TestContext testContext) {
        s3Mock = new S3Mock.Builder()
                .withPort(8001)
                .withInMemoryBackend()
                .build();
        s3Mock.start();

        s3Client = (S3Client) testContext.getApplicationContext().getBean("s3Client");
        s3Client.createBucket(CreateBucketRequest.builder().bucket("test-bucket").build());
    }

    @Override
    public void afterTestMethod(@NonNull TestContext testContext) {
        ListObjectsV2Response listObjects = s3Client.listObjectsV2(ListObjectsV2Request.builder()
                .bucket("test-bucket")
                .build());

        for (S3Object s3Object : listObjects.contents()) {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket("test-bucket")
                    .key(s3Object.key())
                    .build());
        }
    }

    @Override
    public void afterTestClass(@NonNull TestContext testContext) {
        if (s3Mock != null) {
            s3Mock.stop();
        }
    }
}
