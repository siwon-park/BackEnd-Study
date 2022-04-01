package JAVA_0401;

class Car {
  int speed;
  void speedUp() {
    if (speed + 10 < 250) {
      speed += 10;
    }
  }
  int getSpeed() {
    return speed;
  }
}

public class Main {
  public static void main(String[] args) {
    Car car = new Car();
    car.speedUp();
    System.out.println(car.speed); // 10
    car.speed = 300;
    System.out.println(car.speed); // 300
    // speed가 250이 넘으면 안되는 데도 불구하고 접근하여 변경 가능함
  }
}