package run.fleek.application.post.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddDto {

  private String content;
  private String profileName;
}
