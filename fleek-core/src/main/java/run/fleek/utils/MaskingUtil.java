package run.fleek.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MaskingUtil {
  private static final String ASTERISK = "*";
  private static final String ASTERISK_4 = "****";
  private static final char ASTERISK_VAL = '*';

  /**
   * 가운데 이름을 마스킹한다.
   * ex)
   * 홍 => 홍
   * 홍길 => 홍*
   * 홍길동 => 홍*동
   * 홍길동홍 => 홍**동
   * */
  public static String name(String name) {
    if (name == null || name.length() == 1) {
      return name;
    } else if (name.length() == 2) {
      return name.charAt(0) + ASTERISK;
    } else if (name.length() > 2) {

      String first = name.substring(0, 1);
      String last = name.substring(name.length()-1);

      char[] c = new char[name.length() - 2];
      Arrays.fill(c, ASTERISK_VAL);

      return first + String.valueOf(c) + last;
    } else {
      return "";
    }
  }

  public static List<String> nameList(List<String> nameList) {
    return nameList.stream()
      .map(MaskingUtil::name)
      .collect(Collectors.toList());
  }

  /**
   * 전화번호 뒷자리를 마스킹한다.
   * ex) 0000 -> 0000
   * 01012341234 -> 0101234****
   * 010-1234-1234 -> 010-1234-****
   * */
  public static String phoneNumber(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.length()<5) {
      return phoneNumber;
    }
    return phoneNumber.substring(0, phoneNumber.length()-4) + ASTERISK_4;
  }
}
