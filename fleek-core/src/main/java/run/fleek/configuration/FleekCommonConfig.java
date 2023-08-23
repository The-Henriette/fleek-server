package run.fleek.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import run.fleek.configuration.database.RelationalDatabaseConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"run.fleek"})
@EntityScan(basePackages = {"run.fleek"})
@Import({RelationalDatabaseConfig.class})
@EnableAsync(proxyTargetClass = true)
public class FleekCommonConfig {

  @Value("${fleek.service.common.server.thread.core-pool-size:}")
  private Integer CORE_POOL_SIZE;

  @Value("${fleek.service.common.server.thread.max-pool-size:}")
  private Integer MAX_POOL_SIZE;

  @Value("${fleek.service.common.server.thread.queue-capacity:}")
  private Integer QUEUE_CAPACITY;

  @Resource
  private PlatformTransactionManager transactionManager;

  @Bean
  public PlatformTransactionManager chainedTransactionManager() {
    return new ChainedTransactionManager(transactionManager);
  }

  @Bean(name = "defaultTaskExecutor")
  public Executor defaultTaskExecutor() {

    log.info("DefaultTaskExecutor CORE_POOL_SIZE: {}", CORE_POOL_SIZE);
    log.info("DefaultTaskExecutor MAX_POOL_SIZE: {}", MAX_POOL_SIZE);
    log.info("DefaultTaskExecutor QUEUE_CAPACITY: {}", QUEUE_CAPACITY);

    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
    taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
    taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
    taskExecutor.setThreadNamePrefix("DefaultTaskExecutor-");
    taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
    taskExecutor.initialize();
    return taskExecutor;
  }
}
