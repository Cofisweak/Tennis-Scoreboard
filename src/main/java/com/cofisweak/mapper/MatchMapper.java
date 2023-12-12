package com.cofisweak.mapper;

import com.cofisweak.dto.MatchScoreDto;
import com.cofisweak.model.Match;
import com.cofisweak.dto.MatchDto;
import com.cofisweak.model.Player;
import com.cofisweak.util.MatchUtil;
import com.cofisweak.util.Utils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchMapper {
    public static List<MatchDto> mapToMatchesDto(List<Match> matches) {
        List<MatchDto> matchDtoList = new ArrayList<>();
        for (Match match : matches) {
            matchDtoList.add(
                    new MatchDto(
                            match.getPlayer1().getName(),
                            match.getPlayer2().getName(),
                            match.getWinner().getName()));
        }
        return matchDtoList;
    }

    public static MatchScoreDto mapToMatchScoreDto(Match match) {
        int firstPlayerPoints = match.getPlayer1().getPlayerScore().getPoints();
        int secondPlayerPoints = match.getPlayer2().getPlayerScore().getPoints();

        String firstPlayerPointsString = String.valueOf(firstPlayerPoints);
        String secondPlayerPointsString = String.valueOf(secondPlayerPoints);

        if (!MatchUtil.isTieBrake(match)) {
            if (firstPlayerPoints > 3 || secondPlayerPoints > 3) {
                if (firstPlayerPoints > secondPlayerPoints) {
                    firstPlayerPointsString = "AD";
                } else if (secondPlayerPoints > firstPlayerPoints) {
                    secondPlayerPointsString = "AD";
                }
            } else {
                firstPlayerPointsString = Utils.getScoreString(firstPlayerPoints);
                secondPlayerPointsString = Utils.getScoreString(secondPlayerPoints);
            }
        }

        var firstPlayerScoreDto = getPlayerScoreDto(match.getPlayer1(), firstPlayerPointsString);
        var secondPlayerScoreDto = getPlayerScoreDto(match.getPlayer2(), secondPlayerPointsString);

        String firstPlayerName = match.getPlayer1().getName();
        String secondPlayerName = match.getPlayer2().getName();
        String winnerName = getWinnerName(match);

        return new MatchScoreDto(
                firstPlayerName,
                secondPlayerName,
                firstPlayerScoreDto,
                secondPlayerScoreDto,
                match.getMatchStatus(),
                winnerName);
    }

    private static String getWinnerName(Match match) {
        if (match.getWinner() != null) {
            return match.getWinner().getName();
        }
        return null;
    }

    private static MatchScoreDto.PlayerScoreDto getPlayerScoreDto(Player match, String firstPlayerPointsString) {
        return new MatchScoreDto.PlayerScoreDto(
                String.valueOf(match.getPlayerScore().getSets()),
                String.valueOf(match.getPlayerScore().getGames()),
                firstPlayerPointsString);
    }
}
