package managers;

import entity.Customer;
import entity.Product;
import entity.Purchase;
import facade.PurchaseFacade;
import facade.CustomerFacade;
import facade.ProductFacade;
import java.time.Instant;
import java.util.Scanner;
import tools.InputFromKeyboard;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PurchaseManager {
    private final Scanner scanner;
    private final CustomerManager customerManager;
    private final ProductManager productManager;
    private final PurchaseFacade purchaseFacade;
    private final CustomerFacade customerFacade;
    private final ProductFacade productFacade;

    public PurchaseManager(Scanner scanner) {
        this.scanner = scanner;
        this.customerManager = new CustomerManager(scanner);
        this.productManager = new ProductManager(scanner);
        this.purchaseFacade = new PurchaseFacade();
        this.customerFacade = new CustomerFacade();
        this.productFacade = new ProductFacade();
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
                    //System.out.println("Total Turnover: " + totalTurnover);
                    System.out.println("Total Turnover: " + Math.round((totalTurnover)*100.0)/100.0);
                    break;
                case 2:
                    //System.out.println("Yearly Turnover: " + yearlyTurnover);
                    System.out.println("Yearly Turnover: " + Math.round((yearlyTurnover)*100.0)/100.0);
                    break;
                case 3:
                    //System.out.println("Monthly Turnover: " + monthlyTurnover);
                    System.out.println("Monthly Turnover: " + Math.round((monthlyTurnover)*100.0)/100.0);
                    break;
                case 4:
                    //System.out.println("Daily Turnover: " + dailyTurnover);
                    System.out.println("Daily Turnover: " + Math.round((dailyTurnover)*100.0)/100.0);
                    break;
                default:
                    break;
            }
        } while (repeat);
    }
    
    
    
    

    
    private List<Purchase> getYearlyPurchasesForProduct(Product product, int year) {
        List<Purchase> allProductPurchases = purchaseFacade.findAll();
        return allProductPurchases.stream()
                .filter(purchase -> purchase.getProduct().equals(product))
                .filter(purchase -> {
                    LocalDate purchaseDate = purchase.getDateOfPurchase().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return purchaseDate.getYear() == year;
                })
                .collect(Collectors.toList());
    }

    
    private List<Purchase> getMonthlyPurchasesForProduct(Product product, int year, int month) {
        List<Purchase> allProductPurchases = purchaseFacade.findAll();
        return allProductPurchases.stream()
                .filter(purchase -> purchase.getProduct().equals(product))
                .filter(purchase -> {
                    LocalDate purchaseDate = purchase.getDateOfPurchase().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return purchaseDate.getYear() == year && purchaseDate.getMonthValue() == month;
                })
                .collect(Collectors.toList());
    }

    
    private List<Purchase> getDailyPurchasesForProduct(Product product, int year, int month, int day) {
        List<Purchase> allProductPurchases = purchaseFacade.findAll();
        return allProductPurchases.stream()
                .filter(purchase -> purchase.getProduct().equals(product))
                .filter(purchase -> {
                    LocalDate purchaseDate = purchase.getDateOfPurchase().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return purchaseDate.getYear() == year && purchaseDate.getMonthValue() == month && purchaseDate.getDayOfMonth() == day;
                })
                .collect(Collectors.toList());
    }

    
    private int calculateTotalSales(List<Purchase> productPurchases) {
        return productPurchases.stream()
                .mapToInt(Purchase::getPurchasedQuantity)
                .sum();
    }
    
    
    public void displayYearlyProductSalesSorted() {
        List<Product> products = productFacade.findAll();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        System.out.println("Yearly Product Sales (Sorted): ");

        
        Map<String, Integer> productSalesMap = products.stream()
            .collect(Collectors.toMap(
                Product::getProductName,
                product -> calculateTotalSales(getYearlyPurchasesForProduct(product, currentYear))
            ));

        
        List<Map.Entry<String, Integer>> sortedProductSales = productSalesMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toList());

        
        sortedProductSales.forEach(entry ->
            System.out.println(entry.getKey() + ": " + entry.getValue())
        );
    }
    
    public void displayMonthlyProductSalesSorted() {
        List<Product> products = productFacade.findAll();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        System.out.println("Monthly Product Sales (Sorted): ");

        
        Map<String, Integer> productMonthlySalesMap = products.stream()
            .collect(Collectors.toMap(
                Product::getProductName,
                product -> calculateTotalSales(getMonthlyPurchasesForProduct(product, currentYear, currentMonth))
            ));

        
        List<Map.Entry<String, Integer>> sortedProductMonthlySales = productMonthlySalesMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toList());

        
        sortedProductMonthlySales.forEach(entry ->
            System.out.println(entry.getKey() + ": " + entry.getValue())
        );
    }

    public void displayDailyProductSalesSorted() {
        List<Product> products = productFacade.findAll();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        int currentDay = currentDate.getDayOfMonth();

        System.out.println("Daily Product Sales (Sorted): ");

        
        Map<String, Integer> productDailySalesMap = products.stream()
            .collect(Collectors.toMap(
                Product::getProductName,
                product -> calculateTotalSales(getDailyPurchasesForProduct(product, currentYear, currentMonth, currentDay))
            ));

        
        List<Map.Entry<String, Integer>> sortedProductDailySales = productDailySalesMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toList());

        
        sortedProductDailySales.forEach(entry ->
            System.out.println(entry.getKey() + ": " + entry.getValue())
        );
    }
    
    

   

    
    private List<Purchase> getYearlyPurchasesForCustomer(Customer customer, int year) {
        List<Purchase> allCustomerPurchases = purchaseFacade.findAll();
        return allCustomerPurchases.stream()
                .filter(purchase -> purchase.getCustomer().equals(customer))
                .filter(purchase -> {
                    LocalDate purchaseDate = purchase.getDateOfPurchase().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return purchaseDate.getYear() == year;
                })
                .collect(Collectors.toList());
    }

    
    private List<Purchase> getMonthlyPurchasesForCustomer(Customer customer, int year, int month) {
        List<Purchase> allCustomerPurchases = purchaseFacade.findAll();
        return allCustomerPurchases.stream()
                .filter(purchase -> purchase.getCustomer().equals(customer))
                .filter(purchase -> {
                    LocalDate purchaseDate = purchase.getDateOfPurchase().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return purchaseDate.getYear() == year && purchaseDate.getMonthValue() == month;
                })
                .collect(Collectors.toList());
    }

    
    private List<Purchase> getDailyPurchasesForCustomer(Customer customer, int year, int month, int day) {
        List<Purchase> allCustomerPurchases = purchaseFacade.findAll();
        return allCustomerPurchases.stream()
                .filter(purchase -> purchase.getCustomer().equals(customer))
                .filter(purchase -> {
                    LocalDate purchaseDate = purchase.getDateOfPurchase().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return purchaseDate.getYear() == year && purchaseDate.getMonthValue() == month && purchaseDate.getDayOfMonth() == day;
                })
                .collect(Collectors.toList());
    }

    
    private int calculateTotalPurchases(List<Purchase> customerPurchases) {
        return customerPurchases.size();
    }
    
    
    public void displayYearlyCustomerPurchasesSorted() {
        List<Customer> customers = customerFacade.findAll();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        System.out.println("Yearly Customer Purchases (Sorted): ");

        
        Map<Customer, Integer> customerYearlyPurchasesMap = customers.stream()
            .collect(Collectors.toMap(
                customer -> customer,
                customer -> getYearlyPurchasesForCustomer(customer, currentYear).size()
            ));

        
        List<Customer> sortedCustomersByYearlyPurchases = customerYearlyPurchasesMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        
        sortedCustomersByYearlyPurchases.forEach(customer ->
            System.out.println(customer.getCustomerFirstname() + " " + customer.getCustomerLastname() + ": " + customerYearlyPurchasesMap.get(customer))
        );
    }
   
    public void displayMonthlyCustomerPurchasesSorted() {
        List<Customer> customers = customerFacade.findAll();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        System.out.println("Monthly Customer Purchases (Sorted): ");

        
        Map<Customer, Integer> customerMonthlyPurchasesMap = customers.stream()
            .collect(Collectors.toMap(
                customer -> customer,
                customer -> getMonthlyPurchasesForCustomer(customer, currentYear, currentMonth).size()
            ));

      
        List<Customer> sortedCustomersByMonthlyPurchases = customerMonthlyPurchasesMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        
        sortedCustomersByMonthlyPurchases.forEach(customer ->
            System.out.println(customer.getCustomerFirstname() + " " + customer.getCustomerLastname() + ": " + customerMonthlyPurchasesMap.get(customer))
        );
    }

    public void displayDailyCustomerPurchasesSorted() {
        List<Customer> customers = customerFacade.findAll();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        int currentDay = currentDate.getDayOfMonth();

        System.out.println("Daily Customer Purchases (Sorted): ");

        
        Map<Customer, Integer> customerDailyPurchasesMap = customers.stream()
            .collect(Collectors.toMap(
                customer -> customer,
                customer -> getDailyPurchasesForCustomer(customer, currentYear, currentMonth, currentDay).size()
            ));

        
        List<Customer> sortedCustomersByDailyPurchases = customerDailyPurchasesMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        
        sortedCustomersByDailyPurchases.forEach(customer ->
            System.out.println(customer.getCustomerFirstname() + " " + customer.getCustomerLastname() + ": " + customerDailyPurchasesMap.get(customer))
        );
    }

    
}
