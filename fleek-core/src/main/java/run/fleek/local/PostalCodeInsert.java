package run.fleek.local;

import com.google.api.client.util.Lists;
import com.google.api.client.util.Sets;
import org.postgresql.util.PSQLException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Set;

public class PostalCodeInsert {


  private static final String DB_URL = "jdbc:postgresql://localhost:5432/fleek?reWriteBatchedInserts=true";
  private static final String USER = "cherrypop";
  private static final String PASS = "13o1714QO^WXep@A";
  private static final String INSERT_SQL =
    "insert into postal_code_meta (postal_code, sido, sido_eng, sigungu, sigungu_eng, eupmyeon, eupmyeon_eng, road_code, road_name, road_name_eng, underground_yn, building_main_no, building_sub_no, building_manage_no, delivery_name, sigungu_building_name, legal_dong_code, legal_dong_name, ri_name, administrative_dong_name, mountain_yn, jibun_main_no, eupmyeon_dong_serial_no, jibun_sub_no, old_postal_code, postal_code_serial_no) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"; // Add all columns


  public static void main(String[] args) {
    Set<String> alreadyInserted = Sets.newHashSet();
    List<String[]> datas = Lists.newArrayList();

    try (BufferedReader br = new BufferedReader(new FileReader("/Users/chance/Downloads/zipcode_DB/충청북도.txt"));

         Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {

      String line;
      boolean isFirstLine = true;

      while ((line = br.readLine()) != null) {
        if (isFirstLine) { // Skip header line
          isFirstLine = false;
          continue;
        }

        String[] data = line.split("\\|", -1);
        if (alreadyInserted.contains(data[0])) {
          continue;
        }
        datas.add(data);
        alreadyInserted.add(data[0]);
      }

      for (String[] data : datas) {
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_SQL)) {
          for (int i = 0; i < data.length; i++) {
            pstmt.setString(i + 1, data[i]);
          }

          try {
            pstmt.executeUpdate();
          } catch (PSQLException e) {
            if (e.getMessage().contains("duplicate")) {
              System.out.println("Duplicate postal code: " + data[0]);
            } else {
              System.out.println("Error: " + data[0]);
              throw e;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}



