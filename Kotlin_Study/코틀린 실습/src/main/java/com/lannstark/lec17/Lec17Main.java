package com.lannstark.lec17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lec17Main {
  static List<Fruit> fruits = Arrays.asList(
          new Fruit("사과", 1_000),
          new Fruit("사과", 1_200),
          new Fruit("사과", 1_200),
          new Fruit("사과", 1_500),
          new Fruit("바나나", 3_000),
          new Fruit("바나나", 3_200),
          new Fruit("바나나", 2_500),
          new Fruit("수박", 10_000)
  );


  public static void main(String[] args) {
    String targetFruitName = "바나나";
    targetFruitName = "수박";
    // 자바에서는 람다에서 사용 가능한 변수에 제약이 있음 => final인 변수만 가능
//    filterFruitsV2(fruits, (fruit) -> targetFruitName.equals(fruit.getName()));
  }

  private List<Fruit> filterFruits(List<Fruit> fruits, Predicate<Fruit> fruitFilter) {
    List<Fruit> results = new ArrayList<>();
    for (Fruit fruit : fruits) {
      if (fruitFilter.test(fruit)) {
        results.add(fruit);
      }
    }
    return results;
  }

  List<Fruit> ret = filterFruits(fruits, fruit -> fruit.getName().equals("사과"));

  // 스트림
  private static List<Fruit> filterFruitsV2(List<Fruit> fruits, Predicate<Fruit> fruitFilter) {
    return fruits.stream()
            .filter(fruitFilter)
            .collect(Collectors.toList());
  }

  List<Fruit> ret2 = filterFruitsV2(fruits, Fruit::isApple); // 함수를 직접 넘겨주는 것이 아닌 넘겨주는 것처럼 보이는 것!




}
