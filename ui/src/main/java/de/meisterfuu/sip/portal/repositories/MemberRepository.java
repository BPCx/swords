package de.meisterfuu.sip.portal.repositories;

import de.meisterfuu.sip.portal.domain.Member;

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
public class MemberRepository extends BaseJPAService<Member> {
    public Member findByUsername(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
        Root<Member> member = criteriaQuery.from(Member.class);

        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.select(member).where(criteriaBuilder.equal(member.get("name"), parameter));
        Query query = entityManager.createQuery(criteriaQuery);

        query.setParameter(parameter, name);
        Member result = (Member)query.getSingleResult();
        return result;
    }
}
