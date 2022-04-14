package JAVA_0414;

class MyException extends Exception{
  MyException(String s) {
    super(s);
  }
}

public class MyExceptionTest {
  public static void main(String[] args) {
    try {
      MyException me = new MyException("내가 만든 에러입니다");
      throw me;
    } catch (MyException me) {
      System.out.println("에러 메시지: " + me.getMessage());
      me.printStackTrace();
    } finally {
      System.out.println("프로그램이 종료되었습니다.");
    }
  }
}
