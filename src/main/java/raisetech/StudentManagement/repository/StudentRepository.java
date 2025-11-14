package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 *
 */
@Mapper
public interface StudentRepository {

  /**
   *全件検索します。
   *
   * @return　全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourse();

  @Insert("INSERT INTO students (student_id, name, kana_name, nickname, email, area, age, gender, remark, isDeleted)"+
          "VALUES(#{studentID}, #{name}, #{kanaName}, #{nickName}, #{email}, #{area}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  void insert(Student student);

  @Insert("INSERT INTO students_courses (`course_id, student_id, course_name, course_st_day, course_ed_day)"+
      "VALUES(#{courseID}, #{studentID}, #{CourseName}, #{CourseStartday}, #{CourseEndday})")
  void insertStudentCourse(StudentsCourses course);

}
