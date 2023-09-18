package run.fleek.application.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.application.certification.dto.RekognitionResponseDto;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.CompareFacesRequest;
import software.amazon.awssdk.services.rekognition.model.CompareFacesResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.S3Object;

@Component
@RequiredArgsConstructor
public class AwsRekognitionWrapper {

  private static final String bucketName = "trace-static";

  private final RekognitionClient rekognitionClient;

  public RekognitionResponseDto compareFaces(String sourceImageKey, String targetImageKey) {

    S3Object sourceS3Object = S3Object.builder()
      .bucket(bucketName)
      .name(sourceImageKey)
      .build();

    S3Object targetS3Object = S3Object.builder()
      .bucket(bucketName)
      .name(targetImageKey)
      .build();

    Image sourceImage = Image.builder().s3Object(sourceS3Object).build();
    Image targetImage = Image.builder().s3Object(targetS3Object).build();


    CompareFacesRequest compareFacesRequest = CompareFacesRequest.builder()
      .sourceImage(sourceImage)
      .targetImage(targetImage)
      .build();

    CompareFacesResponse response = rekognitionClient.compareFaces(compareFacesRequest);

    return RekognitionResponseDto.from(response);
  }
}
