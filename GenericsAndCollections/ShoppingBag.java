import java.util.HashMap;
import java.util.Map;

public class ShoppingBag<T extends PricedItem<Integer>> {
    private Map<T, Integer> shoppingBag;

    public ShoppingBag() {
        shoppingBag = new HashMap<>();
    }

    public void addItem(T item) {
        Integer count = shoppingBag.getOrDefault(item, 0);
        count++;
        shoppingBag.put(item, count);
    }

    public Integer getTotalPrice() {
        Integer totalPrice = 0;

        for (Map.Entry<T, Integer> entry : shoppingBag.entrySet()) {
            T item = entry.getKey();
            Integer count = entry.getValue();
            Integer itemPrice = item.getPrice() * count;
            totalPrice += itemPrice;
        }

        return totalPrice;
    }

}