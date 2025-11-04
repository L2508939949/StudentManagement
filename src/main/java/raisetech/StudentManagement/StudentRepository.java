package raisetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search(String name);

  @Select("SELECT * FROM students_courses")
  List<Course> searchCourse(String course);

}
