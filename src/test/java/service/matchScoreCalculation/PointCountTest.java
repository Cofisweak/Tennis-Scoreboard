package service.matchScoreCalculation;

import com.cofisweak.model.Match;
import com.cofisweak.model.Player;
import com.cofisweak.model.PlayerNumber;
import com.cofisweak.service.MatchScoreCalculationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PointCountTest {
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
    public void test_whenPlayer2ScoresPoint_thenIncreaseScore() {
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();

        service.countPoint(match, PlayerNumber.SECOND_PLAYER);

        Assertions.assertEquals(0, getPlayerPoints(player1));
        Assertions.assertEquals(1, getPlayerPoints(player2));
    }

    @Test
    public void test_whenPlayer1ScoresPoint_thenIncreaseScore() {
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();

        service.countPoint(match, PlayerNumber.FIRST_PLAYER);

        Assertions.assertEquals(1, getPlayerPoints(player1));
        Assertions.assertEquals(0, getPlayerPoints(player2));
    }

    private int getPlayerPoints(Player player) {
        return player.getPlayerScore().getPoints();
    }
}