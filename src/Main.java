import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main implements Commands{
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement ps;
    private static boolean status = false;
    private static ArrayList<String> com;
    private static String[] elements;
    public static void main(String[] args){
        try{
            connect();

            System.out.println("Введите команду для совершения действия или введите " + '"' + SHOW_COMMANDS + '"' + ", чтобы узнать список команд.");
        while (true){
            System.out.println("Введите команду: ");
           inscan();
        switch (elements[0]){
            case (PRICE):
                System.out.println("Чтобы узнать цену на товар, ведите имя товара: ");
                inscan();
                getPrice(elements[0]);
                break;
            case (CHANGE_PRICE):
                System.out.println("Чтобы изменить цену на товар, ведите имя товара и новую цену: ");
                inscan();
                updateData(elements[0],elements[1]);
                break;
            case (GOODS_BY_PRICE):
                System.out.println("Чтобы посмотреть товары из диапазона цен, введите начальное и конечное значения: ");
                inscan();
                getData(elements[0],elements[1]);
                break;
            case (SHOW_COMMANDS):
                System.out.println("Cписок команд: ");
                break;
            case (END):
                System.out.println("До свидания!");
                status = true;
                break;
                default:
                    System.out.println("Такой команды нет. Попробуйте ещё раз или введите " + '"' + SHOW_COMMANDS + '"' + ", чтобы узнать список команд.");
                    break;
        }if(status)
            break;
        }
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            disconnect();
        }
    }
    public static String [] inscan(){
        Scanner sc = new Scanner(System.in);
        String in = sc.nextLine();
        elements = in.split(" ");
        return elements;
    }
    public static void getData(String costm1, String costm2) throws SQLException{
        String str = "SELECT * FROM goods WHERE cost BETWEEN '"+costm1+"' AND '"+costm2+"' ";
        ResultSet resultSet = statement.executeQuery(str);
        while(resultSet.next())
            System.out.println(resultSet.getInt(1) + " " + resultSet.getString("prodid")+ " " + resultSet.getString("title")+ " " + resultSet.getString("cost"));
    }


    public static void getPrice(String titleGoods) throws SQLException{ //РАЗОБРАТЬСЯ ЕСЛИ НЕТ ТОВАРА!!!!!!!!!!
         PreparedStatement ps = connection.prepareStatement("SELECT cost FROM goods WHERE title = ?") ;
         ps.setString(1,titleGoods);
         ResultSet resultSet = ps.executeQuery();
         while(true)if(resultSet.next()){
             System.out.println("Цена товара равна " + resultSet.getString("cost") + " y.e.");
             break;
         }else {
             System.out.println("Такого товара нет!");
             break;
         }
    }
    public static void updateData(String titlem, String costm) throws SQLException{
        String str = "UPDATE goods SET cost = "+ costm +" WHERE title ='"+titlem+"'";
        statement.executeUpdate(str);
        getPrice(titlem);
    }
    public static void connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:Javaprolesson2_BD.db");
        statement = connection.createStatement();
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