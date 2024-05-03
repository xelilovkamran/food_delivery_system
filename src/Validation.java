import java.util.List;
import java.util.Scanner;

public class Validation {
    public static boolean isAdmin() {
        Scanner scanner = new Scanner(System.in);
        String email = null; 
        String password = null;

        System.out.print("Enter email: ");
        email = scanner.next();
        System.out.print("Enter password: ");
        password = scanner.next();
        // scanner.close();
        
        List<String[]> users = CSVOperations.loadUsers("data/users.csv");


        for (String[] user : users) {
            String userEmail = user[2].trim().substring(1, user[2].length() - 2);
            String userPassword = user[3].trim().substring(1, user[3].length() - 2);
            String is_superuser = user[5].trim();
            if (userEmail.equals(email) && userPassword.equals(password) && is_superuser.equals("true")) {
                return true;
            }
        }

        return false;
    }
}
