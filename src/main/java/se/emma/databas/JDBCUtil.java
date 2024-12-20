package se.emma.databas;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    private static Properties properties = new Properties();

    //för att loada properties en gång när klassen load, för att vi har en test databas o en vanlig
    static {
        try (InputStream input = JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            //om inte properties filen finns
            if (input == null) {
                throw new IOException("Unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load database properties");
        }
    }

    //ger connection till databasen
    public static Connection getConnection() throws SQLException {
        //skapa upp en instans av hsql:s jdbcDriver-klass
        Driver hsqlDriver = new org.hsqldb.jdbcDriver();
        //registrera drivern hos klassen DriverManager
        DriverManager.registerDriver(hsqlDriver);
        //Skapa en URL till databasen
        String dbURL = properties.getProperty("db.url");
        //Default användarnamn
        String userId = properties.getProperty("db.user");
        //Default password
        String password = properties.getProperty("db.password");
        //Använd metoden getConnection i DriverManager för att få en anslutning till databasen
        Connection conn = DriverManager.getConnection(dbURL, userId, password);
        //Sätt autoCommit till false så att vi kan använda rollback osv
        conn.setAutoCommit(false);
        //returnera anslutningen
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //resultset är som en lista med rader i sql
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //sparar sakerna i databasen
    public static void commit(Connection conn) {
        try {
            if (conn != null) {
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //går tillbaka o tar bort saker som inte är sparade
    public static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //testar om jag har koppling till databasen
    public static String getDatabaseProductName(Connection connection) throws SQLException {

        DatabaseMetaData metadata = connection.getMetaData();
        return metadata.getDatabaseProductName();
    }
}
