package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  @NotNull
  @Size(min = 10,max = 10)
  private  String studentID;

  private  String name;
  private  String kanaName;
  private  String nickName;
  private  String email;
  private  String area;
  private  int age;
  private  String gender;
  private  String remark;
  private  boolean isDeleted;

}
