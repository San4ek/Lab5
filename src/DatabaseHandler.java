import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DatabaseHandler {

    Connection connection;
    ResultSet resSet=null;

    public Connection getDBConnection() {
        String connectionString="jdbc:mysql://localhost:3306/lab5";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection= DriverManager.getConnection(connectionString, "root", "12345678");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public ResultSet getUsers() {
        String getUser="SELECT * FROM users";
        PreparedStatement prSt= null;
        try {
            prSt = getDBConnection().prepareStatement(getUser);
            resSet= prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void insertUser(User user) {
        String insertUser="INSERT INTO users (Name, Surname, Patronymic) VALUES (?, ?, ?)";
        PreparedStatement prSt=null;
        try {
            prSt=getDBConnection().prepareStatement(insertUser);
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getSurname());
            prSt.setString(3, user.getPatronymic());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
