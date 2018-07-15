package de.meisterfuu.sip.portal.repositories;

import javax.ejb.Stateful;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
public class EntityManagerProvider {

    @PersistenceContext(name = "sipPersistence", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Produces
    public EntityManager getEntityManager() {
        return entityManager;
    }

}