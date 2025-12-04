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
 * 受講生テーブルと受講生コース情報と紐づくRepositoryです。
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 *
 */
@Mapper
public interface StudentRepository {

  /**
   *受講生の全件検索します。
   *
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   *　受講生のコース情報の全件検索を行います。
   *
   * @Select("SELECT * FROM students_courses")から変更、
   *
   * Postmanでhttp://localhost:8080/studentListを送信すると、コースの開始日と終了日がnullで表示される。
   *
   * 原因：変更理由テーブル(students_courses)の開始日と終了日のテーブルのカラム名とJavaのフィールド名が異なるため、正しくマッピングが一致していなかったため。
   *
   * @return 受講生のコース情報(全件)
   */
  @Select("SELECT course_id, student_id, course_name, course_st_day, course_ed_day FROM students_courses")
  @Results({
      @Result(column="course_id", property="courseID"),
      @Result(column="student_id", property="studentID"),
      @Result(column="course_name", property="courseName"),
      @Result(column="course_st_day", property="courseStartday", javaType=LocalDateTime.class),
      @Result(column="course_ed_day", property="courseEndday", javaType=LocalDateTime.class)
  })
  List<StudentsCourses> searchStudentsCourse();

  /**
   * 受講生の検索を行います。
   * @param studentID 受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE student_id = #{studentID}")
  Student findStudentByID(String studentID);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentID　受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT course_id, student_id, course_name, course_st_day, course_ed_day FROM students_courses WHERE student_id = #{studentID}")
  @Results({
      @Result(column="course_id", property="courseID"),
      @Result(column="student_id", property="studentID"),
      @Result(column="course_name", property="courseName"),
      @Result(column="course_st_day", property="courseStartday", javaType=LocalDateTime.class),
      @Result(column="course_ed_day", property="courseEndday", javaType=LocalDateTime.class)
  })
  List<StudentsCourses> findCoursesByStudentID(String studentID);

  /**
   * 受講生情報を登録します。
   *
   * @param student　受講生
   */

  @Insert("INSERT INTO students (student_id, name, kana_name, nickname, email, area, age, gender, remark, isDeleted)"+
          "VALUES (#{studentID}, #{name}, #{kanaName}, #{nickName}, #{email}, #{area}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  void insert(Student student);

  /**
   * 受講生IDに紐づく受講生コース情報を登録します。
   *
   * @param course　コース情報
   */

  @Insert("INSERT INTO students_courses (course_id, student_id, course_name, course_st_day, course_ed_day)"+
      "VALUES (#{courseID}, #{studentID}, #{courseName}, #{courseStartday}, #{courseEndday})")
  void insertStudentCourse(StudentsCourses course);

  /**
   * 受講生情報を更新します。
   *
   * @param student　受講生
   */

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

  /**
   * 受講生IDに紐づく受講生コース情報を更新します。
   * コースIDも変更できるようにするため、旧コースIDをWHERE条件に持たせます。
   * @param studentID 受講生ID
   * @param oldCourseID 新コースID
   * @param newCourseID 旧コースID
   * @param courseName コース名
   * @param courseStartday コースの開始日
   * @param courseEndday コースの終了日
   */
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
