package facade;
import entity.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomerFacade extends AbstractFacade<Customer>{
    
    private EntityManager em;

    public CustomerFacade() {
        super(Customer.class);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV22StorePU");
        this.em = emf.createEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    
}
