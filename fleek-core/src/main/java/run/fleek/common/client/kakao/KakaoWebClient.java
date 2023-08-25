package run.fleek.common.client.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import run.fleek.common.client.FleekWebClient;
import run.fleek.common.client.kakao.dto.KakaoAuthTokenDto;
import run.fleek.common.client.kakao.dto.KakaoIdTokenDto;
import run.fleek.utils.JsonUtil;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoWebClient {

  private final FleekWebClient fleekWebClient;
  private final RestTemplate restTemplate;

  @Value("${external.token.kakao}")
  private String kakaoRestToken;

  @Value("${redirect.kakao}")
  private String redirectUrl;


  public KakaoIdTokenDto getAuthToken(String code) {
    String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";


    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("grant_type", "authorization_code");
    requestBody.add("client_id", kakaoRestToken);
    requestBody.add("redirect_uri", redirectUrl);
    requestBody.add("code", code);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity formEntity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<KakaoAuthTokenDto> response = restTemplate.postForEntity(kakaoTokenUrl, formEntity, KakaoAuthTokenDto.class);

    String encodedIdToken = response.getBody().getIdToken();
    String payload = encodedIdToken.split("\\.")[1];
    Base64.Decoder decoder = Base64.getDecoder();
    String decodedIdToken = new String(decoder.decode(payload), java.nio.charset.StandardCharsets.UTF_8);

    KakaoIdTokenDto kakaoIdTokenDto = JsonUtil.read(decodedIdToken, KakaoIdTokenDto.class);
    return kakaoIdTokenDto;
  }
}
