package raisetech.StudentManagement.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    return repository.search()
        .stream().filter(student -> student.getAge() >=30 && student.getAge() <40)
        .collect(Collectors.toList());
  }
  public List<StudentsCourses> searchStudentsCourseList(){

    return repository.searchStudentsCourse()
        .stream().filter(course -> course.getCourseName() != null &&
            course.getCourseName().toLowerCase().contains("java"))
        .collect(Collectors.toList());
  }

}
