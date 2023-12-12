package com.cofisweak.service;

import com.cofisweak.model.*;
import com.cofisweak.util.MatchConstants;
import com.cofisweak.util.MatchUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchScoreCalculationService {
    private static final MatchScoreCalculationService INSTANCE = new MatchScoreCalculationService();

    public void countPoint(Match match, PlayerNumber playerNumber) {
        if(MatchUtil.isMatchFinished(match))
            return;

        Player scoringPlayer = getScoringPlayer(match, playerNumber);

        if (MatchUtil.isTieBrake(match)) {
            countPointIfTieBrake(match, scoringPlayer);
            return;
        }

        incrementPoints(scoringPlayer);
        if (isGameCompleted(match, scoringPlayer)) {
            resetPoints(match);
            countGame(match, scoringPlayer);
        }
    }

    private void countPointIfTieBrake(Match match, Player scoringPlayer) {
        incrementPoints(scoringPlayer);
        if (isTieBrakeCompleted(match, scoringPlayer)) {
            resetPoints(match);
            match.setMatchStatus(MatchStatus.ONGOING);
            countGame(match, scoringPlayer);
        }
    }

    private void countGame(Match match, Player scoringPlayer) {
        incrementGames(scoringPlayer);
        int games = scoringPlayer.getPlayerScore().getGames();

        if (games == MatchConstants.GAMES_TO_TIE_BRAKE) {
            checkIsTieBrake(match, scoringPlayer);
        } else if (games == MatchConstants.GAMES_TO_WIN_SET) {
            resetGames(match);
            countSet(match, scoringPlayer);
        }
    }

    private void checkIsTieBrake(Match match, Player scoringPlayer) {
        int diff = getGamesDifference(match);
        if (diff == 0) {
            match.setMatchStatus(MatchStatus.TIE_BRAKE);
        } else if (diff >= 2) {
            resetGames(match);
            countSet(match, scoringPlayer);
        }
    }

    private void countSet(Match match, Player scoringPlayer) {
        incrementSets(scoringPlayer);
        int sets = scoringPlayer.getPlayerScore().getSets();

        if (isMatchCompleted(match, sets)) {
            match.setMatchStatus(MatchStatus.FINISHED);
            match.setWinner(scoringPlayer);
        }
    }

    private int getPointsDifference(Match match) {
        return Math.abs(match.getPlayer2().getPlayerScore().getPoints() - match.getPlayer1().getPlayerScore().getPoints());
    }

    private int getGamesDifference(Match match) {
        return Math.abs(match.getPlayer2().getPlayerScore().getGames() - match.getPlayer1().getPlayerScore().getGames());
    }

    private void resetPoints(Match match) {
        match.getPlayer1().getPlayerScore().setPoints(0);
        match.getPlayer2().getPlayerScore().setPoints(0);
    }

    private void resetGames(Match match) {
        match.getPlayer1().getPlayerScore().setGames(0);
        match.getPlayer2().getPlayerScore().setGames(0);
    }

    private void incrementPoints(Player scoringPlayer) {
        int points = scoringPlayer.getPlayerScore().getPoints();
        scoringPlayer.getPlayerScore().setPoints(points + 1);
    }

    private void incrementGames(Player scoringPlayer) {
        int games = scoringPlayer.getPlayerScore().getGames();
        scoringPlayer.getPlayerScore().setGames(games + 1);
    }

    private void incrementSets(Player scoringPlayer) {
        int sets = scoringPlayer.getPlayerScore().getSets();
        scoringPlayer.getPlayerScore().setSets(sets + 1);
    }

    private boolean isGameCompleted(Match match, Player scoringPlayer) {
        return scoringPlayer.getPlayerScore().getPoints() >= MatchConstants.POINTS_TO_WIN_GAME && getPointsDifference(match) >= 2;
    }

    private boolean isTieBrakeCompleted(Match match, Player scoringPlayer) {
        return scoringPlayer.getPlayerScore().getPoints() >= MatchConstants.POINTS_TO_WIN_TIE_BRAKE && getPointsDifference(match) >= 2;
    }

    private boolean isMatchCompleted(Match match, int sets) {
        return sets >= match.getMaxMatchSets() / 2 + 1;
    }

    private Player getScoringPlayer(Match match, PlayerNumber playerNumber) {
        return switch (playerNumber) {
            case FIRST_PLAYER -> match.getPlayer1();
            case SECOND_PLAYER -> match.getPlayer2();
        };
    }

    public static MatchScoreCalculationService getInstance() {
        return INSTANCE;
    }
}
