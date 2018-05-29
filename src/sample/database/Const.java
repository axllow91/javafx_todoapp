package sample.database;

public class Const {
    public static final String USER_TABLE = "users";
    public static final String TASK_TABLE = "tasks";

    // Column Names in table USERS
    public static final String USERS_USERID = "userid";
    public static final String USERS_FIRSTNAME = "firstname";
    public static final String USERS_LASTNAME = "lastname";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_LOCATION = "location";
    public static final String USERS_GENDER = "gender";

    // Column Names in table Tasks
    public static final String TASKS_TASKID = "taskid";
    public static final String TASKS_USERID = "userid";
    public static final String TASKS_DATECREATED = "datecreated";
    public static final String TASKS_DESCRIPTION = "description";
    public static final String TASKS_TASK = "task";
}
