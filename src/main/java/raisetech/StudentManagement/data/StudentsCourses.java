package raisetech.StudentManagement.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {
  private  String CourseID;
  private  String StudentID;
  private  String CourseName;
  private  LocalDateTime CourseStartday ;
  private  LocalDateTime CourseEndday ;

}
