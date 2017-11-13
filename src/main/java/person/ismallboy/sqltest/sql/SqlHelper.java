package person.ismallboy.sqltest.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlHelper {
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/test";
    private final static String USERNAME = "ismallboy";
    private final static String PASSWORD = "123456";

    private static Logger logger = LoggerFactory.getLogger(SqlHelper.class);

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("load deiver failed");
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            logger.error("get connection fail");
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("close connection failed");
            }
        }
    }

    public void releaseDb(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("close result set failed", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("close statement set failed", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("close connection failed", e);
            }
        }
    }

    /**
     * 获取数据列表
     *
     * @param sql
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String sql, Class<T> clazz, Object... args) {
        List<T> list = new ArrayList<T>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> mapList = resultSetToMap(resultSet);
            if (mapList != null) {
                list = mapListToList(mapList, clazz);
            }
        } catch (Exception e) {
            logger.error("executeQuery error", e);
        } finally {
            releaseDb(resultSet, preparedStatement, connection);
        }
        return list;
    }

    /**
     * resultset转换成mapList
     *
     * @param resultSet
     * @return
     */
    private List<Map<String, Object>> resultSetToMap(final ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (resultSet != null) {
            List<String> columnNameList = getColumnNameList(resultSet);
            if (columnNameList != null) {
                while (resultSet.next()) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (String columnName : columnNameList) {
                        map.put(columnName, resultSet.getObject(columnName));
                    }
                    mapList.add(map);
                }
            }
        }
        return mapList;
    }

    /**
     * map List转换成BeanList
     *
     * @param mapList
     * @param <T>
     * @return
     */
    private <T> List<T> mapListToList(List<Map<String, Object>> mapList, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<T> list = new ArrayList<T>();
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                T bean = clazz.newInstance();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String propName = entry.getKey();
                    Object value = entry.getValue();
                    Field field = bean.getClass().getDeclaredField(propName);
                    field.setAccessible(true);
                    field.set(bean, value);
                }
                list.add(bean);
            }
        }
        return list;
    }

    /**
     * 获取列名列表
     *
     * @param resultSet
     * @return
     */
    private List<String> getColumnNameList(ResultSet resultSet) throws SQLException {
        List<String> columnNameList = new ArrayList<String>();
        if (resultSet != null) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            if (resultSetMetaData != null) {
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    columnNameList.add(resultSetMetaData.getColumnName(i + 1));
                }
            }
        }
        return columnNameList;
    }
}
