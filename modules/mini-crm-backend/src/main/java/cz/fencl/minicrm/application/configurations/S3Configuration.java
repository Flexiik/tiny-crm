package cz.fencl.minicrm.application.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cz.fencl.minicrm.application.utils.Constants;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Configuration {
	
	@Bean
    S3Client getClient() {
        return S3Client.builder()
                .region(Constants.REGION)
                .build();
    }
}
