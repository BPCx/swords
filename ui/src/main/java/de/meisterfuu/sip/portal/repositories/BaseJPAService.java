package de.meisterfuu.sip.portal.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 * Created by meisterfuu.
 */
public class BaseJPAService<T> {

    @Inject
    EntityManager entityManager;

    protected Class<T> entityClass;

    public BaseJPAService(){
        //Get the current class
        Class<?> currentClass = getClass();

        //Check if the super class has a generic parameter
        while (!(currentClass.getGenericSuperclass() instanceof ParameterizedType)) {

            //Provide error if Object.class is reached
            if (currentClass == Object.class) {
                throw new UnsupportedOperationException("No generic entity type is provided in your inheritance chain");
            }

            //Get next higher super class
            currentClass = currentClass.getSuperclass();
        }

        ParameterizedType typeParameter = (ParameterizedType) currentClass.getGenericSuperclass();

        entityClass = (Class<T>) typeParameter.getActualTypeArguments()[0];
    }

    public T create(final T t) {
        this.entityManager.persist(t);
        return t;
    }

    public void delete(final Object id) {
        this.entityManager.remove(this.entityManager.getReference(entityClass, id));
    }

    public T find(final Object id) {
        return (T) this.entityManager.find(entityClass, id);
    }

    public T update(final T t) {
        return this.entityManager.merge(t);
    }

    public ArrayList<T> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        TypedQuery<T> query = entityManager.createQuery(cq);

        try {
            return new ArrayList<>(query.getResultList());
        } catch (final NoResultException nre) {
            return new ArrayList<>();
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
