package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  @Autowired
  private  StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }
  @GetMapping("/studentList")
  public List<Student> getStudentList(String name){
    return repository.search(name);
  }

  @GetMapping("/courseList")
  public List<Course> getCourseList(String course){
    return repository.searchCourse(course);
  }











  /*

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }
  /*
   @GetMapping("/student")
  public String getStudent(@RequestParam String name){
    Student student = repository.searchByName(name);
    return student.getName() + " " + student.getAge() + "歳";
  }
   //

  @GetMapping("/student")
  public String getAllStudent(){
    List<Student> studentList = repository.StudentList();
    StringBuffer result = new StringBuffer();
    for (Student student : studentList){
      result.append(student.getName())
          .append(" ")
          .append(student.getAge())
          .append("歳\n");
    }
    return result.toString();
  }


  @PostMapping("/student")
  public  void registerStudent(String name,int age){
   repository.registarStudent(name,age);

  }
  @PatchMapping("/student")
  public void updeteStudent(String name,int age){
    repository.updateStudent(name,age);
  }
  @DeleteMapping("/student")
  public void deleteStudent(String name){
    repository.deleteStudent(name);
  }
  */

  /*
  @GetMapping("/hello")
    public String hello(){
      return "Hello, World!";
  }

  @GetMapping("/greeting")
  public String greeting(){
    return "こんにちは、Webのプログラム開発の世界にようこそ！";
  }
   */




  /*
  @Autowired
  private  StudentRepository repository;


  private String name;
  private String age;
  private Map<String,String> students = new HashMap<>(Map.of("山田　太郎","26","山田　花子","23"));

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }
  @GetMapping("/studentInformation")
  public  String getStudensInformation(){
    StringBuffer result = new StringBuffer();
    for (Map.Entry<String,String>entry : students.entrySet()){
      name = entry.getKey();
      age = entry.getValue();
      result.append(name).append("：").append(age).append("歳\n");
    }
    return result.toString();
  }
  @PostMapping("/studentInformation")
  public String  updateStudentAge(@RequestParam String name, @RequestParam String age) {
    if (students.containsKey(name)) {
      students.put(name, age);  // 年齢を更新
      return name + "さんの年齢を" + age + "歳に変更しました。";
    } else {
      return name + "さんは登録されていません。";
    }
  }
}

