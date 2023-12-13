package com.cofisweak.servlet;

import com.cofisweak.service.FinishedMatchesPersistenceService;
import com.cofisweak.dto.MatchesDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private final transient FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchQuery = req.getParameter("filter_by_player_name");
        String pageString = req.getParameter("page");
        int page = 1;
        try {
            if (pageString != null) {
                page = Integer.parseInt(pageString);
            }
        } catch (Exception e) {
            resp.sendError(400, "Invalid page");
            return;
        }
        if (searchQuery != null && searchQuery.isBlank()) {
            searchQuery = null;
        }
        MatchesDto matchesDto = finishedMatchesPersistenceService.getMatches(page, searchQuery);
        req.setAttribute("matchesDto", matchesDto);
        req.getRequestDispatcher("/WEB-INF/matches.jsp").forward(req, resp);
    }
}
