package service.matchScoreCalculation;

import com.cofisweak.model.Match;
import com.cofisweak.model.Player;
import com.cofisweak.model.PlayerNumber;
import com.cofisweak.model.PlayerScore;
import com.cofisweak.service.MatchScoreCalculationService;
import com.cofisweak.util.MatchConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GamesCountTest {
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
    public void test_whenPlayer1Have3PointsAndScoresPointAndPlayer2NoHaveAdvantage_thenIncreaseGame() {
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();
        playerScore1.setPoints(MatchConstants.POINTS_TO_WIN_GAME - 1);

        service.countPoint(match, PlayerNumber.FIRST_PLAYER);

        Assertions.assertEquals(0, playerScore1.getPoints());
        Assertions.assertEquals(1, playerScore1.getGames());
    }

    @Test
    public void test_whenPlayer1Have3PointsAndScoresPointAndAnotherPlayerHave3Points_thenNoIncreaseGame() {
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();
        PlayerScore playerScore2 = match.getPlayer2().getPlayerScore();

        playerScore1.setPoints(MatchConstants.POINTS_TO_WIN_GAME - 1);
        playerScore2.setPoints(MatchConstants.POINTS_TO_WIN_GAME - 1);

        service.countPoint(match, PlayerNumber.FIRST_PLAYER);

        Assertions.assertEquals(4, playerScore1.getPoints());
        Assertions.assertEquals(0, playerScore1.getGames());
    }

    @Test
    public void test_whenPlayer1NoHaveAdvantageAndAnotherPlayerHaveAdvantageAndScoresPoint_thenIncreaseGame() {
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();
        PlayerScore playerScore2 = match.getPlayer2().getPlayerScore();

        playerScore1.setPoints(MatchConstants.POINTS_TO_WIN_GAME - 1);
        playerScore2.setPoints(MatchConstants.POINTS_TO_WIN_GAME);

        service.countPoint(match, PlayerNumber.SECOND_PLAYER);

        Assertions.assertEquals(0, playerScore1.getGames());
        Assertions.assertEquals(1, playerScore2.getGames());
    }
}
