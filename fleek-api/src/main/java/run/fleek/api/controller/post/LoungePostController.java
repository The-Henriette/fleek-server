package run.fleek.api.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.post.LoungePostApplication;
import run.fleek.application.post.dto.*;
import run.fleek.common.response.FleekGeneralResponse;
import run.fleek.enums.LoungeTopic;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoungePostController {

  private final LoungePostApplication loungePostApplication;

  @GetMapping("/lounge/topics")
  public List<LoungeTopicDto> getLoungeTopics() {
    return LoungeTopicDto.from(Arrays.asList(LoungeTopic.values()));
  }

  @PostMapping("/lounge/post")
  public void addLoungePost(@RequestBody LoungePostAddDto loungePostAddDto) {
    loungePostApplication.addLoungePost(loungePostAddDto);
  }

  @GetMapping("/lounge/posts")
  public LoungePostPageDto pageLoungePosts(@RequestParam Integer size, @RequestParam Integer page,
                                           @RequestParam(required = false) String profileName, @RequestParam(required = false) String topicCode) {
    return loungePostApplication.pageLoungePosts(size, page, profileName, topicCode);
  }

  @PostMapping("/lounge/post/{postId}")
  public LoungePostDto viewLoungePost(@PathVariable Long postId, @RequestParam(required = false) String profileName) {
    return loungePostApplication.viewLoungePost(postId, profileName);
  }

  @PostMapping("/lounge/post/{postId}/comment")
  public void addComment(@PathVariable Long postId, @RequestBody CommentAddDto addDto) {
    loungePostApplication.addComment(postId, addDto);
  }

  @GetMapping("/lounge/post/{postId}/comment")
  public PostCommentPageDto pagePostComments(@PathVariable Long postId, @RequestParam Integer page, @RequestParam Integer size) {
    return loungePostApplication.pagePostComments(postId, page, size);
  }

  @PostMapping("/lounge/post/{postId}/like")
  public FleekGeneralResponse likePost(@PathVariable Long postId, @RequestParam String profileName) {
    return loungePostApplication.likePost(postId, profileName);
  }

  @PostMapping("/lounge/comment/{commentId}/like")
  public FleekGeneralResponse likeComment(@PathVariable Long commentId, @RequestParam String profileName) {
    return loungePostApplication.likeComment(commentId, profileName);
  }

}


