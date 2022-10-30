package com.lannstark.lec07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JavaFilePrinter {

  public void readFile(String path) throws IOException { // try with resources 구문
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      System.out.println(reader.readLine());
    }
  }

  public void readTextFile() throws IOException {
    File currentFile = new File("."); // 현재 디렉토리
    File file = new File(currentFile.getAbsolutePath() + "/a.txt");
    BufferedReader br = new BufferedReader(new FileReader(file));
    System.out.println(br.readLine());
    br.close();
  }


}
