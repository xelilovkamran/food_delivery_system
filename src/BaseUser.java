public class BaseUser {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean is_superuser;

    public BaseUser(Integer id, String name, String email, String password, boolean is_superuser) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.is_superuser = is_superuser;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSuperuser() {
        return is_superuser;
    }
    
    public String getUserInfo() {
        return "\nName: " + this.name + "\nEmail: " + this.email + "\nPassword: " + this.password;
    }

    /**
     * @param password the new password
     * @return void
     * 
     * This method updates the password of the user and updates the csv file
     */
    public void setPassword(String password) {
        if (CSVOperations.updatePassword("./data/users.csv", this.email, password)) {
            System.out.println("Password updated successfully");
        } else {
            System.out.println("Password update failed");
            return;
        }
        this.password = password;
    }

    /**
     * @param email the new email
     * @return void
     * 
     * This method updates the email of the user and updates the csv file
     */
    public void setEmail(String email) {
        if (CSVOperations.updateEmail("./data/users.csv", this.email, email)) {
            System.out.println("Email updated successfully");
        } else {
            System.out.println("Email update failed");
            return;
        }
        this.email = email;
    }
}
