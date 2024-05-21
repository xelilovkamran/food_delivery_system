import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends BaseUser {
    public Admin(Integer id, String name, String email, String password, boolean is_superuser) {
        super(id, name, email, password, is_superuser);
    }
}
