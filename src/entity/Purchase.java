package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class Purchase implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int purchasedQuantity;
    private double purchasedPrice;
    @OneToOne
    private Customer customer;
    @OneToOne
    private Product product;
    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dateOfPurchase;
    
    

    public Purchase() {
    }

    public Purchase(int purchasedQuantity, double purchasedPrice, Customer customer, Product product, LocalDate dateOfPurchase) {
        this.purchasedQuantity = purchasedQuantity;
        this.purchasedPrice = purchasedPrice;
        this.customer = customer;
        this.product = product;
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(int purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }

    public double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.purchasedQuantity;
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.purchasedPrice) ^ (Double.doubleToLongBits(this.purchasedPrice) >>> 32));
        hash = 17 * hash + Objects.hashCode(this.customer);
        hash = 17 * hash + Objects.hashCode(this.product);
        hash = 17 * hash + Objects.hashCode(this.dateOfPurchase);
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
        final Purchase other = (Purchase) obj;
        if (this.purchasedQuantity != other.purchasedQuantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.purchasedPrice) != Double.doubleToLongBits(other.purchasedPrice)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.dateOfPurchase, other.dateOfPurchase)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Purchase{" + "id=" + id + ", purchasedQuantity=" + purchasedQuantity + ", purchasedPrice=" + purchasedPrice + ", customer=" + customer + ", product=" + product + ", dateOfPurchase=" + dateOfPurchase + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}