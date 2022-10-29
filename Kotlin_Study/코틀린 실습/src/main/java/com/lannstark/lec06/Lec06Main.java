package com.lannstark.lec06;

import java.util.Arrays;
import java.util.List;

public class Lec06Main {

  public static void main(String[] args) {

    for (int i = 1; i <= 5; i += 2) {
      System.out.println(i);
    }

    for (int i = 1; i <= 3; i ++) {
      System.out.println(i);
    }

    for (int i=5; i>=1; i--) {
      System.out.println(i);
    }



    int i = 1;
    while (i <= 3) {
      System.out.println(i);
      i++;
    }

    List<Long> numbers = Arrays.asList(1L, 2L, 3L);
    for (long number: numbers) {
      System.out.println(number);
      System.out.println("====");
    }


  }

}
