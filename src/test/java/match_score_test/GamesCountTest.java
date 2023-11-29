package match_score_test;

import com.cofisweak.model.Match;
import com.cofisweak.model.PlayerNumber;
import com.cofisweak.service.MatchScoreCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GamesCountTest {
    MatchScoreCalculationService service = MatchScoreCalculationService.getInstance();
    Match match;

    @BeforeEach
    void setUp() {
        match = Match.builder()
                .maxMatchSets(5)
                .build();
        match.startMatch();
    }

    @Test
    void testGameCountWhenPlayerScoresFivePoints() {
        for (int i = 0; i < 5; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }

        TestUtils.assertPlayerScore(match.getFirstPlayerScore(), 0, 0, 0);
        TestUtils.assertPlayerScore(match.getSecondPlayerScore(), 1, 1, 0);
    }

    @Test
    void testGameCountWhenPlayerScoresFourPoints() {
        for (int i = 0; i < 4; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }

        TestUtils.assertPlayerScore(match.getFirstPlayerScore(), 0, 0, 0);
        TestUtils.assertPlayerScore(match.getSecondPlayerScore(), 0, 1, 0);
    }

    @Test
    void testGameNotCountWhenTwoPlayersScoresFivePoints() {
        for (int i = 0; i < 3; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        for (int i = 0; i < 3; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }
        service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        service.countPoint(match, PlayerNumber.SECOND_PLAYER);


        TestUtils.assertPlayerScore(match.getFirstPlayerScore(), 4, 0, 0);
        TestUtils.assertPlayerScore(match.getSecondPlayerScore(), 4, 0, 0);
    }

    @Test
    void testGameNotCountWhenSecondPlayerHaveAdvantage() {
        for (int i = 0; i < 3; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        for (int i = 0; i < 3; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }
        service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        service.countPoint(match, PlayerNumber.SECOND_PLAYER);


        TestUtils.assertPlayerScore(match.getFirstPlayerScore(), 4, 0, 0);
        TestUtils.assertPlayerScore(match.getSecondPlayerScore(), 5, 0, 0);
    }

    @Test
    void testGameCountWhenSecondPlayerHaveAdvantageAndScoresPoint() {
        for (int i = 0; i < 3; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        for (int i = 0; i < 3; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }
        service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        for (int i = 0; i < 3; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }

        TestUtils.assertPlayerScore(match.getFirstPlayerScore(), 0, 0, 0);
        TestUtils.assertPlayerScore(match.getSecondPlayerScore(), 0, 1, 0);
    }
}
