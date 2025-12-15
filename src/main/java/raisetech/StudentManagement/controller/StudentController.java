package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
      }

  /**
   * 受講生商大の一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧(全件)
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList(){
    return service.searchStudentList();
  }

  /**
   * エラー用のメソッド
   */
  @GetMapping("/students")
  public List<StudentDetail> getStudentsList()  {
    throw new ExceptionHandling("現在のこのAPIは知用出来ません。URLは「students」ではなく「studentList」を利用してください。");
  }

  /**
   * 受講生詳細の検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param studentID　受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{studentID}")
  public StudentDetail getStudent(
      @PathVariable @NotBlank @Size(min = 10, max = 10) String studentID){
    return service.searchStudent(studentID);
  }

  /**
   * 受講生情報と受講生コース情報を更新します。
   * キャンセルフラグの更新もここで行います。(論理削除)
   * コースIDも変更できるようにするため、旧コースIDをWHERE条件に持たせます。
   * @param studentDetail 受講生情報と受講生コース情報
   * @param oldCourseID 旧受講生ID
   * @param courseStartdayStr コースの開始日
   * @param courseEnddayStr　コースの修了日
   * @return 実行結果
   */


  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(
      @RequestBody @Valid StudentDetail studentDetail,
      @RequestParam("oldCourseID") @NotBlank @Size(min = 10, max = 10) String oldCourseID,
      @RequestParam("courseStartday") String courseStartdayStr,
      @RequestParam("courseEndday") String courseEnddayStr) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    List<StudentCourse> courses = studentDetail.getStudentCourseList();
    if (courses != null && !courses.isEmpty()) {
      StudentCourse course = courses.get(0);

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
   * 受講生詳細の登録を行います。
   * 受講生の情報と受講生のコース情報を登録します。
   * 受講生IDに紐づく受講生コース情報も登録します。
   * 更新を受講生情報と受講生コース情報を表示します。
   *
   * @param studentDetail 　受講生情報と受講生コース情報
   * @return 実行結果
   */

  @Operation(summary ="受講生登録", description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    List<StudentCourse> courses= studentDetail.getStudentCourseList();
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

  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(ExceptionHandling ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
