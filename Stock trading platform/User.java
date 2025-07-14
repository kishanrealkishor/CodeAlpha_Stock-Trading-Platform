import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String name;
    private double balance;
    private Map<String, Integer> portfolio;

    public User(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
        this.portfolio = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Integer> getPortfolio() {
        return portfolio;
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (balance >= cost) {
            portfolio.put(stock.getSymbol(), portfolio.getOrDefault(stock.getSymbol(), 0) + quantity);
            balance -= cost;
            System.out.println("✅ Bought " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("❌ Insufficient balance.");
        }
    }

    public void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.getSymbol(), 0);
        if (owned >= quantity) {
            portfolio.put(stock.getSymbol(), owned - quantity);
            balance += stock.getPrice() * quantity;
            System.out.println("✅ Sold " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("❌ Not enough shares to sell.");
        }
    }

    public void showPortfolio(List<Stock> market) {
        System.out.println("\n📊 Portfolio of " + name + ":");
        double total = balance;
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            double price = findPrice(symbol, market);
            double value = price * quantity;
            total += value;
            System.out.println("- " + symbol + ": " + quantity + " shares @ $" + price);
        }
        System.out.printf("💰 Cash Balance: $%.2f\n", balance);
        System.out.printf("📈 Total Portfolio Value: $%.2f\n", total);
    }

    private double findPrice(String symbol, List<Stock> market) {
        for (Stock stock : market) {
            if (stock.getSymbol().equals(symbol)) return stock.getPrice();
        }
        return 0;
    }
}
