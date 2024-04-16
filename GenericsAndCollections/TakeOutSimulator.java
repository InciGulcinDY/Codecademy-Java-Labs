import java.util.HashMap;
import java.util.Scanner;

public class TakeOutSimulator {
    private Customer customer;
    private FoodMenu menu;
    private Scanner input;

    public TakeOutSimulator(Customer customer, FoodMenu menu, Scanner input) {
        this.customer = customer;
        this.menu = menu;
        this.input = input;
    }

    private <T> T getOutputOnIntInput(String userInputPrompt, IntUserInputRetriever<T> intUserInputRetriever) {
        int userInput = 0;

        while(true) {
            System.out.println(userInputPrompt);
            try {
                userInput = input.nextInt();
                return intUserInputRetriever.produceOutputOnIntUserInput(userInput);
            } catch (IllegalArgumentException e) {
                System.out.println(userInput + " is not a valid input. Try Again!");
                System.out.println("Input needs to be an 'int' type.");
            }
        }

    }

    public boolean shouldSimulate() {
        String userPrompt = "Enter 1 to CONTINUE simulation or 0 to EXIT program: ";

        IntUserInputRetriever<Boolean> userInputRetriever = selection -> {
            Food lowestCostFood = menu.getLowestCostFood();
            if (selection == 1 && this.customer.getMoney() >= lowestCostFood.getPrice()) {
                return true;
            } else if (selection == 0 || this.customer.getMoney() < lowestCostFood.getPrice()) {
                return false;
            } else {
                throw new IllegalArgumentException("Invalid input. Please enter either 0 or 1.");
            }
        };

       return getOutputOnIntInput(userPrompt, userInputRetriever);
    }

    public boolean isStillOrderingFood() {
        String userPrompt = "Enter 1 to CONTINUE shopping or 0 to CHECKOUT: ";

        IntUserInputRetriever<Boolean> userInputRetriever = selection -> {
            if(selection == 1) {
                return true;
            } else if (selection == 0) {
                return false;
            } else {
                throw new IllegalArgumentException("Invalid input. Please enter either 0 or 1.");
            }
        };
        return getOutputOnIntInput(userPrompt, userInputRetriever);
    }

    public void checkOutCustomer(ShoppingBag<Food> shoppingBag) {
        System.out.println("Processing Payment...");
        System.out.println("Your remaining money: $" + this.customer.getMoney());
        System.out.println("Thank you and enjoy your food!");
    }

    public void takeOutPrompt() {
        ShoppingBag<Food> shoppingBag = new ShoppingBag<>();
        int customerMoneyLeft = this.customer.getMoney();

        while(true) {
            System.out.println("You have $" + customerMoneyLeft + " left to spend");
            System.out.println("Today's menu options!");
            System.out.println(this.menu.toString());

            System.out.print("Choose a menu item!: ");
            int index = this.input.nextInt();
            Food food = this.menu.getFood(index);

            if(customerMoneyLeft >= food.getPrice()) {
                shoppingBag.addItem(food);
                customerMoneyLeft -= food.getPrice();
                this.customer.setMoney(customerMoneyLeft);
            } else {
                System.out.println("Oops! Looks like you don't have enough for that. Choose another item or checkout.");
            }
            boolean stillOrdering = isStillOrderingFood();
            if(!stillOrdering) {
                checkOutCustomer(shoppingBag);
                break;
            }
        }
    }

    public void startTakeOutSimulator() {
        System.out.println("Hello, welcome to my restaurant!");
        System.out.println("Welcome " + this.customer.getName());

        boolean isSimulating = shouldSimulate();
        while (isSimulating) {
            takeOutPrompt();
        }

    }

}