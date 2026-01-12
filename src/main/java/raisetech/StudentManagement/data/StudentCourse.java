package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  @NotBlank
  @Size(min = 10, max = 10)
  private String courseId;

  @NotBlank
  @Size(min = 10, max = 10)
  private String studentId;

  @NotBlank
  private String courseName;
  private LocalDateTime courseStartday;
  private LocalDateTime courseEndday;


}