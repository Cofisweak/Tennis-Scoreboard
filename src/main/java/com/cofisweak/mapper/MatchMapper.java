package com.cofisweak.mapper;

import com.cofisweak.dto.MatchScoreDto;
import com.cofisweak.model.Match;
import com.cofisweak.dto.MatchDto;
import com.cofisweak.model.MatchStatus;
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

        if (match.getMatchStatus() != MatchStatus.TIE_BRAKE) {
            firstPlayerPointsString = Utils.getScoreString(firstPlayerPoints);
            secondPlayerPointsString = Utils.getScoreString(secondPlayerPoints);

            if (firstPlayerPoints > 3 || secondPlayerPoints > 3) {
                if (firstPlayerPoints > secondPlayerPoints) {
                    firstPlayerPointsString = "AD";
                } else if(secondPlayerPoints > firstPlayerPoints) {
                    secondPlayerPointsString = "AD";
                }
            }
        }

        MatchScoreDto.PlayerScoreDto firstPlayerScoreDto = new MatchScoreDto.PlayerScoreDto(
                String.valueOf(match.getPlayer1().getPlayerScore().getSets()),
                String.valueOf(match.getPlayer1().getPlayerScore().getGames()),
                firstPlayerPointsString);
        MatchScoreDto.PlayerScoreDto secondPlayerScoreDto = new MatchScoreDto.PlayerScoreDto(
                String.valueOf(match.getPlayer2().getPlayerScore().getSets()),
                String.valueOf(match.getPlayer2().getPlayerScore().getGames()),
                secondPlayerPointsString);

        String winnerName = null;
        if (match.getWinner() != null) {
            winnerName = match.getWinner().getName();
        }
        
        return new MatchScoreDto(match.getPlayer1().getName(),
                match.getPlayer2().getName(),
                firstPlayerScoreDto,
                secondPlayerScoreDto,
                match.getMatchStatus(),
                winnerName);
    }
}
