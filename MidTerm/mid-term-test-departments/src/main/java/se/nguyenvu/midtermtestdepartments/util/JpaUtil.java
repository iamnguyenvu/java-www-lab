package se.nguyenvu.midtermtestdepartments.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static  final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public static EntityManager getEmf() {
        return emf.createEntityManager();
    }
}
