package person.ismallboy.sqltest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.ismallboy.sqltest.service.SqlTestServiceImpl;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("main start");
        SqlTestServiceImpl sqlTest = new SqlTestServiceImpl();
        sqlTest.getStudentList();
        logger.debug("main end");
    }
}
