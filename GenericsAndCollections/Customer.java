public class Customer {
    private String name;
    private int money;

    public Customer(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public int getMoney(){
        return this.money;
    }

    public String getName() {
        return this.name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

}