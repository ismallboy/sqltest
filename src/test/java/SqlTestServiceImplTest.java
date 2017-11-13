import org.junit.Test;
import person.ismallboy.sqltest.service.SqlTestServiceImpl;

public class SqlTestServiceImplTest {
    @Test
    public void getStudentList() throws Exception {
        SqlTestServiceImpl sqlTestService = new SqlTestServiceImpl();
        sqlTestService.getStudentList();
    }

}