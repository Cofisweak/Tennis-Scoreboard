package match_score_test;

import com.cofisweak.model.PlayerScore;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {
    public static void assertPlayerScore(PlayerScore playerScore, int expectedPoints, int expectedGames, int expectedSets) {
        Assertions.assertEquals(expectedPoints, playerScore.getPoints());
        Assertions.assertEquals(expectedGames, playerScore.getGames());
        Assertions.assertEquals(expectedSets, playerScore.getSets());
    }
}
