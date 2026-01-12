package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生コース情報の全件検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(5);
  }


  @Test
  void 受講生IDで受講生の情報が取得できること() {
    Student actual = sut.findStudentById("st00000001");
    assertThat(actual.getStudentID()).isEqualTo("st00000001");
  }


  @Test
  void 受講生IDに紐づく受講生コースの情報をが取得できること() {
    List<StudentCourse> actual = sut.findStudentCourseByStudentId("st00000001");
    assertThat(actual.get(0).getStudentID()).isEqualTo("st00000001");
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setStudentID("st00000006");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickName("タロウ");
    student.setEmail("test@example.com");
    student.setArea("大阪府");
    student.setAge(40);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }


  @Test
  void 受講生IDに紐づく受講生コースの登録が行えること() {
    StudentCourse course = new StudentCourse();
    course.setCourseID("co00000006");
    course.setStudentID("st00000005");
    course.setCourseName("java基礎コース");
    course.setCourseStartday(LocalDateTime.of(2025, 12, 23, 11, 17));
    course.setCourseEndday(LocalDateTime.of(2025, 12, 23, 11, 17));

    sut.insertStudentCourse(course);

    List<StudentCourse> actual = sut.findStudentCourseByStudentId("st00000005");

    assertThat(actual.size()).isEqualTo(2);

  }


  @Test
  void 受講生情報の更新が行えること() {
    Student student = sut.findStudentById("st00000001");
    student.setKanaName("タロウヤマダ");
    student.setNickName("タロウ");

    sut.updateStudent(student);

    Student actual = sut.findStudentById("st00000001");

    assertThat(actual.getKanaName()).isEqualTo("タロウヤマダ");
    assertThat(actual.getNickName()).isEqualTo("タロウ");

    System.out.println(
        "更新結果:" +
            actual.getStudentID() + "," +
            actual.getKanaName() + "," +
            actual.getNickName() + "," +
            actual.getEmail() + "," +
            actual.getArea() + "," +
            actual.getAge() + "," +
            actual.getGender()
    );
  }


  @Test
  void 受講生コース情報を更新できること() {
    String studentID = "st00000001";
    String oldCourseID = "co00000001";
    String newCourseID = "co00000003";
    LocalDateTime CourseStartday = LocalDateTime.of(2025, 12, 23, 11, 17);
    LocalDateTime CourseEndday = LocalDateTime.of(2026, 1, 31, 11, 17);

    sut.updateStudentCourse(
        studentID,
        oldCourseID,
        newCourseID,
        "java応用コース",
        CourseStartday,
        CourseEndday
    );

    List<StudentCourse> updateList = sut.findStudentCourseByStudentId(studentID);

    StudentCourse actual = updateList.stream()
        .filter(c -> newCourseID.equals(c.getCourseID()))
        .findFirst()
        .orElse(null);

    assertThat(actual).isNotNull();
    assertThat(actual.getCourseName()).isEqualTo("java応用コース");
    assertThat(actual.getCourseStartday()).isEqualTo(LocalDateTime.of(2025, 12, 23, 11, 17));
    assertThat(actual.getCourseEndday()).isEqualTo(LocalDateTime.of(2026, 1, 31, 11, 17));
    System.out.println(
        "更新結果:" +
            actual.getCourseID() + "," +
            actual.getStudentID() + "," +
            actual.getCourseStartday() + "," +
            actual.getCourseEndday()
    );
  }
}