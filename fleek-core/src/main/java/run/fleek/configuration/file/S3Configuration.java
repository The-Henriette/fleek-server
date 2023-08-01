package run.fleek.configuration.file;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class S3Configuration {
  @Value("${aws.accessKey:}")
  private String ACCESS_KEY;

  @Value("${aws.secretKey:}")
  private String SECRET_KEY;

  @Bean
  public AmazonS3 amazonS3() {
    if (StringUtils.isBlank(ACCESS_KEY) || StringUtils.isBlank(SECRET_KEY)) {
      return AmazonS3ClientBuilder.standard()
        .withRegion(Region.AP_Seoul.toString())
        .build();
    }

    return AmazonS3ClientBuilder.standard()
      .withRegion(Region.AP_Seoul.toString())
      .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
      .build();
  }
}
