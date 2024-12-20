package se.emma.databas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkRoleDAOImplTest {

    //tar bort hela table och resettar den
    @AfterEach
    public void cleanUp() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement("DROP TABLE IF EXISTS work_role");
        //ta bort work role-tabellen
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        //stäng ned resurser
            JDBCUtil.closeStatement(stmt);
            JDBCUtil.closeConnection(conn);
        }
    }
    @Test
    void insertWorkRoleTest() throws SQLException {

        WorkRoleDAOImpl workRoleDAO = new WorkRoleDAOImpl();
        workRoleDAO.insertWorkRole(new WorkRole("lärare", "lära ut", 30000, java.sql.Date.valueOf("2022-4-17")));

        int size = workRoleDAO.getAllWorkRoles().size();
        Assertions.assertEquals(1, size);
    }
}
