package com.cofisweak.service;

import com.cofisweak.model.Player;
import com.cofisweak.model.QPlayer;
import com.cofisweak.util.HibernateUtil;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

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
                player = new JPAQuery<Player>(session)
                        .select(QPlayer.player)
                        .from(QPlayer.player)
                        .where(QPlayer.player.name.eq(name))
                        .fetchFirst();
            }
            return player;
        }
    }

    public static PlayerService getInstance() {
        return INSTANCE;
    }

}
