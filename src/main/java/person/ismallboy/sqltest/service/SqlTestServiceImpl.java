package person.ismallboy.sqltest.service;

import person.ismallboy.sqltest.dto.Student;
import person.ismallboy.sqltest.sql.SqlHelper;

import java.util.List;

public class SqlTestServiceImpl {

    public List<Student> getStudentList() {
        String sql = "select * from student";
        SqlHelper sqlHelper = new SqlHelper();
        List<Student> studentList = sqlHelper.getList(sql, Student.class);
        return studentList;
    }
}
