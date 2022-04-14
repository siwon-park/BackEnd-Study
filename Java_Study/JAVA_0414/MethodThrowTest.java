package JAVA_0414;
import java.io.*;

public class MethodThrowTest {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String file_name = br.readLine();
    try {
        File f = createFile(file_name);
        System.out.println(f + "파일이 성공적으로 생성되었습니다."); 
      } catch (Exception e) {
        System.out.println(e.getMessage() + " 다시 입력 해주시길 바랍니다.");
      }
    }
  // 메서드 예외 선언 => throws를 했으므로 해당 메서드를 호출하는 곳에서는 try/catch구문으로 잡아줘야한다.
  static File createFile(String file_name) throws Exception {
    if (file_name == null || file_name.equals("")) {
      Exception e = new Exception("파일 이름이 유효하지 않습니다.");
      throw e;
    }
    File f = new File(file_name);
    f.createNewFile();
    return f;
  }
}

