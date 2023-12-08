package managers;

import entity.Product;
import facade.ProductFacade;
import java.util.List;
import java.util.Scanner;
import tools.InputFromKeyboard;

public class ProductManager {
    private final Scanner scanner;
    private final ProductFacade productFacade;

    public ProductManager(Scanner scanner) {
        this.scanner = scanner;
        this.productFacade = new ProductFacade();
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
    
}