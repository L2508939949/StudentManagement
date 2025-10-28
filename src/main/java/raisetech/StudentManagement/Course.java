package raisetech.StudentManagement;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
  private  String courseID;
  private  String studentID;
  private  String courseName;
  private LocalDateTime courseStday ;
  private  LocalDateTime courseEdday ;

}
