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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
      }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧(全件)
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList(){
    return service.searchStudentList();
  }

  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param studentID　受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{studentID}")
  public StudentDetail getStudent(@PathVariable String studentID){
    return service.searchStudent(studentID);
  }

  /**
   * 受講生情報と受講生コース情報を更新します。
   * コースIDも変更できるようにするため、旧コースIDをWHERE条件に持たせます。
   * @param studentDetail 受講生情報と受講生コース情報
   * @param oldCourseID 旧受講生ID
   * @param courseStartdayStr コースの開始日
   * @param courseEnddayStr　コースの修了日
   * @return メッセージで更新処理が成功しました。
   */

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail,
      @RequestParam("oldCourseID") String oldCourseID,
      @RequestParam("courseStartday") String courseStartdayStr,
      @RequestParam("courseEndday") String courseEnddayStr) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    List<StudentsCourses> courses = studentDetail.getStudentsCourse();
    if (courses != null && !courses.isEmpty()) {
      StudentsCourses course = courses.get(0);

      if (courseStartdayStr != null && !courseStartdayStr.isEmpty()) {
        course.setCourseStartday(LocalDateTime.parse(courseStartdayStr, formatter));
      }
      if (courseEnddayStr != null && !courseEnddayStr.isEmpty()) {
        course.setCourseEndday(LocalDateTime.parse(courseEnddayStr, formatter));
      }

      service.updateStudent(studentDetail.getStudent());
      service.updateCourses(studentDetail.getStudent().getStudentID(), oldCourseID, course);
    }
    return ResponseEntity.ok("更新処理が成功しました。");
  }
  /**
   * 受講生の情報と受講生のコース情報を登録します。
   * 受講生IDに紐づく受講生コース情報も登録します。
   * 更新を受講生情報と受講生コース情報を表示します。
   *
   * @param studentDetail 　受講生情報と受講生コース情報
   * @return 受講生情報と受講生コース情報
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    List<StudentsCourses> courses= studentDetail.getStudentsCourse();
    StudentDetail responseStudentDetail = null;

    if (courses != null && !courses.isEmpty()) {
      // 1件だけ登録
      responseStudentDetail = service.registerStudentWthCourse(
          studentDetail.getStudent(),
          courses.get(0)
      );
    }
    return ResponseEntity.ok(responseStudentDetail);
  }
}
