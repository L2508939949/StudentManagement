package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  /*
  private String name = "Enami Kouji";
  private String age = "37";

   @GetMapping("/studentInfo")
  public String getStudentInfo(){
    return name + " " + age + "歳";
  }

  @PostMapping("/studentInfo")
  public  void setStudentInfo(String name,String age){
  this.name = name;
    this.age = age;
  }
  @PostMapping("/studentName")
  public void updeteStudentName(String name){
    this.name = name;
  }
  @GetMapping("/hello")
    public String hello(){
      return "Hello, World!";
  }

  @GetMapping("/greeting")
  public String greeting(){
    return "こんにちは、Webのプログラム開発の世界にようこそ！";
  }
   */

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

