package JAVA_0401;

class CarTest {
  String name;
  private int speed; // 외부에서 함부로 speed에 접근해서 값을 바꿀 수 없음

  public void speedUp() {
    if (speed + 10 < 250) {
      speed += 10;
    }
  }
  // getter(접근자)
  public int getSpeed() { // speed가 private이니 getSpeed를 통해서 speed에 접근함
    return speed;
  }
  // setter(설정자)
  public void setSpeed(int speed) {
    if (speed >= 0 && speed <=250) {
      this.speed = speed;
    } else if (speed > 250) {
      this.speed = 250;
    }
  }
}

public class Main2 {
  public static void main(String[] args) {
    CarTest car = new CarTest();
    // car.speed = 300;
    car.name = "porche";
    car.setSpeed(300);
    System.out.println(car.name+" "+car.getSpeed()+"km/h");
  }
}
