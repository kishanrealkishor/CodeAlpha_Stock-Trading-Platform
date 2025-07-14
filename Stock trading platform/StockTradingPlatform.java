import java.util.*;
import java.io.*;

public class StockTradingPlatform {
    static List<Stock> market = new ArrayList<>();
    static final String FILE_NAME = "portfolio.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Add sample stocks
        market.add(new Stock("AAPL", "Apple Inc.", 180.0));
        market.add(new Stock("GOOGL", "Alphabet Inc.", 2800.0));
        market.add(new Stock("AMZN", "Amazon.com Inc.", 3300.0));
        market.add(new Stock("TSLA", "Tesla Inc.", 800.0));

        System.out.print("👤 Enter your name: ");
        String name = sc.nextLine();
        User user = new User(name, 10000.0);

        user = loadPortfolio(user);

        while (true) {
            System.out.println("\n📋 Menu:");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Save Portfolio");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> showMarket();
                case 2 -> buyStock(user);
                case 3 -> sellStock(user);
                case 4 -> user.showPortfolio(market);
                case 5 -> savePortfolio(user);
                case 6 -> {
                    savePortfolio(user);
                    System.out.println("👋 Exiting. Portfolio saved.");
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    static void showMarket() {
        System.out.println("\n📈 Market Stocks:");
        for (Stock stock : market) {
            System.out.println("- " + stock);
        }
    }

    static void buyStock(User user) {
        System.out.print("Enter stock symbol: ");
        String symbol = sc.nextLine().toUpperCase();
        Stock stock = findStock(symbol);
        if (stock == null) {
            System.out.println("❌ Stock not found.");
            return;
        }
        System.out.print("Enter quantity to buy: ");
        int qty = Integer.parseInt(sc.nextLine());
        user.buyStock(stock, qty);
    }

    static void sellStock(User user) {
        System.out.print("Enter stock symbol: ");
        String symbol = sc.nextLine().toUpperCase();
        Stock stock = findStock(symbol);
        if (stock == null) {
            System.out.println("❌ Stock not found.");
            return;
        }
        System.out.print("Enter quantity to sell: ");
        int qty = Integer.parseInt(sc.nextLine());
        user.sellStock(stock, qty);
    }

    static Stock findStock(String symbol) {
        for (Stock stock : market) {
            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }

    static void savePortfolio(User user) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            out.println(user.getName());
            out.println(user.getBalance());
            for (Map.Entry<String, Integer> entry : user.getPortfolio().entrySet()) {
                out.println(entry.getKey() + "," + entry.getValue());
            }
            System.out.println("💾 Portfolio saved.");
        } catch (IOException e) {
            System.out.println("❌ Error saving portfolio.");
        }
    }

    static User loadPortfolio(User user) {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            String name = fileScanner.nextLine();
            double balance = Double.parseDouble(fileScanner.nextLine());
            User loadedUser = new User(name, balance);
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                if (parts.length == 2) {
                    loadedUser.getPortfolio().put(parts[0], Integer.parseInt(parts[1]));
                }
            }
            System.out.println("📂 Portfolio loaded.");
            return loadedUser;
        } catch (Exception e) {
            System.out.println("📂 No previous portfolio found. Starting fresh.");
            return user;
        }
    }
              }
