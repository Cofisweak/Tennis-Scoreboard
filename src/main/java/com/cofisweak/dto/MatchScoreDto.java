package com.cofisweak.dto;

import com.cofisweak.model.MatchStatus;

public record MatchScoreDto(String firstPlayerName,
                            String secondPlayerName,
                            PlayerScoreDto firstPlayerScore,
                            PlayerScoreDto secondPlayerScore,
                            MatchStatus matchStatus,
                            String winnerName) {
    public record PlayerScoreDto(String sets,
                                 String games,
                                 String points) {
    }
}
