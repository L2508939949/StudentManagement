 CREATE TABLE IF NOT EXISTS students
 (
    student_id VARCHAR(10) NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    kana_name VARCHAR(30) NOT NULL,
    nickname VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    area VARCHAR(100),
    age INT,
    gender VARCHAR(20),
    remark TEXT,
    isDeleted boolean
 );

 CREATE TABLE IF NOT EXISTS students_courses
 (
   course_id varchar(10) NOT NULL,
   student_id varchar(10) NOT NULL,
   course_name varchar(100) NOT NULL,
   course_st_day timestamp,
   course_ed_day timestamp,
   PRIMARY KEY (course_id,student_id)
 );