package de.meisterfuu.sip.portal.repositories;

import de.meisterfuu.sip.portal.domain.Member;
import de.meisterfuu.sip.portal.domain.Recipe;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 * Created by meisterfuu.
 */
@Stateless
public class RecipeRepository extends BaseJPAService<Recipe> {
    public Recipe findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
        Root<Recipe> member = criteriaQuery.from(Recipe.class);

        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.select(member).where(criteriaBuilder.equal(member.get("name"), parameter));
        Query query = entityManager.createQuery(criteriaQuery);

        query.setParameter(parameter, name);
        Recipe result = (Recipe)query.getSingleResult();
        return result;
    }
}
