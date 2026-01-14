package raisetech.StudentManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.CourseApplication;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録・更新処理を行います。
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
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧(全体)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();

    List<CourseApplication> applicationList = repository.searchCourseApplicationList();

    List<StudentDetail> details = converter.convertStudentDetails(studentList, studentCourseList);

    Map<String, List<CourseApplication>> appMap =
        applicationList.stream()
            .collect(Collectors.groupingBy(CourseApplication::getCourseId));

    for (StudentDetail detail : details) {
      List<CourseApplication> matched = new ArrayList<>();

      for (StudentCourse course : detail.getStudentCourseList()) {
        List<CourseApplication> apps = appMap.get(course.getCourseId());
        if (apps != null) {
          matched.addAll(apps);
        }
      }

      detail.setCourseApplicationList(matched);
    }
    return details;
    // return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生の検索を行います。
   *
   * @param studentId 受講生ID
   * @return 受講生ID
   */
  public Student findStudent(String studentId) {
    return repository.findStudentById(studentId);
  }

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  public List<StudentCourse> findCourse(String studentId) {
    return repository.findStudentCourseByStudentId(studentId);
  }

  /**
   * コースの申込状況の一覧検索
   *
   * @return コース申込状況一覧
   */
  public List<CourseApplication> searchCourseApplicationList() {
    return repository.searchCourseApplicationList();
  }


  /**
   * 受講生詳細検索です。 IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コースを取得して設定します。
   * <p>
   * コース情報からコースの申込状況を表示
   *
   * @param studentId 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(String studentId) {

    Student student = repository.findStudentById(studentId);

    List<StudentCourse> studentCourses =
        repository.findStudentCourseByStudentId(studentId);

    List<CourseApplication> applications =
        repository.searchCourseApplicationList();

    List<CourseApplication> matched = new ArrayList<>();
    for (StudentCourse course : studentCourses) {
      applications.stream()
          .filter(app -> app.getCourseId().equals(course.getCourseId()))
          .findFirst()
          .ifPresent(matched::add);
    }

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(studentCourses);
    detail.setCourseApplicationList(matched);

    return detail;
  }


  /**
   * 受講生詳細の更新を行います。 受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param student 受講生
   */
  @Transactional
  public void updateStudent(Student student) {
    repository.updateStudent(student);
  }

  /**
   * 受講生IDに紐づく受講生コース情報を更新します。 コースIDも変更できるようにするため、旧コースIDをWHERE条件に持たせます。
   * コース情報を全て更新するため、コース情報を全て取得して更新します。
   *
   * @param studentId   受講生ID
   * @param oldCourseId 旧受講生ID
   * @param course      コース情報ID
   */
  @Transactional
  public void updateCourses(String studentId, String oldCourseId, StudentCourse course) {
    repository.updateStudentCourse(
        studentId,
        oldCourseId,
        course.getCourseId(),
        course.getCourseName(),
        course.getCourseStartday(),
        course.getCourseEndday()
    );
  }

  @Transactional
  public void updateCourseApplication(List<CourseApplication> applications) {

    for (CourseApplication app : applications) {
      repository.updateCourseApplicationStatus(
          app.getCourseId(),
          app.getStatus()
      );
    }
  }


  /**
   * 受講生詳細の登録を行います。 受講生の情報と受講生のコース情報を個別に登録し、熟考性コース情報には、受講生IDに紐づける値とコースの開始日と終了日受を設定します。
   *
   * @param student 受講生
   * @param course  コース情報
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudentWthCourse(Student student, StudentCourse course) {
    repository.insertStudent(student);

    course.setStudentId(student.getStudentId());
    repository.insertStudentCourse(course);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    List<StudentCourse> courses = new ArrayList<>();
    courses.add(course);
    detail.setStudentCourseList(courses);
    return detail;
  }


  @Transactional
  public StudentDetail registerStudentWithCourseAndApplication(Student student,
      StudentCourse course, CourseApplication application) {
    repository.insertStudent(student);

    course.setStudentId(student.getStudentId());
    repository.insertStudentCourse(course);

    application.setCourseId(course.getCourseId());
    repository.insertCourseApplication(application);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(List.of(course));
    detail.setCourseApplicationList(List.of(application));

    return detail;
  }


}