package run.fleek.application.certification.dto;

import lombok.*;
import software.amazon.awssdk.services.rekognition.model.CompareFacesMatch;
import software.amazon.awssdk.services.rekognition.model.CompareFacesResponse;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RekognitionResponseDto {
  private Double similarity;
  private Boolean samePerson;

  public static RekognitionResponseDto from(CompareFacesResponse response) {
    List<CompareFacesMatch> faceMatches = response.faceMatches();
    if (faceMatches.isEmpty()) {
      return RekognitionResponseDto.builder()
        .similarity(0.0)
        .samePerson(false)
        .build();
    }

    return RekognitionResponseDto.builder()
      .similarity(response.faceMatches().get(0).similarity().doubleValue())
      .samePerson(response.faceMatches().get(0).similarity().doubleValue() > 0.9)
      .build();
  }
}
