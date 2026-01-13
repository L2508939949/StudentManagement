package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "受講生")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {

  @Size(min = 10, max = 10)
  @NotBlank
  private String studentId;

  @NotBlank
  private String name;
  @NotBlank
  private String kanaName;
  @NotBlank
  private String nickName;
  @NotBlank
  @Email
  private String email;
  @NotBlank
  private String area;

  private int age;
  @NotBlank
  private String gender;

  private String remark;
  private boolean isDeleted;

}