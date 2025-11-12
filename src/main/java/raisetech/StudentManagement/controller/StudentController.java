package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
    this.service = service;
  }

  @GetMapping("/studentList")
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentsCourseList(){
    return service.searchStudentsCourseList();
  }
}
