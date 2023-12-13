package service.matchScoreCalculation;

import com.cofisweak.model.*;
import com.cofisweak.service.MatchScoreCalculationService;
import com.cofisweak.util.MatchConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TieBrakeTest {

    MatchScoreCalculationService service = new MatchScoreCalculationService();
    Match match;

    @Before
    public void setUp() {
        Player player1 = new Player();
        Player player2 = new Player();
        match = Match.builder()
                .maxMatchSets(3)
                .player1(player1)
                .player2(player2)
                .build();
    }

    @Test
    public void test_whenPlayer2HaveNecessaryPointsToWinTieBrakeWithout1PointAndScoresPoint_thenTieBrakeFinishAndScoresSet() {
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
    public void test_whenPlayer1HaveNecessaryPointsToWinTieBrakeWithout1PointAndScoresPointAndPlayer2HaveSameScore_thenTieBrakeNoFinish() {
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
    public void test_whenPlayer1HaveAdvantageAndScoresPoint_thenTieBrakeFinishAndScoresSet() {
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
