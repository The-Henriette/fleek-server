package house.trace.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import house.trace.common.exception.TraceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Util {

  private final AmazonS3 amazonS3;

  private static File loadFile(MultipartFile file) {
    try {

      System.out.println("File size: " + file.getSize());

      String extension = '.' + FilenameUtils.getExtension(file.getOriginalFilename());
      File targetFile = File.createTempFile(UUID.randomUUID().toString(), extension);
      file.transferTo(targetFile);

      if (targetFile.length() == 0) {
        throw new TraceException("Failed to upload file");
      }

      System.out.println("File size: " + targetFile.length());




      return targetFile;
    } catch (IOException e) {
      throw new TraceException("Failed to upload file");
    }
  }

  public List<String> uploadFiles(String bucketName, String key, List<MultipartFile> files) {
      List<File> uploadTargets = files.stream()
        .map(S3Util::loadFile)
        .collect(Collectors.toList());

      return uploadTargets.stream()
        .map(target -> uploadFileToS3(bucketName, JoinUtil.SLASH_JOINER.join(key, target.getName()), target))
        .collect(Collectors.toList());
  }

  private String uploadFileToS3(String bucketName, String key, File file) {
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
    amazonS3.putObject(putObjectRequest);

    if (!file.delete()) {
      log.warn("Failed to delete S3 temporary file");
    }
    return key;
  }
}
