package service.matchScoreCalculation;

import com.cofisweak.model.*;
import com.cofisweak.service.MatchScoreCalculationService;
import com.cofisweak.util.MatchConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetsCountTest {

    MatchScoreCalculationService service = new MatchScoreCalculationService();

    @Test
    public void whenPlayer1HaveNecessaryScoreToWinSetWithout1PointAndScoresPointAndPlayer2HaveLessThanSixGamesThenIncreaseSet() {
        Match match = build3SetsMatch();
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();
        PlayerScore playerScore2 = match.getPlayer2().getPlayerScore();

        playerScore1.setPoints(MatchConstants.POINTS_TO_WIN_GAME - 1);
        playerScore1.setGames(MatchConstants.GAMES_TO_WIN_SET - 1);

        service.countPoint(match, PlayerNumber.FIRST_PLAYER);

        Assertions.assertEquals(1, playerScore1.getSets());
        Assertions.assertEquals(0, playerScore2.getSets());
        Assertions.assertEquals(MatchStatus.ONGOING, match.getMatchStatus());
    }

    @Test
    public void whenPlayer1Have6GamesAndPlayer2Scores6GameThenNoIncreaseSetAndStartTieBrake() {
        Match match = build3SetsMatch();
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();
        PlayerScore playerScore2 = match.getPlayer2().getPlayerScore();

        playerScore1.setGames(MatchConstants.GAMES_TO_WIN_SET - 1);
        playerScore2.setGames(MatchConstants.GAMES_TO_WIN_SET - 2);
        playerScore2.setPoints(MatchConstants.POINTS_TO_WIN_GAME - 1);

        service.countPoint(match, PlayerNumber.SECOND_PLAYER);

        Assertions.assertEquals(0, playerScore1.getSets());
        Assertions.assertEquals(0, playerScore2.getSets());
        Assertions.assertEquals(MatchStatus.TIE_BRAKE, match.getMatchStatus());
    }

    @Test
    public void whenPlayer1Scores2SetIn3SetsMatchThenGameFinish() {
        Match match = build3SetsMatch();
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();

        playerScore1.setSets(1);
        playerScore1.setGames(MatchConstants.GAMES_TO_WIN_SET - 1);
        playerScore1.setPoints(MatchConstants.POINTS_TO_WIN_GAME - 1);

        service.countPoint(match, PlayerNumber.FIRST_PLAYER);

        Assertions.assertEquals(2, playerScore1.getSets());
        Assertions.assertEquals(MatchStatus.FINISHED, match.getMatchStatus());
    }

    @Test
    public void whenPlayer1Scores3SetIn5SetsMatchThenGameFinish() {
        Match match = build5SetsMatch();
        PlayerScore playerScore1 = match.getPlayer1().getPlayerScore();

        playerScore1.setSets(2);
        playerScore1.setGames(MatchConstants.GAMES_TO_WIN_SET - 1);
        playerScore1.setPoints(MatchConstants.POINTS_TO_WIN_GAME - 1);

        service.countPoint(match, PlayerNumber.FIRST_PLAYER);

        Assertions.assertEquals(3, playerScore1.getSets());
        Assertions.assertEquals(MatchStatus.FINISHED, match.getMatchStatus());
    }

    private Match build3SetsMatch() {
        Player player1 = new Player();
        Player player2 = new Player();
        return Match.builder()
                .maxMatchSets(3)
                .player1(player1)
                .player2(player2)
                .build();
    }

    private Match build5SetsMatch() {
        Player player1 = new Player();
        Player player2 = new Player();
        return Match.builder()
                .maxMatchSets(5)
                .player1(player1)
                .player2(player2)
                .build();
    }
}
