package raisetech.StudentManagement.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
      }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList(){
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();
    return converter.convertStudentDetails(students,studentsCourses);
  }

  @GetMapping("/updateStudentForm")
  public String  updateStudentForm(@RequestParam("studentID") String studentID,Model model){
    Student student = service.findStudent(studentID);
    List<StudentsCourses> courses = service.findCourses(studentID);

    if (courses.isEmpty()) courses.add(new StudentsCourses());

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setCourse(courses.get(0));
    model.addAttribute("studentDetail", detail);
    model.addAttribute("oldCourseID", courses.get(0).getCourseID());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    if (courses.get(0).getCourseStartday() != null) {
      model.addAttribute("courseStartdayFormatted",
          courses.get(0).getCourseStartday().format(formatter));
    }
    if (courses.get(0).getCourseEndday() != null) {
      model.addAttribute("courseEnddayFormatted",
          courses.get(0).getCourseEndday().format(formatter));
    }
    return "updateStudent";
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail,
      @RequestParam("oldCourseID") String oldCourseID,
      @RequestParam("courseStartday") String courseStartdayStr,
      @RequestParam("courseEndday") String courseEnddayStr) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    if (courseStartdayStr != null && !courseStartdayStr.isEmpty()) {
      studentDetail.getCourse().setCourseStartday(LocalDateTime.parse(courseStartdayStr, formatter));
    }
    if (courseEnddayStr != null && !courseEnddayStr.isEmpty()) {
      studentDetail.getCourse().setCourseEndday(LocalDateTime.parse(courseEnddayStr, formatter));
    }
    service.updateStudent(studentDetail.getStudent());
    service.updateCourses(studentDetail.getStudent().getStudentID(), oldCourseID, studentDetail.getCourse());
    return ResponseEntity.ok("更新処理が成功しました。");
  }


  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentsCourseList(){
    return service.searchStudentsCourseList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }


  @PostMapping("/registerStudent")
  public  String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if(result.hasErrors()) {
      return "registerStudent";
    }
    service.registerStudentWthCourse
        (studentDetail.getStudent(),
            studentDetail.getCourse());

    System.out.println(studentDetail.getStudent().getName()+ "さんが新規受講生として登録されました。");
    return "redirect:/studentList";
  }
}
