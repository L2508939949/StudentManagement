package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 *
 */
@Mapper
public interface StudentRepository {

  /**
   *全件検索します。
   *
   * @return　全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourse();


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
