package com.group.libraryapp.dto.user.request;

import org.jetbrains.annotations.NotNull;

public class UserCreateRequest {

  private String name;
  private Integer age;

  public UserCreateRequest(String name, Integer age) {
    this.name = name;
    this.age = age;
  }

  @NotNull
  public String getName() {
    return name;
  }

  @NotNull
  public Integer getAge() {
    return age;
  }

}
