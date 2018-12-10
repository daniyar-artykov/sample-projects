package com.upwork.dao.impl;

import com.upwork.dao.TourDao;
import com.upwork.model.Tour;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniyar.artykov
 */
@Repository
public class TourDaoImpl implements TourDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tour> findByName(String name) {
        Session currentSession = entityManager.unwrap(Session.class);
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Tour> criteria = builder.createQuery(Tour.class);

        Root<Tour> root = criteria.from(Tour.class);
        criteria.select(root).where(builder.like(builder.lower(root.get("name")),
                "%" + name.toLowerCase() + "%")); // ignore case
        TypedQuery<Tour> query = currentSession.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    @Transactional
    public int deleteAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaDelete<Tour> criteria = builder.createCriteriaDelete(Tour.class);

        Root<Tour> root = criteria.from(Tour.class);
        return currentSession.createQuery(criteria).executeUpdate();
    }

    @Override
    @Transactional
    public void merge(Tour tour) {
        entityManager.merge(tour);
    }

    @Override
    public List<Tour> retrieveAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Tour> criteria = builder.createQuery(Tour.class);

        Root<Tour> root = criteria.from(Tour.class);
        criteria.select(root);
        TypedQuery<Tour> query = currentSession.createQuery(criteria);

        return query.getResultList();
    }

}
