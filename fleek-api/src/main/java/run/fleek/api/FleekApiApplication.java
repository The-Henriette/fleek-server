package run.fleek.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import run.fleek.configuration.FleekCommonConfig;

@SpringBootApplication
@Import({FleekCommonConfig.class})
public class FleekApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(FleekApiApplication.class, args);
  }
}
