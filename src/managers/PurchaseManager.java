package managers;

import entity.Customer;
import entity.Product;
import entity.Purchase;
import facade.PurchaseFacade;
import java.util.Scanner;
import tools.InputFromKeyboard;
import java.time.LocalDate;
import java.util.List;

public class PurchaseManager {
    private final Scanner scanner;
    private final CustomerManager customerManager;
    private final ProductManager productManager;
    private final PurchaseFacade purchaseFacade;

    public PurchaseManager(Scanner scanner) {
        this.scanner = scanner;
        this.customerManager = new CustomerManager(scanner);
        this.productManager = new ProductManager(scanner);
        this.purchaseFacade = new PurchaseFacade();
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
                            purchase.setDateOfPurchase(LocalDate.now());
                            purchase.setPurchasedPrice(roundedTotalPrice);
                            purchase.setPurchasedQuantity(quantityToPurchase);
                            purchaseFacade.create(purchase);
                        }
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
            System.out.printf("%d. %s. %s. %s. %.2f. %d%n.",
                    purchases.get(i).getId(),
                    purchases.get(i).getCustomer().getCustomerFirstname(),
                    purchases.get(i).getCustomer().getCustomerLastname(),
                    purchases.get(i).getProduct().getProductName(),
                    purchases.get(i).getPurchasedPrice(),
                    purchases.get(i).getPurchasedQuantity()
            );
            count++;
        }
        return count;
    } 
}
