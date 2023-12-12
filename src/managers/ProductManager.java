package managers;

import entity.Product;
import entity.Purchase;
import facade.ProductFacade;
import facade.PurchaseFacade;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import tools.InputFromKeyboard;

public class ProductManager {
    private final Scanner scanner;
    private final ProductFacade productFacade;
    private final PurchaseFacade purchaseFacade;

    public ProductManager(Scanner scanner) {
        this.scanner = scanner;
        this.productFacade = new ProductFacade();
        this.purchaseFacade = new PurchaseFacade();
    }
    
    public void addProduct(){
        System.out.println();
        System.out.println("---------------------");
        System.out.println("| Add a new product |");
        System.out.println("---------------------"); 
        
        Product product = new Product();
        
        System.out.print("Name: ");
        product.setProductName(scanner.nextLine());
        
        System.out.print("Type: ");
        product.setProductType(scanner.nextLine());
        
        System.out.print("Price: ");
       
        double pPrice =-1;
        while(pPrice<=0){
            try {
                pPrice = Double.parseDouble(scanner.nextLine());
                if(pPrice<=0 || Math.abs(pPrice*100 - Math.round(pPrice*100))>0.001){
                    System.out.println("Invalid price. Amount should be greater than 0 and have 2 decimal places.");
                    System.out.print("Enter a valid price: ");
                    pPrice = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.print("Enter a valid price: ");                
            }
        }
        product.setProductPrice(pPrice);
        System.out.print("Quantity: ");
        product.setProductQuantity(InputFromKeyboard.inputNumberFromRange(1, 100));
        
        product.setProductRating(0);
        System.out.println("Added product: "+product.toString());
        productFacade.create(product);
    }
    
    public int displayAllProducts() {
        System.out.println();
        System.out.println("--------------------");
        System.out.println("| List of Products |");
        System.out.println("--------------------");
        List<Product> products = productFacade.findAll();
        int count = 0;
        System.out.println("List products: ");
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. %s. %s. %.2f. %d. %d%n",
                    products.get(i).getId(),
                    products.get(i).getProductName(),
                    products.get(i).getProductType(),
                    products.get(i).getProductPrice(),
                    products.get(i).getProductQuantity(),
                    products.get(i).getProductRating()
                    
            );
            count++;
        }
        return count;
    }    
    
    public Product getById(int productId){
        return productFacade.find((long)productId);
    }
    
    public void update(Product product){
        productFacade.edit(product);
    }
    
    public void changeProductDetails(){
        System.out.println();
        System.out.println("-------------------------");
        System.out.println("| Change product details |");
        System.out.println("-------------------------");   
        this.displayAllProducts();
        System.out.print("Enter the customer number: ");
        int productNumber = InputFromKeyboard.inputNumberFromRange(1, null);   
        Product product = productFacade.find((long)productNumber);
        boolean repeat = true;
        do {
            System.out.println(product.getProductName());
            System.out.println(product.getProductType());
            System.out.println(product.getProductQuantity());
            System.out.println(product.getProductPrice()+" Eur");
            System.out.println("0. Exit");
            System.out.println("1. Name");
            System.out.println("2. Type");
            System.out.println("3. Price");
            System.out.println("4. Quantity");
            System.out.print("Enter task number: ");
            int task = InputFromKeyboard.inputNumberFromRange(0, 14);
            
            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    System.out.print("New name: ");
                    product.setProductName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("New type: ");
                    product.setProductType(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("New price: ");
                    double pPrice =-1;
                    while(pPrice<=0){
                        try {
                            pPrice = Double.parseDouble(scanner.nextLine());
                            if(pPrice<=0 || Math.abs(pPrice*100 - Math.round(pPrice*100))>0.001){
                                System.out.println("Invalid price. Amount should be greater than 0 and have 2 decimal places.");
                                System.out.print("Enter a valid price: ");
                                pPrice = -1;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            System.out.print("Enter a valid price: ");                
                        }
                    }
                    product.setProductPrice(pPrice);
                    break;
                case 4:
                    System.out.print("New quantity: ");
                    product.setProductQuantity(InputFromKeyboard.inputNumberFromRange(1, 100));
                    break;
                default:
                    break;
            }
        } while (repeat);
        productFacade.edit(product);
        
    }
    public void ProductReplenishment(){
        System.out.println();
        System.out.println("-------------------------");
        System.out.println("| Product Replenishment |");
        System.out.println("-------------------------");   
        this.displayAllProducts();
        System.out.print("Enter the customer number: ");
        int productNumber = InputFromKeyboard.inputNumberFromRange(1, null);   
        Product product = productFacade.find((long)productNumber);
        System.out.print("Quantity to add: ");
        product.setProductQuantity(product.getProductQuantity() + InputFromKeyboard.inputNumberFromRange(1, 100));  
        productFacade.edit(product);
    }

}

 