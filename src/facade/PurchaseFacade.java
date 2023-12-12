/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facade;

import entity.Purchase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class PurchaseFacade extends AbstractFacade<Purchase>{
    EntityManager em;

    public PurchaseFacade() {
        super(Purchase.class);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV22StorePU");
        this.em = emf.createEntityManager();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    

//    public List<Purchase> findHistoryToReadingBooks() {
//        try {
////            return em.createQuery("SELECT history FROM History history WHERE history.returnBook = null")
////                .getResultList();
//            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//            CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
//            Root<Purchase> historyRoot = criteriaQuery.from(Purchase.class);
//
//            criteriaQuery.select(historyRoot);
//            criteriaQuery.where(criteriaBuilder.isNull(historyRoot.get("returnBook")));
//
//            return em.createQuery(criteriaQuery).getResultList();
//        } catch (Exception e) {
//            return new ArrayList<>();
//        }
//    }
}
