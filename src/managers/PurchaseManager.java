package managers;

import entity.Customer;
import entity.Product;
import entity.Purchase;
import facade.PurchaseFacade;
import facade.CustomerFacade;
import java.time.Instant;
import java.util.Scanner;
import tools.InputFromKeyboard;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseManager {
    private final Scanner scanner;
    private final CustomerManager customerManager;
    private final ProductManager productManager;
    private final PurchaseFacade purchaseFacade;
    private final CustomerFacade customerFacade;

    public PurchaseManager(Scanner scanner) {
        this.scanner = scanner;
        this.customerManager = new CustomerManager(scanner);
        this.productManager = new ProductManager(scanner);
        this.purchaseFacade = new PurchaseFacade();
        this.customerFacade = new CustomerFacade();
    }
    
    public void purchaseProduct(){
        System.out.println();
        System.out.println("----------------------");
        System.out.println("| Purchase a Product |");
        System.out.println("----------------------");
        
        Purchase purchase = new Purchase();
        customerManager.displayAllCustomers();
        System.out.print("Enter customer number: ");
        int customerNumber = InputFromKeyboard.inputNumberFromRange(1, null);
        int customerId = customerNumber;
        purchase.setCustomer(customerManager.getById(customerNumber));
        Customer customer = customerManager.getById(customerId);
        productManager.displayAllProducts();
        int productId = InputFromKeyboard.inputNumberFromRange(1, null);
        Product product = productManager.getById(productId);
        purchase.setProduct(productManager.getById(productId));
        if(product.getProductQuantity()>0){
            int quantityToPurchase;
            while (true) {
                System.out.print("Enter the quantity to purchase: ");
                if (scanner.hasNextInt()) {
                    quantityToPurchase = scanner.nextInt(); scanner.nextLine();
                    if (quantityToPurchase==0) {
                        break;
                    }else if(quantityToPurchase <= 0 || quantityToPurchase > product.getProductQuantity()){
                        System.out.println("Invalid quantity. Please enter a valid quantity.");
                    }else{
                        double totalPrice = quantityToPurchase * product.getProductPrice();
                        double roundedTotalPrice = Math.round(totalPrice*100.0)/100.0;
                        if(roundedTotalPrice<=customer.getCustomerBalance()){
                            customer.setCustomerBalance(Math.round((customer.getCustomerBalance() - roundedTotalPrice)*100.0)/100.0);
                            System.out.println("Purchase successful. Total cost: " + roundedTotalPrice + " EUR");
                            product.setProductQuantity(product.getProductQuantity()-quantityToPurchase);
                            customer.setCustomerNumberOfPurchases(customer.getCustomerNumberOfPurchases()+1);
                            product.setProductRating(product.getProductRating()+quantityToPurchase);
                            customerManager.update(customer);
                            productManager.update(product);
                            
                            
                            purchase.setDateOfPurchase(new GregorianCalendar().getTime());
                            
                            purchase.setPurchasedPrice(roundedTotalPrice);
                            purchase.setPurchasedQuantity(quantityToPurchase);
                            purchaseFacade.create(purchase);
                        }else{
                            System.out.println("Not enough money to buy!");
                        }
                        break;
                    }
                }
            }
        }else{
            System.out.println("This product is completely sold out.");
        }
        
        
    }
    public int displayAllPurchases() {
        System.out.println();
        System.out.println("--------------------");
        System.out.println("| List of Products |");
        System.out.println("--------------------");
        List<Purchase> purchases = purchaseFacade.findAll();
        int count = 0;
        System.out.println("List products: ");
        for (int i = 0; i < purchases.size(); i++) {
            System.out.printf("%d. %s. %s. %s. %.2f. %d. %s%n.",
                    purchases.get(i).getId(),
                    purchases.get(i).getCustomer().getCustomerFirstname(),
                    purchases.get(i).getCustomer().getCustomerLastname(),
                    purchases.get(i).getProduct().getProductName(),
                    purchases.get(i).getPurchasedPrice(),
                    purchases.get(i).getPurchasedQuantity(),
                    purchases.get(i).getDateOfPurchase()
            );
            count++;
        }
        return count;
    }
    
    public void displayTotalPurchaseAmount(){
        List<Purchase> purchases = purchaseFacade.findAll();
        double totalTurnover = 0.0;
        double yearlyTurnover = 0.0;
        double monthlyTurnover = 0.0;
        double dailyTurnover = 0.0;
        
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        int currentDay = currentDate.getDayOfMonth();
        for (Purchase purchase : purchases) {
            double purchaseAmount = purchase.getPurchasedPrice();
            totalTurnover += purchaseAmount;

//            LocalDate purchaseDate = purchase.getDateOfPurchase();
            Instant instant = purchase.getDateOfPurchase().toInstant();
            LocalDate purchaseDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            
            int purchaseYear = purchaseDate.getYear();
            int purchaseMonth = purchaseDate.getMonthValue();
            int purchaseDay = purchaseDate.getDayOfMonth();

            if (purchaseYear == currentYear) {
                yearlyTurnover += purchaseAmount;

                if (purchaseMonth == currentMonth) {
                    monthlyTurnover += purchaseAmount;

                    if (purchaseDay == currentDay) {
                        dailyTurnover += purchaseAmount;
                    }
                }
            }
        }
        boolean repeat = true;
        do {            
            System.out.println("0. Exit");
            System.out.println("1. Total");
            System.out.println("2. For Year");
            System.out.println("3. For Month");
            System.out.println("4. For Day");
            System.out.print("Enter task number: ");
            int task = InputFromKeyboard.inputNumberFromRange(0, 14);
            
            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    System.out.println("Total Turnover: " + totalTurnover);
                    break;
                case 2:
                    System.out.println("Yearly Turnover: " + yearlyTurnover);
                    break;
                case 3:
                    System.out.println("Monthly Turnover: " + monthlyTurnover);
                    break;
                case 4:
                    System.out.println("Daily Turnover: " + dailyTurnover);
                    break;
                default:
                    break;
            }
        } while (repeat);
    }
    public void displayCustomerPurchaseRating() {
        List<Customer> customers = customerFacade.findAll();
        
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        int currentDay = currentDate.getDayOfMonth();
        
        Map<Customer, Integer> yearlyPurchaseCount = new HashMap<>();
        Map<Customer, Integer> monthlyPurchaseCount = new HashMap<>();
        Map<Customer, Integer> dailyPurchaseCount = new HashMap<>();
        
        for (Customer customer : customers) {
            int yearlyCount = 0;
            int monthlyCount = 0;
            int dailyCount = 0;
            
            List<Purchase> purchases = purchaseFacade.findAll();
            
            for (Purchase purchase : purchases) {
                Instant instant = purchase.getDateOfPurchase().toInstant();
                LocalDate purchaseDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();  
                int purchaseYear = purchaseDate.getYear();
                int purchaseMonth = purchaseDate.getMonthValue();
                int purchaseDay = purchaseDate.getDayOfMonth();
                
                if (purchaseYear == currentYear) {
                    yearlyCount++;
                    if (purchaseMonth == currentMonth) {
                        monthlyCount++;
                        if (purchaseDay == currentDay) {
                            dailyCount++;
                        }
                    }
                }
            }
            
            yearlyPurchaseCount.put(customer, yearlyCount);
            monthlyPurchaseCount.put(customer, monthlyCount);
            dailyPurchaseCount.put(customer, dailyCount);
        }
        
        System.out.println("Рейтинг покупателей за год:");
        displayCustomerRating(yearlyPurchaseCount);
        
        System.out.println("\nРейтинг покупателей за месяц:");
        displayCustomerRating(monthlyPurchaseCount);
        
        System.out.println("\nРейтинг покупателей за день:");
        displayCustomerRating(dailyPurchaseCount);
    }
    
    private void displayCustomerRating(Map<Customer, Integer> purchaseCount) {
        purchaseCount.entrySet().stream()
            .sorted(Map.Entry.<Customer, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                Customer customer = entry.getKey();
                int count = entry.getValue();
                System.out.println(customer.getCustomerFirstname() + " " + customer.getCustomerLastname() + ": " + count);
            });
    }    
    
}
