/**
 * Java core pro. Home Work 2.
 * @author Ievlev Andrey
 * @version 25 Fab, 2018
 * @Link https://github.com/OLskrain/HMjavaProLesson2.git
 */
import java.sql.*;

public class HMLesson2 implements Commands{
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static final String GOODS ="Товар";

    public static void main(String[] args) {
        try {
            connect();
            dropTable();
            createTable();
            //clearTable();
            addGoods();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            disconnect();
        }
    }

    private static void connect()throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:Javaprolesson2_BD.db");
        statement = connection.createStatement();
    }
    private static void createTable() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS goods (id INTEGER PRIMARY KEY AUTOINCREMENT, prodid INTEGER, title TEXT, cost INTEGER);");
    }
    private static void clearTable()throws SQLException{
        statement.execute("DELETE FROM goods");
    }
    private static void dropTable()throws SQLException{
        statement.execute("DROP TABLE goods");
    }
    private static void addGoods()throws SQLException{
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO goods(prodid, title , cost) VALUES (?,?,?);");
        for (int i = 0; i <10000 ; i++) {
            preparedStatement.setInt(1, i+1);
            preparedStatement.setString(2, GOODS + (i+1));
            preparedStatement.setInt(3, (i+1)*10);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.commit();
    }
    public static void disconnect() {
        try{
            statement.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
