package com.lannstark.lec11;

public abstract class StringUtils {

  private StringUtils() {} // 유틸성 메서드

  public boolean isDirectoryPath(String path) {
    return path.endsWith("/");
  }

}

