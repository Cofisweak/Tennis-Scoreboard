package com.cofisweak.service;

import com.cofisweak.dao.MatchDao;
import com.cofisweak.mapper.MatchMapper;
import com.cofisweak.model.Match;
import com.cofisweak.dto.MatchDto;
import com.cofisweak.dto.MatchesDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinishedMatchesPersistenceService {
    private static final FinishedMatchesPersistenceService INSTANCE = new FinishedMatchesPersistenceService();
    private final MatchDao matchDao = MatchDao.getInstance();

    public void persistMatch(Match match) {
        matchDao.persistMatch(match);
    }

    public MatchesDto getMatches(int page, String searchQuery) {
        List<Match> matches = matchDao.loadMatches(page, searchQuery);
        List<MatchDto> matchesDto = MatchMapper.mapToMatchesDto(matches);
        int pageCount = matchDao.getPageCountByQuery(searchQuery);
        return new MatchesDto(
                matchesDto,
                page,
                searchQuery,
                pageCount);
    }

    public static FinishedMatchesPersistenceService getInstance() {
        return INSTANCE;
    }
}
