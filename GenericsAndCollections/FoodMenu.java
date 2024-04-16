import java.util.ArrayList;
import java.util.List;

public class FoodMenu {
    private List<Food> menu;

    Food food1 = new Food("Pizza", "Delicious Margarita", 260);
    Food food2 = new Food("Dumpling", "Fluffy Dumpling", 280);
    Food food3 = new Food("Meatball Spagetti", "Meatball Spagetti with lots of sauce", 300);

    public FoodMenu() {
        this.menu = new ArrayList<>();
        menu.add(food1);
        menu.add(food2);
        menu.add(food3);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        for (Food food : menu) {
            stringBuilder.append(counter++)
                    .append(": ")
                    .append(food.toString())
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    public Food getFood(int index) {
        try {
            return this.menu.get(index-1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index Out of Bounds. " + e.getMessage());
            return null;
        }
    }

    public Food getLowestCostFood() {
        Food lowestCostFood = this.menu.get(0);

        for (Food food : this.menu) {
            if (food.getPrice() < lowestCostFood.getPrice()) {
                lowestCostFood = food;
            }
        }
        return lowestCostFood;
    }
}