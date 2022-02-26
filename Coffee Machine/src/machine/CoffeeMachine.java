package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static Scanner scanner = new Scanner(System.in);
    public static int money = 550;
    public static int water = 400;
    public static int milk = 540;
    public static int coffeeBeans = 120;
    public static int disposableCups = 9;

    public static void main(String[] args) {
        boolean shouldContinue = true;

        while (shouldContinue) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = scanner.nextLine().toLowerCase();
            switch (action) {
                case("buy"):
                    if (buy()) {
                        System.out.println();
                        break;
                    } else {
                        continue;
                    }
                case ("fill"):
                    fill();
                    break;
                case("take"):
                    take();
                    break;
                case("remaining"):
                    coffeeMachineState();
                    System.out.println();
                    break;
                case("exit"):
                    shouldContinue = false;
            }
        }
    }

    enum Coffee {
        ESPRESSO(250, 0, 16, 1,  4),
        LATTE(350, 75, 20, 1, 7),
        CAPPUCCINO(200, 100, 12, 1, 6);

        final int WATER;
        final int MILK;
        final int COFFEE_BEANS;
        final int CUP;
        final int MONEY;

        Coffee(int WATER, int MILK, int COFFEE_BEANS, int CUP, int MONEY) {
            this.WATER = WATER;
            this.MILK = MILK;
            this.COFFEE_BEANS = COFFEE_BEANS;
            this.CUP = CUP;
            this.MONEY = MONEY;
        }

        public int getWATER() {
            return WATER;
        }

        public int getMILK() {
            return MILK;
        }

        public int getCOFFEE_BEANS() {
            return COFFEE_BEANS;
        }

        public int getCUP() {
            return CUP;
        }

    }

    public static void coffeeMachineState() {
        System.out.println("The coffee machine has:\n" +
                water + " ml of water\n" +
                milk + " ml of milk\n" +
                coffeeBeans + " g of coffee beans\n" +
                disposableCups + " disposable cups\n" +
                "$" + money + " of money");
    }

    public static boolean buy() {
        boolean back = false;
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        String userChoice = scanner.nextLine();
        switch (userChoice) {
            case("1"):
                if (supplyCheck(Coffee.ESPRESSO)) {
                    water -= 250;
                    coffeeBeans -= 16;
                    money += 4;
                    disposableCups -= 1;
                }
                break;
            case("2"):
                if (supplyCheck(Coffee.LATTE)) {
                    water -= 350;
                    milk -= 75;
                    coffeeBeans -= 20;
                    money += 7;
                    disposableCups -= 1;
                }
                break;
            case("3"):
                if (supplyCheck(Coffee.CAPPUCCINO)) {
                    water -= 200;
                    milk -= 100;
                    coffeeBeans -= 12;
                    money += 6;
                    disposableCups -= 1;
                }
                break;
            case("back"):
                back = true;
        }
        return back;
    }

    public static void fill() {
        System.out.println("Write how many ml of water you want to add:");
        int waterAdd = scanner.nextInt();
        water += waterAdd;
        System.out.println("Write how many ml of milk you want to add:");
        int milkAdd = scanner.nextInt();
        milk += milkAdd;
        System.out.println("Write how many grams of coffee beans you want to add:");
        int beansAdd = scanner.nextInt();
        coffeeBeans += beansAdd;
        System.out.println("Write how many disposable cups of coffee you want to add:");
        int cupsAdd = scanner.nextInt();
        disposableCups += cupsAdd;
        scanner.nextLine();
    }

    public static void take() {
        System.out.println("I gave you $" + money);
        money = 0;
    }

    public static boolean supplyCheck(Coffee type) {
        int waterRequired = type.getWATER();
        int milkRequired = type.getMILK();
        int beansRequired = type.getCOFFEE_BEANS();
        int cupRequired = type.getCUP();

        if (waterRequired > water) {
            System.out.println("Sorry, not enough water!");
        } else if (milkRequired > milk) {
            System.out.println("Sorry, not enough milk!");
        } else if (beansRequired > coffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (cupRequired > disposableCups) {
            System.out.println("Sorry, not enough cups!");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            return true;
        }
        return false;
    }
}
