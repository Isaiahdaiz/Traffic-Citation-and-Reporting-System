package tcrs;

import java.sql.*;

public class AuthModel {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public User AuthenticateUser(User user) throws SQLException {
        //boolean isUserValid = false;

        String myQuery = "select * from Users where username =? and password =?";

        try (Connection conn = DatabaseUtils.getConnection()) {;

            preparedStatement = conn.prepareStatement(myQuery);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());


            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user.setStatus(resultSet.getString("Status"));
                user.setType(resultSet.getString("Type"));
                if (user.getStatus().toUpperCase().equals("ACTIVE")) {
                    user.isValid = true;
                } else user.isValid = false;
            } else {
                user.isValid = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return user;
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
