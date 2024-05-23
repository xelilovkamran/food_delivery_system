import java.awt.*;
import java.awt.event.*;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;
import java.util.Random;

public class User extends BaseUser {
    private double balance;

    public User(Integer id, String name, String email, String password, boolean is_superuser, double balance) {
        super(id, name, email, password, is_superuser);
        this.balance = balance;
    }

    public void checkBalance() {
        System.out.println("Your balance is: $" + this.balance);
    }

    public void rechargeBalance(double amount) {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String code =  String.format("%06d", number);

        try {
            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

            TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);

            trayIcon.displayMessage("Code to recharge balance", "Enter this code to recharge: " + code, MessageType.INFO);
        } catch(Exception ex){
              System.err.print(ex);
        }

        String input = System.console().readLine("Enter the code to recharge your balance: ");

        if (input.equals(code)) {
            if (CSVOperations.updateBalance("./data/users.csv", this.getEmail(), amount)) {
                this.balance += amount;
                System.out.println("Balance recharged successfully.");
            } else {
                System.out.println("An error occurred while recharging your balance.");
            }
        } else {
            System.out.println("Invalid code.");
        }
    }
}


