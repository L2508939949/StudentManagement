package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "コースの申込状況")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseApplication {

  @Size(min = 10, max = 10)
  @NotBlank
  private String applicationId;

  @NotBlank
  @Size(min = 10, max = 10)
  private String courseId;

  private String status;

}
