package com.cofisweak.servlet;

import com.cofisweak.dto.PersistMatchDto;
import com.cofisweak.service.OngoingMatchesService;
import com.cofisweak.util.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private final transient OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstPlayerName = req.getParameter("first_player");
        String secondPlayerName = req.getParameter("second_player");
        String setsCountString = req.getParameter("sets_count");

        PersistMatchDto addMatchDto = getNewMatchDto(firstPlayerName, secondPlayerName, setsCountString, resp);
        if(addMatchDto == null) return;

        UUID uuid = ongoingMatchesService.createMatch(addMatchDto);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }

    private PersistMatchDto getNewMatchDto(String firstPlayerName, String secondPlayerName, String setsCountString, HttpServletResponse resp) throws IOException {
        if (!isValidParameters(firstPlayerName, secondPlayerName, setsCountString, resp))
            return null;
        int setsCount;
        try {
            setsCount = Integer.parseInt(setsCountString.trim());
            if (setsCount != 3 && setsCount != 5) {
                resp.sendError(400, "The number of sets should be 3 or 5");
                return null;
            }
            return new PersistMatchDto(firstPlayerName.trim(), secondPlayerName.trim(), setsCount);
        } catch (NumberFormatException e) {
            resp.sendError(400, "The number of sets should be 3 or 5");
            return null;
        }
    }

    private static boolean isValidParameters(String firstPlayerName, String secondPlayerName, String setsCountString, HttpServletResponse resp) throws IOException {
        if (Utils.isFieldNotFilled(firstPlayerName)) {
            resp.sendError(400, "First player name required");
            return false;
        }
        if (Utils.isFieldNotFilled(secondPlayerName)) {
            resp.sendError(400, "Second player name required");
            return false;
        }
        if (Utils.isFieldNotFilled(setsCountString)) {
            resp.sendError(400, "Sets count required");
            return false;
        }
        if (firstPlayerName.trim().equalsIgnoreCase(secondPlayerName.trim())) {
            resp.sendError(400, "Players must be different");
            return false;
        }
        return true;
    }
}