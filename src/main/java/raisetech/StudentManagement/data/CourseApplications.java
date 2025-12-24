package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class CourseApplications {

  @Size(min = 10, max = 10)
  @NotBlank
  private String applicationID;

  @Size(min = 10, max = 10)
  @NotBlank
  private String studentID;

  @NotBlank
  @Size(min = 10, max = 10)
  private String courseID;

  private String status;

}
