package com.studydashboard.api.global.config.aws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;


@Configuration
public class SecretManagerConfig {
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.of(region))
                .build();
    }

    public Map<String, String> getSecret(String secretName) {
        SecretsManagerClient client = secretsManagerClient();

        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = client.getSecretValue(request);
        String secretString = response.secretString();
        System.out.println(response.secretString());
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(secretString, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Unable to parse secret string", e);
        }
    }

}
