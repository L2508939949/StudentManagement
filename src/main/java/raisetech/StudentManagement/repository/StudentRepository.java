package raisetech.StudentManagement.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.StudentManagement.data.CourseApplication;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報と紐づくRepositoryです。 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 *
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索します。
   *
   * @return 全件検索した受講生情報の一覧
   */
  List<Student> search();

  /**
   * 受講生のコース情報の全件検索を行います。 Select("SELECT * FROM students_courses")から変更、
   * Postmanで、(http://localhost:8080/studentList)を送信すると、コースの開始日と終了日がnullで表示される。
   * 原因：変更理由テーブル(students_courses)の開始日と終了日のテーブルのカラム名とJavaのフィールド名が異なるため、正しくマッピングが一致していなかったため。
   *
   * @return 受講生のコース情報(全件)
   */
  List<StudentCourse> searchStudentCourseList();


  /**
   * 受講生の検索を行います。
   *
   * @param studentId 受講生ID
   * @return 受講生
   */
  Student findStudentById(String studentId);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> findStudentCourseByStudentId(String studentId);


  /**
   * コースの申込状況の全件検索
   *
   * @return 全件検索したコースの申込状況の一覧
   */
  List<CourseApplication> searchCourseApplcationList();

  /**
   * 指定したコースの申込状況が検索されるようにする。
   *
   * @param courseId
   * @return
   */
  CourseApplication findCourseApplicationByCourseId(String courseId);

  void insertCourseApplication(CourseApplication application);

  void updateCourseApplicationStatus(
      @Param("courseId") String courseId,
      @Param("status") String status
  );


  /**
   * 受講生を新規登録します。
   *
   * @param student 受講生
   */
  void insertStudent(Student student);

  /**
   * 受講生IDに紐づく受講生コース情報を新規登録します。
   *
   * @param course 受講生コース情報
   */
  void insertStudentCourse(StudentCourse course);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報を更新します。 コースIDも変更できるようにするため、旧コースIDをWHERE条件に持たせます。
   *
   * @param studentId      受講生ID
   * @param oldCourseId    新コースID
   * @param newCourseId    旧コースID
   * @param courseName     コース名
   * @param courseStartday コースの開始日
   * @param courseEndday   コースの終了日
   */
  void updateStudentCourse(String studentId, String oldCourseId, String newCourseId,
      String courseName, @Param("courseStartday") LocalDateTime courseStartday,
      @Param("courseEndday") LocalDateTime courseEndday);


}
