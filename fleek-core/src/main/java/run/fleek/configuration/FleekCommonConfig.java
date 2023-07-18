package run.fleek.configuration;

import run.fleek.configuration.database.RelationalDatabaseConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"run.fleek"})
@EntityScan(basePackages = {"run.fleek"})
@Import({RelationalDatabaseConfig.class})
public class FleekCommonConfig {

}
