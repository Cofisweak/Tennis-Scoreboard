package com.cofisweak.servlet;

import com.cofisweak.dto.MatchScoreDto;
import com.cofisweak.mapper.MatchMapper;
import com.cofisweak.model.Match;
import com.cofisweak.model.MatchStatus;
import com.cofisweak.model.PlayerNumber;
import com.cofisweak.service.MatchScoreCalculationService;
import com.cofisweak.service.OngoingMatchesService;
import com.cofisweak.util.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final transient MatchScoreCalculationService matchScoreCalculationService = MatchScoreCalculationService.getInstance();
    private final transient OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();

    private static final String PLAYER_1_ID = "1";
    private static final String PLAYER_2_ID = "2";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidString = req.getParameter("uuid");
        Match match = getMatch(resp, uuidString);
        if (match == null) return;
        forwardToMatchScorePage(req, resp, match);
    }

    private static void forwardToMatchScorePage(HttpServletRequest req, HttpServletResponse resp, Match match) throws ServletException, IOException {
        MatchScoreDto matchScoreDto = MatchMapper.mapToMatchScoreDto(match);
        req.setAttribute("matchScoreDto", matchScoreDto);
        req.getRequestDispatcher("/WEB-INF/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerId = req.getParameter("player_id");
        String uuidString = req.getParameter("uuid");
        if (isValidPlayerId(playerId)) {
            resp.sendError(400, "Player not specified");
            return;
        }
        Match match = getMatch(resp, uuidString);
        if (match == null) return;

        PlayerNumber playerNumber = getPlayerNumber(playerId);
        matchScoreCalculationService.countPoint(match, playerNumber);

        if (match.getMatchStatus() == MatchStatus.FINISHED) {
            ongoingMatchesService.endMatch(UUID.fromString(uuidString));
        }

        forwardToMatchScorePage(req, resp, match);
    }

    private PlayerNumber getPlayerNumber(String playerId) {
        return playerId.equals(PLAYER_1_ID) ?
                PlayerNumber.FIRST_PLAYER :
                PlayerNumber.SECOND_PLAYER;
    }

    private static boolean isValidPlayerId(String playerId) {
        return Utils.isFieldNotFilled(playerId) &&
               !playerId.trim().equals(PLAYER_1_ID) &&
               !playerId.trim().equals(PLAYER_2_ID);
    }

    private Match getMatch(HttpServletResponse resp, String uuidString) throws IOException {
        if (Utils.isFieldNotFilled(uuidString)) {
            resp.sendError(400, "UUID field required");
            return null;
        }
        UUID uuid;
        try {
            uuid = UUID.fromString(uuidString.trim());
        } catch (Exception e) {
            resp.sendError(400, "Invalid match UUID");
            return null;
        }
        Optional<Match> matchOptional = ongoingMatchesService.getMatch(uuid);
        if (matchOptional.isEmpty()) {
            resp.sendError(404, "Match not found");
            return null;
        }
        return matchOptional.get();
    }
}
