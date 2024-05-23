import java.util.List;

public class Cart {
    private List<CartItem> items;
    private int id;

    public Cart(List<CartItem> items, int id) {
        this.items = items;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (CartItem item : items) {
            sb.append(index++ + " " + item.toString());
            sb.append("\n");
        }
        sb.append("Total: $" + getTotalPrice());
        return sb.toString();
    }
}