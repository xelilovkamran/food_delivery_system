import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVOperations {
    public static List<User> loadUsers(String path) {
        List<User> users = new ArrayList<>();
        List<String[]> lines = new ArrayList<>();
        lines = getFileLines(path);

        for (String[] user : lines) {
            Integer id = Integer.parseInt(user[0].trim());
            String name = user[1].trim();
            String email = user[2].trim();
            String password = user[3].trim();
            double balance = Double.parseDouble(user[4].trim());
            boolean is_superuser = user[5].trim().equals("true");
            User newUser = new User(id, name, email, password, is_superuser, balance);
            users.add(newUser);
        }

        return users;
    }

    public static User getUser(String email, String password) {
        List<User> users = loadUsers("data/users.csv");

        for (User user : users) {
            if (Validation.Authenticated(email, password, user)) {
                return user;
            }
        }

        return null;
    }

    public static Admin getAdmin(String email, String password) {
        List<User> users = loadUsers("data/users.csv");

        for (User user : users) {
            if (Validation.Authenticated(email, password, user)) {
                Integer id = user.getId();
                String name = user.getName();
                boolean is_superuser = user.isSuperuser();
                Admin newAdmin = new Admin(id, name, email, password, is_superuser);
                return newAdmin;
            }
        }

        return null;
    }

    public static boolean updatePassword(String path, String email, String newPassword) {
        List<String[]> userLines = new ArrayList<>();
        boolean updated = false;

        userLines = getFileLines(path);

        for (String[] userLine : userLines) {
            if (userLine[2].trim().equals(email)) {
                userLine[3] = newPassword;
                updated = true;
                break;
            }
        }

        CSVOperations.writeUsers(path, userLines);
        return updated;
    }

    public static boolean updateEmail(String path, String email, String newEmail) {
        List<String[]> userLines = new ArrayList<>();
        boolean updated = false;

        userLines = getFileLines(path);

        if (userIsAvailableInUsers(userLines, newEmail)) {
            System.out.println("Email already exists.");
            return updated;
        }

        for (String[] userLine : userLines) {
            if (userLine[2].trim().equals(email)) {
                userLine[2] = newEmail;
                updated = true;
                break;
            }
        }

        CSVOperations.writeUsers(path, userLines);
        return updated;
    }

    public static boolean userIsAvailableInUsers(List<String[]> userLines, String email) {
        for (String[] userLine : userLines) {
            if (userLine[2].trim().equals(email)) {
                return true;
            }
        }

        return false;
    }

    public static Menu loadMenu(String path) {
        List<FoodItem> items = new ArrayList<>();
        List<String[]> lines = new ArrayList<>();
        
        lines = getFileLines(path);


        for (String[] item : lines) {
            String name = item[1].trim();
            double price = Double.parseDouble(item[2].trim());
            String description = item[3].trim();
            boolean is_available = item[4].trim().equals("true");
            FoodItem newItem = new FoodItem(name, price, description, is_available);
            items.add(newItem);
        }

        return new Menu(items);
    }

    public static void addItemToMenu(FoodItem item, String path) {
        List<String[]> lines = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");
                if (cells[1].trim().equals(item.getName())) {
                    System.out.println("Item already exists.");
                    return;
                }

                lines.add(cells);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write((lines.size() + 1) + ", " + item.getName() + ", " + item.getPrice() + ", " + item.getDescription() + ", " + item.isAvailable());
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeItemFromMenu(String itemName, String path) {
        List<String[]> lines = new ArrayList<>();
        String line;
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");

                if (cells[1].trim().equals(itemName)) {
                    found = true;
                    continue;
                }

                lines.add(cells);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("Item not found.");
            return;
        }

        lines = lines.subList(1, lines.size());

        CSVOperations.writeMenu(path, lines);
    }

    public static void writeMenu(String path, List<String[]> menu) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("id,  name,  price,  description,  is_available");
            bw.newLine();

            for (String[] item : menu) {
                bw.write(String.join(", ", item));
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeUsers(String path, List<String[]> users) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("id,  name,  email,  password,  balance, is_superuser");
            bw.newLine();

            for (String[] user : users) {
                bw.write(String.join(", ", user));
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getFileLines(String path) {
        List<String[]> lines = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");
                lines.add(cells);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        lines = lines.subList(1, lines.size());
        
        return lines;
    }

}
