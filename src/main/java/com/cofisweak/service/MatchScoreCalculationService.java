package com.cofisweak.service;

import com.cofisweak.model.Match;
import com.cofisweak.model.MatchStatus;
import com.cofisweak.model.PlayerNumber;
import com.cofisweak.model.PlayerScore;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchScoreCalculationService {
    private static final MatchScoreCalculationService INSTANCE = new MatchScoreCalculationService();

    private static final int POINTS_TO_WIN_GAME = 4;
    private static final int POINTS_TO_WIN_TIE_BRAKE = 7;
    private static final int GAMES_TO_WIN_SET = 7;
    private static final int GAMES_TO_TIE_BRAKE = 6;

    public void countPoint(Match match, PlayerNumber playerNumber) {
        if(match.getMatchStatus() == MatchStatus.FINISHED)
            return;

        PlayerScore playerScore;
        if (playerNumber == PlayerNumber.FIRST_PLAYER) {
            playerScore = match.getFirstPlayerScore();
        } else {
            playerScore = match.getSecondPlayerScore();
        }

        if (match.getMatchStatus() == MatchStatus.TIE_BRAKE) {
            countPointIfTieBrake(match, playerScore);
            return;
        }

        playerScore.incrementPoints();
        if (playerScore.getPoints() >= POINTS_TO_WIN_GAME && match.getPointsDifference() >= 2) {
            match.resetPoints();
            countGame(match, playerScore);
        }
    }

    private void countPointIfTieBrake(Match match, PlayerScore playerScore) {
        playerScore.incrementPoints();
        if (playerScore.getPoints() >= POINTS_TO_WIN_TIE_BRAKE && match.getPointsDifference() >= 2) {
            match.resetPoints();
            match.setMatchStatus(MatchStatus.ONGOING);
            countGame(match, playerScore);
        }
    }

    private void countGame(Match match, PlayerScore playerScore) {
        playerScore.incrementGames();
        int games = playerScore.getGames();

        if (games == GAMES_TO_TIE_BRAKE) {
            int diff = match.getGamesDifference();
            if (diff == 0) {
                match.setMatchStatus(MatchStatus.TIE_BRAKE);
            } else if (diff >= 2) {
                match.resetGames();
                countSet(match, playerScore);
            }
        } else if (games == GAMES_TO_WIN_SET) {
            match.resetGames();
            countSet(match, playerScore);
        }
    }

    private void countSet(Match match, PlayerScore playerScore) {
        playerScore.incrementSets();
        int sets = playerScore.getSets();

        if (sets >= match.getMaxMatchSets() / 2 + 1) {
            match.setMatchStatus(MatchStatus.FINISHED);
            match.setWinner(playerScore.getPlayer());
        }
    }

    public static MatchScoreCalculationService getInstance() {
        return INSTANCE;
    }
}
