package raisetech.StudentManagement.service;

import java.util.List;
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
    //検索処理
    //ここで何かしたの処理を行う
    repository.search();

    //絞り込みをする。年齢が30第の人のみを抽出する。
    //抽出したリストをコントローラーに返す
    return repository.search();

  }

  public List<StudentsCourses> searchStudentsCourseList(){
    //絞り込み検索で「JAVAコース」のコース情報のみを抽出する。
    //抽出したリストをコントローラーに返す。
    return repository.searchStudentsCourse();
  }

}
