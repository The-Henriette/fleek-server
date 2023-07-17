package run.fleek.api;

import house.trace.configuration.TraceCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({FleekCommonConfig.class})
public class FleekApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(FleekApiApplication.class, args);
  }
}
