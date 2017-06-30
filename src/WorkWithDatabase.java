import java.sql.*;
import java.util.Scanner;

/**
 * Created by Dasha on 30.06.2017.
 */
public class WorkWithDatabase {
    public static Connection getDBConnection(){
        //устанавливаем путь к драйверу
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("This driver doesn't instal on your computer");
            return null;
        }

        //подключаемся к драйверу
        Connection dbConnection = null;
        try{
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_kokorina","root","da6555354");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }


    //добавление данных в таблицу users
    public static void addDataTable() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;

        String test = "insert into users(Name, password) values ('Rita','das')";

        try{
            dbConnection = WorkWithDatabase.getDBConnection();
            statement =   dbConnection.createStatement();
            statement.execute(test);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            if(dbConnection !=null)
                dbConnection.close();
            if(statement != null)
                statement.close();
        }
    }


    //список всех пользователей
    public static void selectDataTableUsers() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;

        try{
            dbConnection = WorkWithDatabase.getDBConnection();
            statement =   dbConnection.createStatement();
            ResultSet rs =statement.executeQuery("select id, Name from users");
            while (rs.next()) {
                System.out.println(rs.getString("id") + " " + rs.getString("Name"));


            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            if(dbConnection !=null)
                dbConnection.close();
            if(statement != null)
                statement.close();
        }
    }


    //авторизация
    public static User autirizFromDataBase() throws SQLException{

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter your login:\n");
        String log = scanner.nextLine();
        System.out.println("Please, enter your password:\n");
        String pass = scanner.nextLine();

        User user = new User();
        Connection dbConnection = null;
        Statement statement = null;
        try{
            dbConnection = WorkWithDatabase.getDBConnection();
            statement =   dbConnection.createStatement();
            ResultSet rs =statement.executeQuery("select id, Name, password from users");
            while (rs.next()) {
                if(log.equals(rs.getString("Name"))  &&  pass.equals(rs.getString("password"))){
                    user.setId(rs.getInt("id"));
                    user.setLogin(rs.getString("Name"));
                    user.setPassword(rs.getString("password"));
                    System.out.println("вы авторизовались");
                    return user;
                }


            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            if(dbConnection !=null)
                dbConnection.close();
            if(statement != null)
                statement.close();
        }

        return registration();
    }


    //отправка сообщения
    public static void addDataTableMessage(User user) throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter forWhomId");
        int forWhomId = sc.nextInt();
        System.out.println("Enter text of message");
        Scanner sc1 = new Scanner(System.in);
        String text = sc1.nextLine();


        String test = "insert into messages(text, fromWhomId, forWhomId) values ('"+text+"', "+user.getId()+", "+forWhomId+")";

        try{
            dbConnection = WorkWithDatabase.getDBConnection();
            statement =   dbConnection.createStatement();
            statement.execute(test);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            if(dbConnection !=null)
                dbConnection.close();
            if(statement != null)
                statement.close();
        }
    }


    //список входящих сообщений
    public static void inputMessageFromDatabase(User user) throws SQLException{

        Connection dbConnection = null;
        Statement statement = null;
        try{
            dbConnection = WorkWithDatabase.getDBConnection();
            statement =   dbConnection.createStatement();
            ResultSet rs =statement.executeQuery("select id, text, fromWhomId, forWhomId from messages where forWhomId ="+user.getId());
            while (rs.next()) {
                System.out.println("MessageId: "+rs.getString("id") + " Text: " + rs.getString("text") + " FromWhomId: " + rs.getString("fromWhomId"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            if(dbConnection !=null)
                dbConnection.close();
            if(statement != null)
                statement.close();
        }
    }

    //список исходящих сообщений
    public static void outputMessageFromDatabase(User user) throws SQLException{

        Connection dbConnection = null;
        Statement statement = null;
        try{
            dbConnection = WorkWithDatabase.getDBConnection();
            statement =   dbConnection.createStatement();
            ResultSet rs =statement.executeQuery("select id, text, fromWhomId, forWhomId from messages where fromWhomId ="+user.getId());
            while (rs.next()) {
                System.out.println("MessageId: "+rs.getString("id") + " Text: " + rs.getString("text") + " ForWhomId: " + rs.getString("forWhomId"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            if(dbConnection !=null)
                dbConnection.close();
            if(statement != null)
                statement.close();
        }
    }

    //регистрация
    public static User registration() throws SQLException{
        User user = new User();
        System.out.println("Хотите зарегистрироваться?");
        System.out.println("1.Да\n2.Нет");
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        switch (i) {
            case 1:
                Connection dbConnection = null;
                Statement statement = null;


                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите логин");
                String login = scanner.nextLine();

                if (idetifire(login)) {
                    registration();
                }

                System.out.println("Введите пароль");
                String password = scanner.nextLine();

                String test = "insert into users(name, password) values ('" + login + "','" + password + "')";

                try {
                    dbConnection = WorkWithDatabase.getDBConnection();
                    statement = dbConnection.createStatement();
                    statement.execute(test);
                    System.out.println("Вы зарегистрированы");
                    autirizFromDataBase();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } finally {
                    if (dbConnection != null)
                        dbConnection.close();
                    if (statement != null)
                        statement.close();
                }
                break;

            case 2:
                autirizFromDataBase();
                break;
        }
        return user;
    }




    //сравнение на идентичность логина
    public static boolean idetifire(String login) throws SQLException{

        Connection dbConnection = null;
        Statement statement = null;

        try{
            dbConnection = WorkWithDatabase.getDBConnection();
            statement =   dbConnection.createStatement();
            ResultSet rs =statement.executeQuery("select Name from users");
            while (rs.next()) {
                if(login.equals(rs.getString("Name"))){
                    System.out.println("Введенный вами логин уже существует");
                    return true;
                }

            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            if(dbConnection !=null)
                dbConnection.close();
            if(statement != null)
                statement.close();
        }
        return false;
    }

    public static void menuReg(){

    }
}
