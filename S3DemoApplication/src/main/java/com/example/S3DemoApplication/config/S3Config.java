package com.example.S3DemoApplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${cloud.aws.region.static}")
    private String region;
    
    @Bean("s3Client")
    @Profile("local")
    public S3Client s3client(@Value("${cloud.aws.credentials.access-key}") String accesskey,
                             @Value("${cloud.aws.credentials.secret-key}") String secretkey){
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accesskey, secretkey);
        return  S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider
                        .create(awsBasicCredentials))
                .build();

    }

    @Bean("s3Client")
    @Profile("dev")
    public S3Client s3clientDev(){
        return  S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

    }

}
