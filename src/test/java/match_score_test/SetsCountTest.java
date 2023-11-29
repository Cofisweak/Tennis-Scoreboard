package match_score_test;

import com.cofisweak.model.Match;
import com.cofisweak.model.MatchStatus;
import com.cofisweak.model.PlayerNumber;
import com.cofisweak.service.MatchScoreCalculationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SetsCountTest {
    MatchScoreCalculationService service = MatchScoreCalculationService.getInstance();

    @Test
    void testSetCountWhenPlayerScores24Points() {
        Match match = Match.builder()
                .maxMatchSets(5)
                .build();

        match.startMatch();

        for (int i = 0; i < 24; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }

        TestUtils.assertPlayerScore(match.getFirstPlayerScore(), 0, 0, 0);
        TestUtils.assertPlayerScore(match.getSecondPlayerScore(), 0, 0, 1);
    }

    @Test
    void test3SetMatchEndsWhenPlayerCounts2Sets() {
        Match match = Match.builder()
                .maxMatchSets(3)
                .build();

        match.startMatch();

        for (int i = 0; i < 4 * 6 * 2; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        Assertions.assertEquals(MatchStatus.FINISHED, match.getMatchStatus());
    }

    @Test
    void test5SetMatchEndsWhenPlayerCounts3Sets() {
        Match match = Match.builder()
                .maxMatchSets(5)
                .build();

        match.startMatch();

        for (int i = 0; i < 4 * 6 * 3; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        Assertions.assertEquals(MatchStatus.FINISHED, match.getMatchStatus());
    }

    @Test
    void test3SetMatchNoEndsWhenPlayerCounts2SetsWithout1Point() {
        Match match = Match.builder()
                .maxMatchSets(3)
                .build();

        match.startMatch();

        for (int i = 0; i < 4 * 6 * 2 - 1; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        Assertions.assertNotEquals(MatchStatus.FINISHED, match.getMatchStatus());
    }

    @Test
    void test5SetMatchNoEndsWhenPlayerCounts3SetsWithout1Point() {
        Match match = Match.builder()
                .maxMatchSets(5)
                .build();

        match.startMatch();

        for (int i = 0; i < 4 * 6 * 3 - 1; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        Assertions.assertNotEquals(MatchStatus.FINISHED, match.getMatchStatus());
    }
}
