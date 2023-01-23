package order;

import java.util.ArrayList;

public class Order {
    private static final String fluo = "61c0c5a71d1f82001bdaaa6d";
    private static final String meatOfClams = "61c0c5a71d1f82001bdaaa6f";
    private final ArrayList<Object> ingredients;

    public Order(ArrayList<Object> ingredients) {
        this.ingredients = ingredients;
    }

    public static Order getDefaultOrder() {
        ArrayList<Object> order = new ArrayList<>();
        order.add(fluo);
        order.add(meatOfClams);
        return new Order(order);
    }

    public static Order getIncorrectOrder() {
        ArrayList<Object> order = new ArrayList<>();
        order.add("order1");
        order.add("order2");
        return new Order(order);
    }
}
