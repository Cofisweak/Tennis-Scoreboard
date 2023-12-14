package service.matchScoreCalculation;

import com.cofisweak.model.*;
import com.cofisweak.service.MatchScoreCalculationService;
import com.cofisweak.util.MatchConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TieBrakeTest {

    MatchScoreCalculationService service = new MatchScoreCalculationService();
    Match match;

    @BeforeEach
    void setUp() {
        Player player1 = new Player();
        Player player2 = new Player();
        match = Match.builder()
                .maxMatchSets(3)
                .player1(player1)
                .player2(player2)
                .build();
    }

    @Test
    void whenPlayer2HaveNecessaryPointsToWinTieBrakeWithout1PointAndScoresPointThenTieBrakeFinishAndScoresSet() {
        match.setMatchStatus(MatchStatus.TIE_BRAKE);
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();
        PlayerScore playerScore2 = match.getPlayer2().getPlayerScore();

        playerScore1.setGames(MatchConstants.GAMES_TO_TIE_BRAKE);
        playerScore2.setGames(MatchConstants.GAMES_TO_TIE_BRAKE);
        playerScore2.setPoints(MatchConstants.POINTS_TO_WIN_TIE_BRAKE - 1);

        service.countPoint(match, PlayerNumber.SECOND_PLAYER);

        Assertions.assertEquals(1, playerScore2.getSets());
        Assertions.assertNotEquals(MatchStatus.TIE_BRAKE, match.getMatchStatus());
    }

    @Test
    void whenPlayer1HaveNecessaryPointsToWinTieBrakeWithout1PointAndScoresPointAndPlayer2HaveSameScoreThenTieBrakeNoFinish() {
        match.setMatchStatus(MatchStatus.TIE_BRAKE);
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();
        PlayerScore playerScore2 = match.getPlayer2().getPlayerScore();

        playerScore1.setGames(MatchConstants.GAMES_TO_TIE_BRAKE);
        playerScore2.setGames(MatchConstants.GAMES_TO_TIE_BRAKE);
        playerScore1.setPoints(MatchConstants.POINTS_TO_WIN_TIE_BRAKE - 1);
        playerScore2.setPoints(MatchConstants.POINTS_TO_WIN_TIE_BRAKE - 1);

        service.countPoint(match, PlayerNumber.FIRST_PLAYER);

        Assertions.assertEquals(MatchStatus.TIE_BRAKE, match.getMatchStatus());
    }

    @Test
    void whenPlayer1HaveAdvantageAndScoresPointThenTieBrakeFinishAndScoresSet() {
        match.setMatchStatus(MatchStatus.TIE_BRAKE);
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();
        PlayerScore playerScore2 = match.getPlayer2().getPlayerScore();

        playerScore1.setGames(MatchConstants.GAMES_TO_TIE_BRAKE);
        playerScore2.setGames(MatchConstants.GAMES_TO_TIE_BRAKE);
        playerScore1.setPoints(MatchConstants.POINTS_TO_WIN_TIE_BRAKE);
        playerScore2.setPoints(MatchConstants.POINTS_TO_WIN_TIE_BRAKE - 1);

        service.countPoint(match, PlayerNumber.FIRST_PLAYER);

        Assertions.assertEquals(1, playerScore1.getSets());
        Assertions.assertNotEquals(MatchStatus.TIE_BRAKE, match.getMatchStatus());
    }
}
