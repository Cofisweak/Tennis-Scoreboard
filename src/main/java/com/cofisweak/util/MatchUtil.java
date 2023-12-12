package com.cofisweak.util;

import com.cofisweak.model.Match;
import com.cofisweak.model.MatchStatus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MatchUtil {
    public static boolean isMatchFinished(Match match) {
        return match.getMatchStatus() == MatchStatus.FINISHED;
    }

    public static boolean isTieBrake(Match match) {
        return match.getMatchStatus() == MatchStatus.TIE_BRAKE;
    }
}
