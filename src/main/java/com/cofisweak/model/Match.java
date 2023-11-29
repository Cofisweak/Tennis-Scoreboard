package com.cofisweak.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "player1", nullable = false)
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2", nullable = false)
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner", nullable = false)
    private Player winner;

    @Transient
    @Setter(AccessLevel.PRIVATE)
    private PlayerScore firstPlayerScore;

    @Transient
    @Setter(AccessLevel.PRIVATE)
    private PlayerScore secondPlayerScore;

    @Transient
    private MatchStatus matchStatus;

    @Transient
    private int maxMatchSets;

    public void resetPoints() {
        firstPlayerScore.resetPoints();
        secondPlayerScore.resetPoints();
    }

    public void resetGames() {
        firstPlayerScore.resetGames();
        secondPlayerScore.resetGames();
    }

    public void startMatch() {
        firstPlayerScore = new PlayerScore();
        firstPlayerScore.setPlayer(player1);
        secondPlayerScore = new PlayerScore();
        secondPlayerScore.setPlayer(player2);

        matchStatus = MatchStatus.ONGOING;
    }

    public int getPointsDifference() {
        return Math.abs(secondPlayerScore.getPoints() - firstPlayerScore.getPoints());
    }

    public int getGamesDifference() {
        return Math.abs(secondPlayerScore.getGames() - firstPlayerScore.getGames());
    }
}
