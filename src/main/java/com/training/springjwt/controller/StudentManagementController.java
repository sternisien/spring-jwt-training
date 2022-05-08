package com.training.springjwt.controller;

import com.training.springjwt.entities.Student;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

  private final List<Student> students =
      Arrays.asList(new Student(1, "Sébastien"), new Student(2, "Florian"));

  /**
   * Endpoint to get a student by id
   *
   * @return
   */
  @GetMapping
  public List<Student> getAllStudents() {
    return students;
  }

  @PostMapping
  public void registerNewStudent(@RequestBody Student student) {
    System.out.println(student);
  }

  @DeleteMapping("/{studentId}")
  public void deleteStudent(@PathVariable Integer studentId) {
    System.out.println(studentId);
  }

  @PutMapping("/{studentId}")
  public void updateStudent(@PathVariable Integer studentId, @RequestBody Student student) {
    System.out.println(studentId);
  }
}
