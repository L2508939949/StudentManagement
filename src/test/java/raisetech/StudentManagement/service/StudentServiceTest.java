package raisetech.StudentManagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void befoure() {
    sut = new StudentService(repository, converter);
  }


  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    StudentService sut = new StudentService(repository, converter);
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    List<StudentDetail> actual = sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }


  @Test
  void 受講生IDの検索_リポジトリの検索処理が1回呼び出され結果が返ること() {
    String studentId = "st00000001";
    Student student = new Student();

    when(repository.findStudentById(studentId)).thenReturn(student);

    Student actual = sut.findStudent(studentId);
    verify(repository, times(1)).findStudentById(studentId);
    assertThat(actual).isEqualTo(student);
  }


  @Test
  void 受講生コース検索_受講生IDに紐づくコース情報が取得できること() {
    String studentId = "st00000001";
    List<StudentCourse> studentCourse = new ArrayList<>();

    when(repository.findStudentCourseByStudentId(studentId)).thenReturn(studentCourse);

    List<StudentCourse> actual = sut.findCourse(studentId);
    assertEquals(studentCourse, actual);

    verify(repository, times(1)).findStudentCourseByStudentId(studentId);
  }


  @Test
  void 受講生詳細検索_IDに紐づく受講生とコースが返ること() {
    String studentId = "st00000001";
    Student student = new Student();
    List<StudentCourse> courses = new ArrayList<>();

    when(repository.findStudentById(studentId)).thenReturn(student);
    when(repository.findStudentCourseByStudentId(studentId)).thenReturn(courses);

    StudentDetail actual = sut.searchStudent(studentId);

    assertEquals(student, actual.getStudent());
    assertEquals(courses, actual.getStudentCourseList());

    verify(repository, times(1)).findStudentById(studentId);
    verify(repository, times(1)).findStudentCourseByStudentId(studentId);
  }


  @Test
  void 受講生更新_リポジトリの更新処理が1回呼ばれること() {
    Student student = new Student();
    sut.updateStudent(student);
    verify(repository, times(1)).updateStudent(student);
  }


  @Test
  void 受講生コース更新_必要な引数で更新処理が呼ばれること() {
    String studentId = "st00000001";
    String oldcourseId = "co00000001";
    LocalDateTime courseStartday = LocalDateTime.of(2025, 12, 16, 12, 6);
    LocalDateTime courseEndday = LocalDateTime.of(2026, 1, 31, 12, 6);

    StudentCourse course = new StudentCourse(
        "co00000002",
        studentId,
        "java応用コース",
        courseStartday,
        courseEndday
    );

    sut.updateCourses(studentId, oldcourseId, course);

    verify(repository, times(1)).updateStudentCourse(
        eq(studentId),
        eq(oldcourseId),
        eq("co00000002"),
        eq("java応用コース"),
        eq(courseStartday),
        eq(courseEndday)
    );
  }

  @Test
  void 受講生登録_受講生とコースが登録され受講生詳細が返ること() {
    Student student = new Student();
    student.setStudentId("st00000001");
    student.setName("山田太郎");

    StudentCourse course = new StudentCourse();
    course.setCourseId("co00000001");
    course.setCourseName("java基礎コース");

    StudentDetail actual = sut.registerStudentWthCourse(student, course);

    verify(repository, times(1)).insertStudent(student);
    verify(repository, times(1)).insertStudentCourse(course);

    assertEquals("st00000001", course.getStudentId());
    assertNotNull(actual);
    assertEquals(student, actual.getStudent());

    assertNotNull(actual.getStudentCourseList());
    assertEquals(1, actual.getStudentCourseList().size());
    assertEquals(course, actual.getStudentCourseList().get(0));
  }
}