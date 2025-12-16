package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  @Size(min = 10,max = 10)
  @NotBlank
  private  String studentID;

  @NotBlank
  private  String name;
  @NotBlank
  private  String kanaName;
  @NotBlank
  private  String nickName;
  @NotBlank
  @Email
  private  String email;
  @NotBlank
  private  String area;

  private  int age;
  @NotBlank
  private  String gender;

  private  String remark;
  private  boolean isDeleted;

}
