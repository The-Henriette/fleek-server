package run.fleek.application.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import run.fleek.utils.S3Util;

import java.util.List;

@Component
@RequiredArgsConstructor
public class S3Application {
  private final S3Util s3Util;

  private static final String TRACE_S3_BUCKET_NAME = "trace-static";

  public List<String> uploadFiles(List<MultipartFile> files) {
    return s3Util.uploadFiles(TRACE_S3_BUCKET_NAME, "fleek", files);
  }

}
