package run.fleek.configuration.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.configuration.auth.vo.FleekPrincipalVo;
import run.fleek.domain.users.FleekUser;
import run.fleek.utils.TimeUtil;

import java.security.Key;
import java.util.Date;

import static run.fleek.common.constants.Constants.Auth.ACCESS_TOKEN_TTL_MILLISECOND;
import static run.fleek.common.constants.Constants.Auth.REFRESH_TOKEN_TTL_MILLISECOND;


@Slf4j
@Component
public class FleekTokenProvider {

  private static final String GRANT_TYPE = "Bearer";
  private final Key key;

  public FleekTokenProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public TokenDto generateTokenDto(FleekUser fleekUser) {
    long accessTokenExpiresAt = TimeUtil.getCurrentTimeMillisUtc() + ACCESS_TOKEN_TTL_MILLISECOND;

    String accessToken = Jwts.builder().setSubject(fleekUser.getFleekUserId().toString())
      .claim("id", fleekUser.getFleekUserId().toString())
      .claim("auth", "[]")
      .setExpiration(new Date(TimeUtil.getCurrentTimeMillisUtc() + ACCESS_TOKEN_TTL_MILLISECOND)).signWith(this.key, SignatureAlgorithm.HS512).compact();

    long refreshTokenExpiresAt = TimeUtil.getCurrentTimeMillisUtc() + REFRESH_TOKEN_TTL_MILLISECOND;

    String refreshToken = Jwts.builder().setSubject(fleekUser.getFleekUserId().toString())
      .claim("id", fleekUser.getFleekUserId().toString())
      .claim("auth", "[]")
      .setExpiration(new Date(refreshTokenExpiresAt)).signWith(this.key, SignatureAlgorithm.HS512).compact();

    return TokenDto.builder()
      .grantType(GRANT_TYPE)
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .accessTokenExpiresAt(accessTokenExpiresAt)
      .refreshTokenExpiresAt(refreshTokenExpiresAt)
      .build();
  }

  public TokenDto generateDevTokenDto(FleekUser fleekUser) {
    String accessToken = Jwts.builder().setSubject(fleekUser.getFleekUserId().toString())
      .claim("id", fleekUser.getFleekUserId().toString())
      .claim("auth", "[]")
      .setExpiration(new Date(TimeUtil.getCurrentTimeMillisUtc() + REFRESH_TOKEN_TTL_MILLISECOND)).signWith(this.key, SignatureAlgorithm.HS512).compact();

    long refreshTokenExpiresAt = TimeUtil.getCurrentTimeMillisUtc() + REFRESH_TOKEN_TTL_MILLISECOND;

    String refreshToken = Jwts.builder().setSubject(fleekUser.getFleekUserId().toString())
      .claim("id", fleekUser.getFleekUserId().toString())
      .claim("auth", "[]")
      .setExpiration(new Date(refreshTokenExpiresAt)).signWith(this.key, SignatureAlgorithm.HS512).compact();

    return TokenDto.builder()
      .grantType(GRANT_TYPE)
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .refreshTokenExpiresAt(refreshTokenExpiresAt)
      .build();
  }

  public String validateToken(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().get("id").toString();
    } catch (MalformedJwtException | SecurityException var3) {
      log.error("wrong JWT signature.");
    } catch (ExpiredJwtException var4) {
      log.error("expired JWT token.");
    } catch (UnsupportedJwtException var5) {
      log.error("unsupported JWT.");
    } catch (IllegalArgumentException var6) {
      log.error("wrong JWT token.");
    }

    return null;
  }

  public Long getId(String token) {
    return Long.parseLong(Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().get("id").toString());
  }

  private Claims parseClaims(String accessToken) {
    try {
      return (Claims)Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException var3) {
      return var3.getClaims();
    }
  }


}
