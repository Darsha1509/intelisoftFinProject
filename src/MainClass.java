import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Dasha on 30.06.2017.
 */
public class MainClass {
    public static void main(String[] args) throws SQLException {

        User authUser = WorkWithDatabase.autirizFromDataBase();

        selectMenu(authUser);

    }

    public static void selectMenu(User user) throws SQLException {
        System.out.println("1.Входящие\n2.Отправленные\n3.Отправить сообщение\n4.Список всех пользователей\n5.Выйдти");
        Scanner num = new Scanner(System.in);
        int i = num.nextInt();
        switch (i) {

            case 1:
                WorkWithDatabase.inputMessageFromDatabase(user);
                selectMenu(user);
                break;

            case 2:
                WorkWithDatabase.outputMessageFromDatabase (user);
                selectMenu(user);
                break;

            case 3:
                WorkWithDatabase.addDataTableMessage(user);
                selectMenu(user);
                break;

            case 4:

                WorkWithDatabase.selectDataTableUsers();
                selectMenu(user);
                break;

            case 5:
                user = null;

                user = WorkWithDatabase.autirizFromDataBase();
                selectMenu(user);
                break;
        }
    }

}
