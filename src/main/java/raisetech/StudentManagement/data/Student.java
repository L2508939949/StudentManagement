package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Student student = (Student) o;
    return age == student.age &&
        isDeleted == student.isDeleted &&
        Objects.equals(studentId, student.studentId) &&
        Objects.equals(name, student.name) &&
        Objects.equals(kanaName, student.kanaName) &&
        Objects.equals(nickName, student.nickName) &&
        Objects.equals(email, student.email) &&
        Objects.equals(area, student.area) &&
        Objects.equals(gender, student.gender) &&
        Objects.equals(remark, student.remark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        studentId,
        name,
        kanaName,
        nickName,
        email,
        area,
        age,
        gender,
        remark,
        isDeleted
    );
  }
}
