package com.lannstark.lec15;

import java.util.HashMap;
import java.util.Map;

public class Lec15Main {

  public static void main(String[] args) {
    Map<Integer, String> map = new HashMap<>();
    for (Map.Entry<Integer, String> entry : map.entrySet()) {
      System.out.println(entry.getKey());
      System.out.println(entry.getValue());
    }

  }

}
