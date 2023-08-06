package run.fleek.application.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentPageDto {
  private Integer page;
  private Long totalSize;

  private List<PostCommentDto> comments;
}
