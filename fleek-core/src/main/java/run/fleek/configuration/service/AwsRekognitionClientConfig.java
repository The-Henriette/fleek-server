package run.fleek.configuration.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

@Configuration
@RequiredArgsConstructor
public class AwsRekognitionClientConfig {

  @Value("${aws.accessKey:}")
  private String ACCESS_KEY;

  @Value("${aws.secretKey:}")
  private String SECRET_KEY;

  @Bean
  public RekognitionClient rekognitionClient() {
    return RekognitionClient.builder()
      .region(Region.AP_NORTHEAST_2)
      .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
      .build();
  }
}
