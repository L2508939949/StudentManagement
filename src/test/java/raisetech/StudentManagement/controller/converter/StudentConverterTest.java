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

  private  StudentConverter sut;
  @BeforeEach
    void before(){
    sut = new StudentConverter();
  }


  @Test
  void  受講生のリストと受講生コース情報の知ストを渡して受講生詳細リストが作成できること(){

    Student student = new Student();

    student.setStudentID("st00000001");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickName("タロウ");
    student.setEmail("test@example.com");
    student.setArea("大阪府");
    student.setAge(40);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    StudentCourse studentCourse = new StudentCourse();
    LocalDateTime courseStartday = LocalDateTime.of(2025,12,22,12,06);
    LocalDateTime courseEndday = LocalDateTime.of(2026,2,21,12,06);

    studentCourse.setCourseID("co00000001");
    studentCourse.setStudentID("st00000001");
    studentCourse.setCourseName("java基礎コース");
    studentCourse.setCourseStartday(courseStartday);
    studentCourse.setCourseEndday(courseEndday);

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(result.get(0).getStudent()).isEqualTo(student);
    assertThat(result.get(0).getStudentCourseList()).isEmpty();

  }
}