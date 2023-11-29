package com.cofisweak.dao;

import com.cofisweak.model.Match;
import com.cofisweak.model.QMatch;
import com.cofisweak.util.HibernateUtil;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchDao {
    private static final MatchDao INSTANCE = new MatchDao();
    private static final int PAGE_SIZE = 2;

    public void persistMatch(Match match) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(match);
            session.getTransaction().commit();
        }
    }

    public static MatchDao getInstance() {
        return INSTANCE;
    }

    public List<Match> loadMatches(int page, String searchQuery) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Predicate> where = new ArrayList<>();
            if (searchQuery != null) {
                where.add(QMatch.match.player1.name.containsIgnoreCase(searchQuery));
                where.add(QMatch.match.player2.name.containsIgnoreCase(searchQuery));
            }

            return new JPAQuery<Match>(session)
                    .select(QMatch.match)
                    .from(QMatch.match)
                    .join(QMatch.match.player1)
                    .join(QMatch.match.player2)
                    .join(QMatch.match.winner)
                    .where(ExpressionUtils.anyOf(where))
                    .limit(PAGE_SIZE)
                    .offset((long) PAGE_SIZE * (page - 1))
                    .fetch();
        }
    }

    public int getPageCountByQuery(String searchQuery) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Predicate> where = new ArrayList<>();
            if (searchQuery != null) {
                where.add(QMatch.match.player1.name.containsIgnoreCase(searchQuery));
                where.add(QMatch.match.player2.name.containsIgnoreCase(searchQuery));
            }
            Long result = new JPAQuery<Long>(session)
                    .select(QMatch.match.count())
                    .from(QMatch.match)
                    .join(QMatch.match.player1)
                    .join(QMatch.match.player2)
                    .join(QMatch.match.winner)
                    .where(ExpressionUtils.anyOf(where))
                    .fetchOne();
            if (result == null) {
                return 0;
            }
            return (int) Math.ceil((double) result / PAGE_SIZE);
        }

    }
}
