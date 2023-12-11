package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String productType;
    private double productPrice;
    private int productQuantity;
    private int productRating;



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.productName);
        hash = 13 * hash + Objects.hashCode(this.productType);
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.productPrice) ^ (Double.doubleToLongBits(this.productPrice) >>> 32));
        hash = 13 * hash + this.productQuantity;
        hash = 13 * hash + this.productRating;
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
        final Product other = (Product) obj;
        if (Double.doubleToLongBits(this.productPrice) != Double.doubleToLongBits(other.productPrice)) {
            return false;
        }
        if (this.productQuantity != other.productQuantity) {
            return false;
        }
        if (this.productRating != other.productRating) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", productName=" + productName + ", productType=" + productType + ", productPrice=" + productPrice + ", productQuantity=" + productQuantity + ", productRating=" + productRating + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductRating() {
        return productRating;
    }

    public void setProductRating(int productRating) {
        this.productRating = productRating;
    }


    
}