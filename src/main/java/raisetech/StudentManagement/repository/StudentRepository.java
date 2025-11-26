package raisetech.StudentManagement.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
  @Select("SELECT * FROM students WHERE isDeleted = false")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourse();

  @Insert("INSERT INTO students (student_id, name, kana_name, nickname, email, area, age, gender, remark, isDeleted)"+
          "VALUES (#{studentID}, #{name}, #{kanaName}, #{nickName}, #{email}, #{area}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  void insert(Student student);

  @Insert("INSERT INTO students_courses (course_id, student_id, course_name, course_st_day, course_ed_day)"+
      "VALUES (#{courseID}, #{studentID}, #{courseName}, #{courseStartday}, #{courseEndday})")
  void insertStudentCourse(StudentsCourses course);

  @Select("SELECT * FROM students WHERE student_id = #{studentID}")
  Student findStudentByID(String studentID);

  @Select("SELECT course_id, student_id, course_name, course_st_day, course_ed_day FROM students_courses WHERE student_id = #{studentID}")
  @Results({
      @Result(column="course_id", property="courseID"),
      @Result(column="student_id", property="studentID"),
      @Result(column="course_name", property="courseName"),
      @Result(column="course_st_day", property="courseStartday", javaType=LocalDateTime.class),
      @Result(column="course_ed_day", property="courseEndday", javaType=LocalDateTime.class)
  })
  List<StudentsCourses> findCoursesByStudentID(String studentID);


  @Update("""
      UPDATE students SET
        name = #{name},
        kana_name = #{kanaName},
        nickname = #{nickName},
        email = #{email},
        area = #{area},
        age = #{age},
        gender = #{gender},
        remark = #{remark},
        isDeleted =#{isDeleted}
      WHERE student_id = #{studentID}
      """)
  void updateStudent(Student student);

  @Update("""
      UPDATE students_courses SET
        course_id = #{newCourseID},
        course_name = #{courseName},
        course_st_day = #{courseStartday},
        course_ed_day = #{courseEndday}
      WHERE course_id = #{oldCourseID} AND
            student_id = #{studentID}
      """)
  void updateStudentCourse(String studentID, String oldCourseID, String newCourseID, String courseName, @Param("courseStartday") LocalDateTime courseStartday, @Param("courseEndday") LocalDateTime courseEndday);

}
