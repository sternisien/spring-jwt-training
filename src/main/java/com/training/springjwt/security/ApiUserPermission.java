package com.training.springjwt.security;

public enum ApiUserPermission {
  STUDENT_READ("student:read"),
  STUDENT_WRITE("student:write"),
  COURSE_READ("course:read"),
  COURSE_WRITE("course:write");

  ApiUserPermission(String permission) {
    this.value = permission;
  }

  private String value;

  public String getValue() {
    return value;
  }
}
