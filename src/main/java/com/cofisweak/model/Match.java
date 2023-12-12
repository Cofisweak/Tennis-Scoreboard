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
    @Builder.Default
    private MatchStatus matchStatus = MatchStatus.ONGOING;

    @Transient
    private int maxMatchSets;
}
