package com.cofisweak.service;

import com.cofisweak.model.Player;
import com.cofisweak.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerService {
    private static final PlayerService INSTANCE = new PlayerService();

    public Player getPlayerOrCreateIfNotExists(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Player> query = session.createNamedQuery("getByName", Player.class);
            query.setParameter("name", name);

            Player player;
            List<Player> list = query.getResultList();
            if (list.isEmpty()) {
                player = Player.builder()
                        .name(name)
                        .build();
                session.persist(player);
            } else {
                player = list.get(0);
            }

            session.getTransaction().commit();
            return player;
        }
    }

    public static PlayerService getInstance() {
        return INSTANCE;
    }

}
