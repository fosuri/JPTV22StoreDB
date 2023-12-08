package tools;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EntityManagerSingleton {
    private static final EntityManagerSingleton instance = new EntityManagerSingleton();
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    private EntityManagerSingleton() {
    // Создание EntityManagerFactory (обычно, она создается один раз при запуске приложения)
        this.entityManagerFactory = Persistence.createEntityManagerFactory("JPTV22StorePU");

        // Создание EntityManager
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public static EntityManagerSingleton getInstance() {
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}