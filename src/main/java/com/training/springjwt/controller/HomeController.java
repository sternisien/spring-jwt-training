package com.training.springjwt.controller;

import com.training.springjwt.entities.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class HomeController {

  private final List<Student> students =
      Arrays.asList(new Student(1, "Sébastien"), new Student(2, "Florian"));

  /**
   * Endpoint to get a student by id
   *
   * @return
   */
  @GetMapping("/{studentId}")
  public Student getStudent(@PathVariable Integer studentId) {
    return students.stream()
        .filter(student -> studentId.equals(student.getStudentId()))
        .findFirst()
        .orElseThrow();
  }
}
