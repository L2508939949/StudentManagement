package raisetech.StudentManagement.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {
  private  String courseID;
  private  String studentID;
  private  String courseName;
  private  LocalDateTime courseStartday ;
  private  LocalDateTime courseEndday ;

}
