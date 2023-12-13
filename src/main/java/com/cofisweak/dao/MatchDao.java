package com.cofisweak.dao;

import com.cofisweak.model.Match;
import com.cofisweak.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class MatchDao {
    private static final int PAGE_SIZE = 2;

    public void persistMatch(Match match) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(match);
            session.getTransaction().commit();
        }
    }

    public List<Match> loadMatches(int page, String searchQuery) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String whereClause = buildWhereClause(searchQuery);
            String hql = "FROM Match WHERE " + whereClause;
            Query<Match> query = session.createQuery(hql, Match.class);
            if (searchQuery != null) {
                query.setParameter("name", "%" + searchQuery.trim() + "%");
            }
            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult((page - 1) * PAGE_SIZE);
            return query.list();
        }
    }

    public int getPageCountByQuery(String searchQuery) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String whereClause = buildWhereClause(searchQuery);
            String hql = "SELECT COUNT(m) FROM Match m WHERE " + whereClause;
            Query<Long> query = session.createQuery(hql, Long.class);
            if (searchQuery != null) {
                query.setParameter("name", "%" + searchQuery.trim() + "%");
            }
            Long result = query.uniqueResult();
            if (result == null) {
                return 0;
            }
            return (int) Math.ceil((double) result / PAGE_SIZE);
        }
    }

    private String buildWhereClause(String searchQuery) {
        if (searchQuery == null) {
            return "1 = 1";
        } else {
            return "player1.name ILIKE :name OR player2.name ILIKE :name";
        }
    }
}
