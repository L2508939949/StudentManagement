package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {
  @NotBlank
  @Size(min = 10, max = 10)
  private  String courseID;

  @NotBlank
  @Size(min = 10, max = 10)
  private  String studentID;

  @NotBlank
  private  String courseName;

  private  LocalDateTime courseStartday ;
  private  LocalDateTime courseEndday ;

}
