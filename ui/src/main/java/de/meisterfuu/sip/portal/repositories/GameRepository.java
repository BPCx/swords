package de.meisterfuu.sip.portal.repositories;

import de.meisterfuu.sip.portal.domain.Game;

import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

/**
 * Created by meisterfuu.
 */
@Stateful
public class GameRepository extends BaseJPAService<Game> {

    public ArrayList<Game> getAllComplete() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Game> cq = cb.createQuery(entityClass);
        Root<Game> root = cq.from(Game.class);
        root.fetch("members", JoinType.INNER);
        root.fetch("webLinks", JoinType.INNER);
        root.fetch("genres", JoinType.INNER);
        cq.select(root);
        TypedQuery<Game> query = entityManager.createQuery(cq);

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
