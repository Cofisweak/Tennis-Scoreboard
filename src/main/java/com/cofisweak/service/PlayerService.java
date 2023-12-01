package com.cofisweak.service;

import com.cofisweak.model.Player;
import com.cofisweak.model.QPlayer;
import com.cofisweak.util.HibernateUtil;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerService {
    private static final PlayerService INSTANCE = new PlayerService();

    public Player getPlayerOrCreateIfNotExists(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Player player = new JPAQuery<Player>(session)
                    .select(QPlayer.player)
                    .from(QPlayer.player)
                    .where(QPlayer.player.name.eq(name))
                    .fetchFirst();

            if (player == null) {
                player = Player.builder()
                        .name(name)
                        .build();
                session.persist(player);
            }
            session.getTransaction().commit();
            return player;
        }
    }

    public static PlayerService getInstance() {
        return INSTANCE;
    }

}
