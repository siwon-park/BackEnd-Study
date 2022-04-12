package JAVA_0412;

interface Animal {
  String getName();
  String getFood();
  void eatFood(String food);
}

class ZooKeeper {
  void feedAnimal(Animal animal) {
    System.out.println(animal.getName()+ "에게 " + animal.getFood() + "를 먹입니다.");
  }
}

class Tiger implements Animal {
  String name = "호랑이";
  public String getName(){
    return this.name;
  }
  public String getFood() {
    return "Meat";
  }
  public void eatFood(String food) {
    System.out.println("저는 호랑이입니다. " + food + "를 좋아합니다.");
  }
}

class Penguin implements Animal {
  String name = "펭귄";
  public String getName(){
    return this.name;
  }
  public String getFood() {
    return "Fish";
  }
  public void eatFood(String food) {
    System.out.println("저는 펭귄입니다. " + food + "를 좋아합니다.");
  }
}

public class Zoo {
  public static void main(String[] args) {
    Tiger t = new Tiger();
    Penguin p = new Penguin();
    ZooKeeper zoo = new ZooKeeper();
    zoo.feedAnimal(p);
    zoo.feedAnimal(t);
  }
}
