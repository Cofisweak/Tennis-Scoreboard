package service.matchScoreCalculation;

import com.cofisweak.model.Match;
import com.cofisweak.model.Player;
import com.cofisweak.model.PlayerNumber;
import com.cofisweak.service.MatchScoreCalculationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PointCountTest {
    MatchScoreCalculationService service = new MatchScoreCalculationService();
    Match match;

    @BeforeEach
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
    public void whenPlayer2ScoresPointThenIncreaseScore() {
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();

        service.countPoint(match, PlayerNumber.SECOND_PLAYER);

        Assertions.assertEquals(0, getPlayerPoints(player1));
        Assertions.assertEquals(1, getPlayerPoints(player2));
    }

    @Test
    public void whenPlayer1ScoresPointThenIncreaseScore() {
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
