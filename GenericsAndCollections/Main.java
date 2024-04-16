import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Customer customer = new Customer("inci", 5000);
        FoodMenu menu = new FoodMenu();
        TakeOutSimulator simulator = new TakeOutSimulator(customer, menu, input);

        simulator.startTakeOutSimulator();
    }
}
