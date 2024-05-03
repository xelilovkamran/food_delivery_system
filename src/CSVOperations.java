import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVOperations {

    public static boolean itemIsAvailableInMenu(String path, String itemName) {
        List<String[]> menu = loadMenu(path);

        for (String[] item : menu) {
            if (item[1].trim().toLowerCase().equals(itemName.trim().toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static List<String[]> loadUsers(String path) {
        String line;
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");

                lines.add(cells);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines.subList(1, lines.size());
    }

    public static List<String[]> loadMenu(String path) {
        String line;
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");

                lines.add(cells);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines.subList(1, lines.size());
    }

    public static void AddLineToFile(String filePath, String line) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
