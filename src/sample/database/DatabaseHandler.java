package sample.database;

import sample.model.Task;
import sample.model.User;

import java.sql.*;

public class DatabaseHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/"
                + dbName
                + "?useSSL=false";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    // Write
    public void signupUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "( " + Const.USERS_FIRSTNAME
                + ", " + Const.USERS_LASTNAME + ", " + Const.USERS_USERNAME
                + ", " + Const.USERS_PASSWORD + ", " + Const.USERS_LOCATION + ","
                + Const.USERS_GENDER + ")" + " VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement prstmt = getDbConnection().prepareStatement(insert);

            prstmt.setString(1, user.getFirstName());
            prstmt.setString(2, user.getLastName());
            prstmt.setString(3, user.getUsername());
            prstmt.setString(4, user.getPassword());
            prstmt.setString(5, user.getLocation());
            prstmt.setString(6, user.getGender());

            prstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // User login
    public ResultSet getUser(User user) {

        ResultSet resultSet = null;

        if (!user.getUsername().equals("") || user.getPassword().equals("")) {

            // select all from users table where username = selected user and password selected password
            String query = "SElECT * FROM " + Const.USER_TABLE + " WHERE "
                    + Const.USERS_USERNAME + "= ? " + "AND "
                    + Const.USERS_PASSWORD + " = ?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please enter your credentials");
        }
        return resultSet;
    }


    // Insert task into tb
    public void insertTask(Task task) {


        String insert = "INSERT INTO " + Const.TASK_TABLE + "(" + Const.TASKS_USERID + ", "
                + Const.TASKS_TASK + ", " + Const.TASKS_DATECREATED + ", "
                + Const.TASKS_DESCRIPTION + ") " + "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setString(2, task.getTask());
            preparedStatement.setTimestamp(3, task.getDateCreated());
            preparedStatement.setString(4, task.getDescription());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // get all tasks
    public int getAllTasks(int userId) throws SQLException, ClassNotFoundException {

        // query  the count tasks from task table where user id is selected
        String queryTask = "SELECT COUNT(*) FROM " + Const.TASK_TABLE + " WHERE "
                + Const.TASKS_USERID + "= ?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(queryTask);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            // get the column with our value(count column)
            return resultSet.getInt(1);
        }
        return resultSet.getInt(1);
    }

    // get all task by user
    public ResultSet getTasksByUser(int userId) {

        ResultSet resultSet = null;
        String query = "SELECT * FROM " + Const.TASK_TABLE + " WHERE "
                + Const.TASKS_USERID + "= ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }


    // delete task
    public void deleteTask(int userId, int taskId) {
        String query = "DELETE FROM " + Const.TASK_TABLE + " WHERE "
                + Const.TASKS_USERID + "=?" +  " AND " + Const.TASKS_TASKID +  "=?";

        try(PreparedStatement preparedStatement = getDbConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, taskId);

            // execute() for delete/update
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(Timestamp datecreated, String description, String task, int taskId) {

        // String query = "UPDATE " + Const.TASK_TABLE + " SET " + datecreated + "=?, " +  description + "=?," + task + "=? WHERE " + taskId + "=?";

        String query = "UPDATE tasks SET datecreated=?, description=?, task=? WHERE taskid=?";

        // I don't need to close preparedStatement because try-resources closes automatically for me
        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(query)) {

            preparedStatement.setTimestamp(1, datecreated);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, task);
            preparedStatement.setInt(4, taskId);


            // executing the update method
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    // Read
}
