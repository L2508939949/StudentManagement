package raisetech.StudentManagement.controller.converter;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }


  @Test
  void 受講生のリストと受講生コース情報を渡して受講生詳細リストが作成できること() {

    Student student = new Student(
        "st00000001",
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

    LocalDateTime courseStartday = LocalDateTime.of(2025, 12, 22, 12, 06);
    LocalDateTime courseEndday = LocalDateTime.of(2026, 2, 21, 12, 06);

    StudentCourse studentCourse = new StudentCourse(
        "co00000001",
        "st00000001",
        "java基礎コース",
        courseStartday,
        courseEndday
    );

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(result.get(0).getStudent()).isEqualTo(student);
    assertThat(result.get(0).getStudentCourseList())
        .containsExactly(studentCourse);

  }
}