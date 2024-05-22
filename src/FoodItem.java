class FoodItem {
    private String name;
    private double price;
    private String description;
    private boolean is_available;
    private int restaurant_id;

    public FoodItem(String name, double price, String description, boolean is_available, int restaurant_id) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.is_available = is_available;
        this.restaurant_id = restaurant_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return is_available;
    }

    public int getRestaurantId() {
        return restaurant_id;
    }

    public String getFoodInfo() {
        Restaurant restaurant = CSVOperations.getRestaurantById(this.restaurant_id);
        return "\nName: " + this.name + "; Price: " + this.price + "; Description: " + this.description + "; Available: " + this.is_available + "; Restaurant: " + restaurant.getName() + "; Address: " + restaurant.getAddress() + "\n";
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailability(boolean is_available) {
        this.is_available = is_available;
    }

    public void setName(String name) {
        this.name = name;
    }

}