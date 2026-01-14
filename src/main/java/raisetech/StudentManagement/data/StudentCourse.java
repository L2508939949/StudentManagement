package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StudentCourse that = (StudentCourse) o;
    return Objects.equals(courseId, that.courseId) &&
        Objects.equals(studentId, that.studentId) &&
        Objects.equals(courseName, that.courseName) &&
        Objects.equals(courseStartday, that.courseStartday) &&
        Objects.equals(courseEndday, that.courseEndday);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        courseId,
        studentId,
        courseName,
        courseStartday,
        courseEndday
    );
  }
}