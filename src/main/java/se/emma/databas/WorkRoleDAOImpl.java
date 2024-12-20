package se.emma.databas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkRoleDAOImpl implements WorkRoleDAO {

    private Connection connection;

    //konstruktor, hämtar min connection
    public WorkRoleDAOImpl() {
        try {
            connection = JDBCUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){
        JDBCUtil.closeConnection(connection);
    }

    @Override
    public void insertWorkRole(WorkRole workRole) throws SQLException {

        //preparedstatement e för frågetecken annars ett vanligt statement för sql
        String sql = "INSERT INTO work_role (title, description, salary, creation_date)" +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;

        //försöker sätta värden på objekten
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, workRole.getTitle());
            preparedStatement.setString(2, workRole.getDescription());
            preparedStatement.setDouble(3, workRole.getSalary());
            preparedStatement.setDate(4, workRole.getCreationDate());

            preparedStatement.executeUpdate();
            JDBCUtil.commit(connection);

        } catch (SQLException e) {
            JDBCUtil.rollback(connection);
            e.printStackTrace();
            throw e;

        } finally {
            JDBCUtil.closeStatement(preparedStatement);
        }
    }

    @Override
    public ArrayList<WorkRole> getAllWorkRoles() throws SQLException {

        String sql = "SELECT * FROM work_role";
        PreparedStatement preparedStatement = null;
        ArrayList<WorkRole> workRoleArrayList = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            //omvandlar från resultset till en faktisk lista med workroles
            while (resultSet.next()){

                workRoleArrayList.add(new WorkRole(
                        resultSet.getInt("role_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDouble("salary"),
                        resultSet.getDate("creation_date")));
            }

        } catch (SQLException e){

            e.printStackTrace();
            throw e;

        } finally {
            JDBCUtil.closeResultSet(resultSet);
            JDBCUtil.closeStatement(preparedStatement);
        }
        return workRoleArrayList;
    }

    @Override
    public WorkRole getWorkRoleById(int id) throws SQLException {

        WorkRole workRole = null;
        String sql = "SELECT * FROM work_role WHERE role_id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int roleId = resultSet.getInt("role_id");
                String tile = resultSet.getString("title");
                String description = resultSet.getString("description");
                double salary = resultSet.getDouble("salary");
                java.sql.Date creationDate = resultSet.getDate("creation_date");

                //vi behöver ju returnera en workrole och dessa värden är samma juu
                workRole = new WorkRole(roleId, tile, description, salary, creationDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;

        }finally {
            JDBCUtil.closeResultSet(resultSet);
             JDBCUtil.closeStatement(preparedStatement);
        }
        //returnerar rätt workrole
        return workRole;
    }

    @Override
    public void updateWorkRole(int id, WorkRole newWorkRole) throws SQLException {

        String sql = "UPDATE work_role SET title = ?, description = ?, salary = ?, creation_date = ? WHERE role_id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newWorkRole.getTitle());
            preparedStatement.setString(2, newWorkRole.getDescription());
            preparedStatement.setDouble(3, newWorkRole.getSalary());
            preparedStatement.setDate(4, newWorkRole.getCreationDate());
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
            JDBCUtil.commit(connection);

        } catch (SQLException e) {
            JDBCUtil.rollback(connection);
            e.printStackTrace();
            throw e;
        }
        finally {
            JDBCUtil.closeStatement(preparedStatement);
        }
    }        

    @Override
    public void deleteWorkRole(int id) throws SQLException {

        String sql = "DELETE FROM work_role WHERE role_id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            //i uppdaterande metoder måste vi ha commit, inte i querys som bara hämtar
            preparedStatement.executeUpdate();
            JDBCUtil.commit(connection);

        } catch (SQLException e) {
            JDBCUtil.rollback(connection);
            e.printStackTrace();
            throw e;
        }
        finally {
            JDBCUtil.closeStatement(preparedStatement);
        }
    }
}