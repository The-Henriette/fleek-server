package run.fleek.api.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.post.LoungePostApplication;
import run.fleek.application.post.dto.*;
import run.fleek.common.response.FleekGeneralResponse;
import run.fleek.domain.lounge.LoungePost;
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
  public LoungePostDto addLoungePost(@RequestBody LoungePostAddDto loungePostAddDto) {
    return loungePostApplication.addLoungePost(loungePostAddDto);
  }

  @PutMapping("/lounge/post/{postId}")
  public LoungePostDto editLoungePost(@PathVariable Long postId, @RequestBody LoungePostAddDto loungePostAddDto) {
    return loungePostApplication.editLoungePost(postId, loungePostAddDto);
  }

  @GetMapping("/lounge/posts")
  public LoungePostPageDto pageLoungePosts(@RequestParam Integer size, @RequestParam Integer page,
                                           @RequestParam(required = false) String profileName,
                                           @RequestParam(required = false) String topicCode,
                                           @RequestParam(required = false) String writerName,
                                           @RequestParam(required = false) String keyword) {
    return loungePostApplication.pageLoungePosts(size, page, profileName, topicCode, writerName, keyword);
  }

  @PostMapping("/lounge/post/{postId}")
  public LoungePostDto viewLoungePost(@PathVariable Long postId, @RequestParam(required = false) String profileName) {
    return loungePostApplication.viewLoungePost(postId, profileName);
  }

  @PostMapping("/lounge/post/{postId}/comment")
  public PostCommentDto addComment(@PathVariable Long postId, @RequestBody CommentAddDto addDto) {
    return loungePostApplication.addComment(postId, addDto);
  }

  @PutMapping("/lounge/comment/{commentId}")
  public PostCommentDto editComment(@PathVariable Long commentId, @RequestBody CommentAddDto addDto) {
    return loungePostApplication.editComment(commentId, addDto);
  }

  @GetMapping("/lounge/post/{postId}/comment")
  public PostCommentPageDto pagePostComments(@PathVariable Long postId, @RequestParam Integer page, @RequestParam Integer size, @RequestParam(required = false) String profileName, @RequestParam(required = false) Long parentCommentId) {
    return loungePostApplication.pagePostComments(postId, page, size, profileName, parentCommentId);
  }

  @PostMapping("/lounge/post/{postId}/like")
  public FleekGeneralResponse likePost(@PathVariable Long postId, @RequestParam String profileName) {
    return loungePostApplication.likePost(postId, profileName);
  }

  @PostMapping("/lounge/comment/{commentId}/like")
  public FleekGeneralResponse likeComment(@PathVariable Long commentId, @RequestParam String profileName) {
    return loungePostApplication.likeComment(commentId, profileName);
  }

  @DeleteMapping("/lounge/post/{postId}")
  public FleekGeneralResponse deletePost(@PathVariable Long postId) {
    return loungePostApplication.deletePost(postId);
  }

  @DeleteMapping("/lounge/comment/{commentId}")
  public FleekGeneralResponse deleteComment(@PathVariable Long commentId) {
    return loungePostApplication.deleteComment(commentId);
  }

}


