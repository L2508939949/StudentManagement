package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList(){
    return repository.search();
  }
  public List<StudentsCourses> searchStudentsCourseList(){
    return repository.searchStudentsCourse();
  }

  public  Student findStudent(String studentID){
    return repository.findStudentByID(studentID);
  }

  public List<StudentsCourses> findCourses(String studentID) {
    return repository.findCoursesByStudentID(studentID);
  }

  @Transactional
  public void updateStudent(Student student) {
    repository.updateStudent(student);
  }

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

  @Transactional
  public  void  registerStudentWthCourse(Student student, StudentsCourses course){
    repository.insert(student);

    course.setStudentID(student.getStudentID());
    repository.insertStudentCourse(course);
  }
}
