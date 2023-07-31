package run.fleek.utils;

import java.util.Random;

public final class RandomUtil {
  public static String generateRandomSixDigitNumber() {
    Random random = new Random();
    int min = 100_000; // Minimum 6-digit number (100000)
    int max = 999_999; // Maximum 6-digit number (999999)
    int randomNumber = random.nextInt(max - min + 1) + min;

    return String.format("%06d", randomNumber);
  }

}
