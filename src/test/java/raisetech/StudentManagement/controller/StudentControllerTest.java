package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void students_APIは例外で返ってくること() throws Exception {
    mockMvc.perform(get("/students"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(
            "現在のこのAPIは知用出来ません。URLは「students」ではなく「studentList」を利用してください。"));
  }

  @Test
  void 受講生IAを指定して受講生詳細が取得できること() throws Exception {
    String studentID = "st00000001";

    StudentDetail mockstudentDetail = new StudentDetail();
    when(service.searchStudent(studentID)).thenReturn(mockstudentDetail);

    mockMvc.perform(get("/student/{studentID}", studentID))
        .andExpect(status().isOk());
    verify(service, times(1)).searchStudent(studentID);
  }

  @Test
  void 受講生情報とコース情報が更新できること() throws Exception {

    String json = """
        {
          "student" :{
            "studentID" :"st00000001",
            "name" : "山田太郎"
          },
          "studentCourseList":[
            {
              "studentID" :"st00000001",
              "courseID" : "co00000001",
              "courseName" : "java基礎コース"
            }
          ]
        }
        """;

    mockMvc.perform(
        put("/updateStudent")
            .contentType("application/json")
            .param("oldCourseID", "co00000001")
            .param("courseStartday", "2025-12-17T00:00")
            .param("courseEndday", "2026-01-16T00:00")
            .content(json)
    ).andExpect(status().isOk()).andExpect(content().string("更新処理が成功しました。"));

    verify(service, times(1)).updateStudent(any());
    verify(service, times(1))
        .updateCourses(
            eq("st00000001"),
            eq("co00000001"),
            any(StudentCourse.class)
        );
  }

  @Test
  void 受講生登録が成功できること() throws Exception {
    StudentDetail response = new StudentDetail();
    when(service.registerStudentWthCourse(any(), any()))
        .thenReturn(response);

    String json = """
            {
              "student":{
              "studentID" :"st00000001",
              "name" : "山田太郎"
              },
              "studentCourseList":[
                {
                "studentID" :"st00000001",
                "courseID": "co00000001",
                "courseName":"java基礎コース"
                }
              ]
           }
        """;
    mockMvc.perform(
            post("/registerStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
        .andExpect(status().isOk());
    verify(service, times(1)).registerStudentWthCourse(any(), any());
  }

  @Test
  void 受講生詳細の受講生で適せつな値を入力した時に入力チェックに掛かること() {
    Student student = new Student("st00000001",
        "山田太郎",
        "ヤマダタロウ",
        "タロウ",
        "test@example.com",
        "大阪府",
        40,
        "男性",
        "",
        false
    );

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いたときに入力チェックに掛かること() {
    Student student = new Student();
    student.setStudentId("テストです。");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickName("タロウ");
    student.setEmail("test@example.com");
    student.setArea("大阪府");
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").
        containsOnly("数字のみ入力するようにしてください。");
  }

  @Test
  void 受講生IAを指定した内容に10桁以外のとき入力チェックに掛かること() throws Exception {
    String studentID = "st000001";

    ServletException exception =
        assertThrows(ServletException.class, () -> {
          mockMvc.perform(get("/student/{studentID}", studentID)).andReturn();
        });

    assertThat(exception.getCause())
        .isInstanceOf(ConstraintViolationException.class);

    verify(service, times(0)).searchStudent(any());
  }

  @Test
  void 受講生登録時にコース情報のコースIDが10桁以外のとき入力チェックに掛かること()
      throws Exception {
    StudentDetail response = new StudentDetail();
    when(service.registerStudentWthCourse(any(), any()))
        .thenReturn(response);

    String json = """
            {
              "student":{
              "studentID" :"st00000001",
              "name" : "山田太郎"
              },
              "studentCourseList":[
                {
                "studentID" :"st00000001",
                "courseID": "co000001",
                "courseName":"java基礎コース"
                }
              ]
            }
        """;

    mockMvc.perform(
            post("/registerStudent")
                .contentType("application/json")
                .content(json)
        )
        .andExpect(status().isBadRequest());
    verify(service, times(0)).registerStudentWthCourse(any(), any());
  }
}