import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

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

    public void withdrawBalance(double amount) {
        if (this.balance >= amount) {
            if (CSVOperations.updateBalance("./data/users.csv", this.getEmail(), -amount)) {
                this.balance -= amount;
                System.out.println("Balance withdrawn successfully.");
            } else {
                System.out.println("An error occurred while withdrawing your balance.");
            }
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void placeOrder(Cart cart) {
        System.out.println("Enter delivery address:");
        String address = System.console().readLine();

        System.out.println("If you have any special instructions, please enter them below: (if not, type .)");
        String instructions = System.console().readLine();

        
        if (CSVOperations.placeOrder("./data/order.csv", cart, this, address, instructions)) {
            this.withdrawBalance(cart.getTotalPrice());
            cart.setItems(new ArrayList<CartItem>());
            System.out.println("Order placed successfully.");
        } else {
            System.out.println("An error occurred while placing the order.");
        }
    }

    public void viewOrderHistory() {
        List<String[]> orders = CSVOperations.getFileLines("./data/order.csv");

        for (String[] order : orders) {
            if (this.getId() == Integer.parseInt(order[0])) {
                System.out.println("************************************");
                System.out.println("Order ID: " + order[0]);
                System.out.println("Order Date: " + order[9]);
                System.out.println("Delivery Address: " + order[4]);
                System.out.println("Item: " + order[1]);
                System.out.println("quantity: " + order[2]);
                System.out.println("Total Price: " + order[3] + "$");
                if (!order[8].trim().equals(".")) {
                    System.out.println("Instructions: " + order[8]);
                } 
                System.out.println("************************************");
            }
        }
    }
}


