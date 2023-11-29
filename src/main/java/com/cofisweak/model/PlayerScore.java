package com.cofisweak.model;

import lombok.Data;

@Data
public class PlayerScore {
    private Player player;
    private int points;
    private int games;
    private int sets;

    public void incrementPoints() {
        points++;
    }

    public void incrementGames() {
        games++;
    }
    public void incrementSets() {
        sets++;
    }

    public void resetPoints() {
        points = 0;
    }

    public void resetGames() {
        games = 0;
    }
}
