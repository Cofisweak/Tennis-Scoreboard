package com.cofisweak.service;

import com.cofisweak.model.Player;
import com.cofisweak.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerService {
    private static final PlayerService INSTANCE = new PlayerService();

    public Player getPlayerOrCreateIfNotExists(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Player player;
            try {
                session.beginTransaction();
                player = Player.builder()
                        .name(name)
                        .build();
                session.persist(player);
                session.getTransaction().commit();
            } catch (ConstraintViolationException ex) {
                session.getTransaction().rollback();

                String hql = "SELECT p FROM Player p WHERE p.name = :name";
                Query<Player> query = session.createQuery(hql, Player.class);
                query.setParameter("name", name);
                player = query.getSingleResult();
            }
            return player;
        }
    }

    public static PlayerService getInstance() {
        return INSTANCE;
    }

}
