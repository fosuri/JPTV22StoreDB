package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerFirstname;
    private String customerLastname;
    private String customerLogin;
    private double customerBalance;
    private int customerNumberOfPurchases;

    public Customer() {
    }

    public Customer(Long id, String customerFirstname, String customerLastname, String customerLogin, double customerBalance, int customerNumberOfPurchases) {
        this.id = id;
        this.customerFirstname = customerFirstname;
        this.customerLastname = customerLastname;
        this.customerLogin = customerLogin;
        this.customerBalance = customerBalance;
        this.customerNumberOfPurchases = customerNumberOfPurchases;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.customerFirstname);
        hash = 43 * hash + Objects.hashCode(this.customerLastname);
        hash = 43 * hash + Objects.hashCode(this.customerLogin);
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.customerBalance) ^ (Double.doubleToLongBits(this.customerBalance) >>> 32));
        hash = 43 * hash + this.customerNumberOfPurchases;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (Double.doubleToLongBits(this.customerBalance) != Double.doubleToLongBits(other.customerBalance)) {
            return false;
        }

        if (!Objects.equals(this.customerNumberOfPurchases, other.customerNumberOfPurchases)) {
            return false;
        }
        if (!Objects.equals(this.customerFirstname, other.customerFirstname)) {
            return false;
        }
        if (!Objects.equals(this.customerLastname, other.customerLastname)) {
            return false;
        }
        if (!Objects.equals(this.customerLogin, other.customerLogin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", customerFirstname=" + customerFirstname + ", customerLastname=" + customerLastname + ", customerLogin=" + customerLogin + ", customerBalance=" + customerBalance + ", customerNumberOfPurchases=" + customerNumberOfPurchases + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerFirstname(String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public String getCustomerLogin() {
        return customerLogin;
    }

    public void setCustomerLogin(String customerLogin) {
        this.customerLogin = customerLogin;
    }

    public double getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(double customerBalance) {
        this.customerBalance = customerBalance;
    }

    public int getCustomerNumberOfPurchases() {
        return customerNumberOfPurchases;
    }

    public void setCustomerNumberOfPurchases(int customerNumberOfPurchases) {
        this.customerNumberOfPurchases = customerNumberOfPurchases;
    }

    
}