package match_score_test;

import com.cofisweak.model.Match;
import com.cofisweak.model.PlayerNumber;
import com.cofisweak.service.MatchScoreCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointCountTest {
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
    void countPoint() {
        service.countPoint(match, PlayerNumber.SECOND_PLAYER);

        TestUtils.assertPlayerScore(match.getFirstPlayerScore(), 0, 0, 0);
        TestUtils.assertPlayerScore(match.getSecondPlayerScore(), 1, 0, 0);
    }

    @Test
    void count2Point() {
        service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        service.countPoint(match, PlayerNumber.SECOND_PLAYER);

        TestUtils.assertPlayerScore(match.getFirstPlayerScore(), 0, 0, 0);
        TestUtils.assertPlayerScore(match.getSecondPlayerScore(), 2, 0, 0);
    }
}
