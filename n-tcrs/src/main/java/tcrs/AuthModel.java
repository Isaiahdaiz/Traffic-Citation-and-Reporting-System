package tcrs;

import java.sql.*;

public class AuthModel {
//    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/project?useSSL=false";
//    private static final String DATABASE_USERNAME = "root";
//    private static final String DATABASE_PASSWORD = "test";
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public boolean AuthenticateUser(String username, String password) throws SQLException {
        boolean isUserValid = false;

        String myQuery = "select Username from Users where username =? and password =?";

        try (Connection conn = DatabaseUtils.getConnection()) {;

            preparedStatement = conn.prepareStatement(myQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);


            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return false;
            } else isUserValid = true;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return isUserValid;
    }

//    public static void printSQLException(SQLException ex) {
//        for (Throwable e: ex) {
//            if (e instanceof SQLException) {
//                e.printStackTrace(System.err);
//                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
//                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
//                System.err.println("Message: " + e.getMessage());
//                Throwable t = ex.getCause();
//                while (t != null) {
//                    System.out.println("Cause: " + t);
//                    t = t.getCause();
//                }
//            }
//        }
//    }

}
