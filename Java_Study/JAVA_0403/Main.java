package JAVA_0403;

class Person {
  String name;
  int age;
  String gender;
  String national = "KOR";
  Person(String name, int age, String gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }
  void hello() {
    System.out.println("안녕하세요, 저는 " + name +"이고, 나이는 " + age +"입니다.");
  }
}

class Student extends Person {
  String major;
  String national = "USA";
  Student(String name, int age, String gender, String major) {
    super(name, age, gender);
    this.major = major;
  }
  @Override
  void hello() {
    System.out.println("안녕하세요, 저는 " + name +"이고, 나이는 " + age + "이며," + major + " 전공 학생입니다.");
  }
}

public class Main {
  public static void main(String[] args) {
    Person p = new Person("SIWON", 30, "남성");
    p.hello();

    Person p2 = new Student("SIWON-PARK", 28, "남성", "경영학");
    p2.hello(); // 자식이 Override한 hello()가 실행됨(동적 바인딩)
    System.out.println(p2.national); // USA가 아니라 부모에 있는 KOR이 출력됨
  }
}
