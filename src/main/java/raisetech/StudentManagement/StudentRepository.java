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


  /*
  @Select("SELECT * FROM student WHERE name= #{name}")
  Student searchByName(String name);

  @Insert("INSERT student values(#{name},#{age})")
  void registarStudent(String name,int age);

  @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
  void updateStudent( String name ,int age);

  @Delete("DELETE FROM student WHERE name = #{name}")
  void deleteStudent(String name);

  @Select("SELECT * FROM student")
  List<Student> StudentList();

   */
}
