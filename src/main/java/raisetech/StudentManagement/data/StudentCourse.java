package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  @NotBlank
  @Size(min = 10, max = 10)
  private String courseID;

  @NotBlank
  @Size(min = 10, max = 10)
  private String studentID;

  @NotBlank
  private String courseName;
  private LocalDateTime courseStartday;
  private LocalDateTime courseEndday;

  public StudentCourse(String courseID, String courseName,
      LocalDateTime courseStartday, LocalDateTime courseEndday) {
    this.courseID = courseID;
    this.courseName = courseName;
    this.courseStartday = courseStartday;
    this.courseEndday = courseEndday;
  }
}
