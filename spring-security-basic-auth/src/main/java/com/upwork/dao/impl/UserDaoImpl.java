package com.upwork.dao.impl;

import com.upwork.dao.UserDao;
import com.upwork.model.UserDetails;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserDetails> getUserDetails() {
        
        Session currentSession = entityManager.unwrap(Session.class);
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<UserDetails> criteria = builder.createQuery(UserDetails.class);

        Root<UserDetails> variableRoot = criteria.from(UserDetails.class);
        criteria.select(variableRoot);
        TypedQuery<UserDetails> query = currentSession.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public UserDetails findByUserName(String username) {

        Session currentSession = entityManager.unwrap(Session.class);
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<UserDetails> criteria = builder.createQuery(UserDetails.class);

        Root<UserDetails> variableRoot = criteria.from(UserDetails.class);
        criteria.select(variableRoot).where(builder.equal(variableRoot.get("username"), username));
        TypedQuery<UserDetails> query = currentSession.createQuery(criteria);
        
        List<UserDetails> users = query.getResultList();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

}
