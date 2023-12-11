package managers;

import entity.Customer;
import facade.CustomerFacade;
import java.util.List;
import java.util.Scanner;
import tools.InputFromKeyboard;

public class CustomerManager {
    private final Scanner scanner;
    private final CustomerFacade customerFacade;
    
    
    public CustomerManager(Scanner scanner) {
        this.scanner = scanner;
        this.customerFacade = new CustomerFacade();
    }
    

    public void addCustomer(){
        System.out.println();
        System.out.println("------------------");
        System.out.println("| Add a new user |");
        System.out.println("------------------");
        
        Customer customer = new Customer();
        
        System.out.print("Firstname: ");
        customer.setCustomerFirstname(scanner.nextLine());
        
        System.out.print("Lastname: ");
        customer.setCustomerLastname(scanner.nextLine());
        
        System.out.print("login: ");
        customer.setCustomerLogin(scanner.nextLine());
        
        customer.setCustomerBalance(0.00);
        customer.setCustomerNumberOfPurchases(0);

        System.out.println("Added customer " + customer.toString());
        customerFacade.create(customer);            
    }
    public int displayAllCustomers(){
        System.out.println();
        System.out.println("---------------------");
        System.out.println("| List of Customers |");
        System.out.println("---------------------");
        List<Customer> customers = customerFacade.findAll();
        int count = 0;
        System.out.println("List customers: ");
        for (int i = 0; i < customers.size(); i++) {
            System.out.printf("%d. %s. %s. %s. %.2f. %d%n",
                    customers.get(i).getId(),
                    customers.get(i).getCustomerFirstname(),
                    customers.get(i).getCustomerLastname(),
                    customers.get(i).getCustomerLogin(),
                    customers.get(i).getCustomerBalance(),
                    customers.get(i).getCustomerNumberOfPurchases()
            );
            count++;
        }
        return count;
    }
//    public Customer getById(int id) {
//        return customerFacade.find((long)id);
//    }
    
    public void replenishmentOfBalance(){
        System.out.println();
        System.out.println("-----------------------------------");
        System.out.println("| Replenishment of client balance |");
        System.out.println("-----------------------------------");   
        this.displayAllCustomers();
        System.out.print("Enter the customer number: ");
        int customerNumber = InputFromKeyboard.inputNumberFromRange(1, null);
        System.out.print("Enter the deposit amount (should be greater than 0 and have 2 decimal places(#.##)): ");
        double depositAmount = -1;

        while(depositAmount<=0){
            try {
                depositAmount = Double.parseDouble(scanner.nextLine());
                if(depositAmount==0){
                    break;
                }else if (depositAmount<=0 || Math.abs(depositAmount*100 - Math.round(depositAmount*100))>0.001){
                    System.out.println("Invalid amount. Amount should be greater than 0 and have 2 decimal places.");
                    System.out.print("Enter a valid deposit amount: ");
                    depositAmount=-1;                    
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.print("Enter a valid deposit amount: ");                 
            }
        }
        Customer customer = customerFacade.find((long)customerNumber);
        customer.setCustomerBalance(Math.round((customer.getCustomerBalance()+depositAmount)*100.0)/100.0);
        customerFacade.edit(customer);
    }
    public Customer getById(int customerId){
        return customerFacade.find((long)customerId);
    }
    
    public void update(Customer customer){
        customerFacade.edit(customer);
    }
    
    public void changeCustomerDetails(){
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("| Change customer details |");
        System.out.println("---------------------------");   
        this.displayAllCustomers();
        System.out.print("Enter the customer number: ");
        int customerNumber = InputFromKeyboard.inputNumberFromRange(1, null);   
        Customer customer = customerFacade.find((long)customerNumber);
        System.out.println(customer.getCustomerFirstname()+" "+customer.getCustomerLastname());
        System.out.print("New firstname: ");
        customer.setCustomerFirstname(scanner.nextLine());
        System.out.print("New lastname: ");
        customer.setCustomerLastname(scanner.nextLine());
        customerFacade.edit(customer);
    }
    
    
}
