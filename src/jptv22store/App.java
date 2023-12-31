
package jptv22store;


import managers.ProductManager;
import managers.CustomerManager;
import java.util.Scanner;
import managers.PurchaseManager;
import managers.TimeManager;
import tools.InputFromKeyboard;

public class App {
    private final Scanner scanner;
    private final ProductManager productManager;
    private final CustomerManager customerManager;
    private final PurchaseManager purchaseManager;
    private final TimeManager timeManager;
    
    
    public App() {
        this.scanner = new Scanner(System.in);
        this.productManager = new ProductManager(scanner);
        this.customerManager = new CustomerManager(scanner);
        this.purchaseManager = new PurchaseManager(scanner);
        this.timeManager = new TimeManager();
        
    }
    
    void run() {
        System.out.println("-------------------------");
        System.out.println("| Welcome to the store! |");
        System.out.println("-------------------------");
        boolean repeat = true;
        do {
            System.out.println("--------------");
            System.out.println("| Store menu |");
            System.out.println("--------------");
            System.out.println("0. Exit");
            System.out.println("1. Add a new customer");
            System.out.println("2. Display all customers");
            System.out.println("3. Replenishment of balance");
            System.out.println("4. Change customer details");
            System.out.println("---------------------------");
            System.out.println("5. Add a new product");
            System.out.println("6. Display all products");
            System.out.println("7. Change product details");
            System.out.println("8. Product replenishment");
            System.out.println("---------------------------");
            System.out.println("9. Purchase");
            System.out.println("10. Purchased Products");
            System.out.println("11. Total sales");
            System.out.println("12. Customer rating by number of purchases");
            System.out.println("13. Product sales rating");
            System.out.println("14. Time to sales");

            System.out.print("Enter task number: ");
            int task = InputFromKeyboard.inputNumberFromRange(0, 14);
            System.out.println("Selected task is "+task+". Are you sure? Y/N");
            String continueRun = InputFromKeyboard.inputSymbolYesOrNo();
            if(continueRun.equalsIgnoreCase("n")){
                continue;
            }
            
            switch (task) {
                case 0:
                    System.out.println("You left the store.");
                    repeat = false;
                    break;
                case 1:
                    customerManager.addCustomer();
                    break;
                case 2:
                    customerManager.displayAllCustomers();
                    break;
                case 3:
                    customerManager.replenishmentOfBalance();
                    break;
                case 4:
                    customerManager.changeCustomerDetails();
                    break;
                case 5:
                    productManager.addProduct();
                    break;
                case 6:
                    productManager.displayAllProducts();
                    break;
                case 7:
                    productManager.changeProductDetails();
                    break;
                case 8:
                    productManager.ProductReplenishment();
                    break;
                case 9:
                    purchaseManager.purchaseProduct();
                    break;
                case 10:
                    purchaseManager.displayAllPurchases();
                    break;
                case 11:
                    purchaseManager.displayTotalPurchaseAmount();
                    break;
                case 12:
                    //purchaseManager.displayCustomerPurchaseRating();
//                    purchaseManager.displayYearlyCustomerPurchases();
//                    purchaseManager.displayMonthlyCustomerPurchases();
//                    purchaseManager.displayDailyCustomerPurchases();
//                    
                    purchaseManager.displayYearlyCustomerPurchasesSorted();
                    purchaseManager.displayMonthlyCustomerPurchasesSorted();
                    purchaseManager.displayDailyCustomerPurchasesSorted();
                    break;
                case 13:
                    //productManager.displayProductSalesRating();
//                    purchaseManager.displayYearlyProductSales();
//                    purchaseManager.displayMonthlyProductSales();
//                    purchaseManager.displayDailyProductSales();
                    purchaseManager.displayYearlyProductSalesSorted();
                    purchaseManager.displayMonthlyProductSalesSorted();
                    purchaseManager.displayDailyProductSalesSorted();
                    break;
                case 14:
                    timeManager.TimeTo();
                    break;
                default:
                    break;
            }           
        } while (repeat);  
    }
}
