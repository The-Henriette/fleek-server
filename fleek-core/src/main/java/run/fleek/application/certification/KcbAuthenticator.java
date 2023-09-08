package run.fleek.application.certification;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kcb.module.v3.OkCert;
import kcb.module.v3.exception.OkCertException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import run.fleek.application.certification.dto.KcbCertRequestDto;
import run.fleek.utils.JsonUtil;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class KcbAuthenticator {

  private final ResourceLoader resourceLoader;

  private final String B2BCode = "V63470000000";
  private final String environment = "PROD";

  private OkCert okCert;
  private InputStream license;
  private String licensePath = "classpath:cert/V63470000000_IDS_01_PROD_AES_license.dat";

  @PostConstruct
  public void init() throws OkCertException, IOException {
    okCert = new OkCert();
//    okCert.setProtocol2type("22");

    Resource resource = resourceLoader.getResource(licensePath);
    license = resource.getInputStream();
  }

  public String addCertRequest(KcbCertRequestDto kcbCertRequestDto) {
    try {
//      String json = JsonUtil.write(kcbCertRequestDto);

//      JsonParser parser = new JsonParser();
      String resultString = okCert.callOkCert(environment, B2BCode, "IDS_HS_EMBED_SMS_REQ", licensePath, JsonUtil.write(kcbCertRequestDto), license);
      byte[] jsonBytes = resultString.getBytes(StandardCharsets.UTF_8);
      ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonBytes);
//
//      JsonObject resultJson = (JsonObject) parser.parse(inputStream.toString());
//
//      return JsonUtil.write(resultJson);
      return resultString;
    } catch (OkCertException e) {
      System.out.println(e.getStackTrace());
      throw new RuntimeException(e);
    }
  }
}
