package run.fleek.api.controller.file;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import run.fleek.application.file.S3Application;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
  private final S3Application s3Application;

  @PostMapping("/file/upload")
  public List<String> uploadFiles(@RequestParam List<MultipartFile> files) {
    return s3Application.uploadFiles(files);
  }

}
