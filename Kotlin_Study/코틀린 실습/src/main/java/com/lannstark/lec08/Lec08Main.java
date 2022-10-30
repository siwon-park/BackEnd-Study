package com.lannstark.lec08;

public class Lec08Main {

  public static void main(String[] args) {
    printAll("A", "B", "C");

    String[] arr = new String[]{"D", "E", "F"};
    printAll(arr);
  }

  public static void printAll(String... strings) {
    for (String str : strings) {
      System.out.println(str);
    }
  }

}
