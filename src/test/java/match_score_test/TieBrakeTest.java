package match_score_test;

import com.cofisweak.model.Match;
import com.cofisweak.model.MatchStatus;
import com.cofisweak.model.PlayerNumber;
import com.cofisweak.service.MatchScoreCalculationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TieBrakeTest {

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
    void testGameStatusIsTieBrake() {
        for (int i = 0; i < 4 * 5; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        for (int i = 0; i < 4 * 5; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }

        for (int i = 0; i < 5; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        for (int i = 0; i < 5; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }

        Assertions.assertEquals(MatchStatus.TIE_BRAKE, match.getMatchStatus());
    }

    @Test
    void testGameStatusIsTieBrakeWhenFirstPlayerHaveAdvantage() {
        for (int i = 0; i < 4 * 5; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        for (int i = 0; i < 4 * 5; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }

        for (int i = 0; i < 4; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        for (int i = 0; i < 4; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }

        for (int i = 0; i < 6; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }

        for (int i = 0; i < 6; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }

        service.countPoint(match, PlayerNumber.FIRST_PLAYER);

        Assertions.assertEquals(MatchStatus.TIE_BRAKE, match.getMatchStatus());
    }

    @Test
    void testGameStatusIsNotTieBrake() {
        for (int i = 0; i < 4 * 5; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        for (int i = 0; i < 4 * 5; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }

        for (int i = 0; i < 5; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }
        for (int i = 0; i < 5; i++) {
            service.countPoint(match, PlayerNumber.FIRST_PLAYER);
        }

        for (int i = 0; i < 8; i++) {
            service.countPoint(match, PlayerNumber.SECOND_PLAYER);
        }

        Assertions.assertEquals(MatchStatus.ONGOING, match.getMatchStatus());
    }
}
