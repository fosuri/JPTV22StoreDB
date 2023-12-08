package facade;
import entity.Product;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProductFacade extends AbstractFacade<Product>{
    
    private EntityManager em;

    public ProductFacade() {
        super(Product.class);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV22StorePU");
        this.em = emf.createEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    
}

