package raisetech.StudentManagement;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
  private  String CourseID;
  private  String StudentID;
  private  String CourseName;
  private  LocalDateTime CourseStartday ;
  private  LocalDateTime CourseEndday ;
}
