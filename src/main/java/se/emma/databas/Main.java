package se.emma.databas;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        WorkRoleDAOImpl workRoleDAOImpl = new WorkRoleDAOImpl();
        //eftersom de en databas så sparas allt efter varje körning
        workRoleDAOImpl.insertWorkRole(new WorkRole("läkare", "skapa fred i sverige", 56000, java.sql.Date.valueOf("2010-1-5")));



        //System.out.println(workRoleDAOImpl.getWorkRoleById(1));
        //WorkRole workRole = workRoleDAOImpl.getWorkRoleById(1);
        //workRole.setSalary(40000);
        //workRoleDAOImpl.updateWorkRole(1, workRole);
        //workRoleDAOImpl.deleteWorkRole(1);

        List<WorkRole> roles = workRoleDAOImpl.getAllWorkRoles();

        for (WorkRole role : roles) {
            System.out.println(role);
        }

        workRoleDAOImpl.closeConnection();

    }
}
