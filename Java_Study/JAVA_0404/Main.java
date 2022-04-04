package JAVA_0404;

class MySingleton {
  private static MySingleton my_singleton;

  private MySingleton() {
  }

  public static MySingleton getSingleton() {
    if (my_singleton == null) {
      my_singleton = new MySingleton();
    }
    return my_singleton;
  }
}

public class Main {
  public static void main(String[] args) {
    // MySingleton singleton = new MySingleton(); new키워드로 생성 불가. 생성자가 private이기 때문
    MySingleton singleton1 = MySingleton.getSingleton();
    MySingleton singleton2 = MySingleton.getSingleton();
    System.out.println(singleton1 == singleton2);
  }  
}
