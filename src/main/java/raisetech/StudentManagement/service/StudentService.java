package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   * @return 受講生一覧(全体)
   */
  public List<StudentDetail> searchStudentList(){
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCourse();
    return converter.convertStudentDetails(studentList,studentsCoursesList);
  }
  /**
   * 受講生の検索を行います。
   * @param studentID 受講生ID
   * @return 受講生ID
   */
  public  Student findStudent(String studentID){
    return repository.findStudentByID(studentID);
  }

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentID　受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  public List<StudentsCourses> findCourses(String studentID) {
    return repository.findCoursesByStudentID(studentID);
  }

  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コースを取得して設定します。
   *
   * @param studentID　受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String studentID) {
    Student student = repository.findStudentByID(studentID);
    List<StudentsCourses> studentsCourses = repository.findCoursesByStudentID(studentID);
    return new StudentDetail(student, studentsCourses);
  }

  /**
   * 受講生のデータを更新です。
   * 受講生IDと一致したデータを更新します。
   * @param student　受講生
   */
  @Transactional
  public void updateStudent(Student student) {
    repository.updateStudent(student);
  }

  /**
   * 受講生IDに紐づく受講生コース情報を更新します。
   * コースIDも変更できるようにするため、旧コースIDをWHERE条件に持たせます。
   * コース情報を全て更新するため、コース情報を全て取得して更新します。
   * @param studentID　受講生ID
   * @param oldCourseID 旧受講生ID
   * @param course コース情報ID
   */
  @Transactional
  public void updateCourses(String studentID, String oldCourseID, StudentsCourses course) {
    repository.updateStudentCourse(
        studentID,
        oldCourseID,
        course.getCourseID(),
        course.getCourseName(),
        course.getCourseStartday(),
        course.getCourseEndday()
    );
  }

  /**
   *受講生の情報と受講生のコース情報を登録します。
   * 受講生IDに紐づく受講生コース情報を登録します。
   * @param student　受講生
   * @param course　コース情報
   * @return 受講生情報と受講生コース情報
   */
  @Transactional
  public StudentDetail registerStudentWthCourse(Student student, StudentsCourses course){
    repository.insert(student);

    course.setStudentID(student.getStudentID());
    repository.insertStudentCourse(course);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    List<StudentsCourses> courses = new ArrayList<>();
    courses.add(course);
    detail.setStudentsCourse(courses);
    return detail;
  }
}
