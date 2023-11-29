package com.cofisweak.service;

import com.cofisweak.dto.PersistMatchDto;
import com.cofisweak.model.Match;
import com.cofisweak.model.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OngoingMatchesService {
    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getInstance();
    private final PlayerService playerService = PlayerService.getInstance();
    private final ConcurrentMap<UUID, Match> matches = new ConcurrentHashMap<>();

    public Optional<Match> getMatch(UUID uuid) {
        return Optional.ofNullable(matches.get(uuid));
    }

    public UUID createMatch(PersistMatchDto dto) {
        UUID uuid = UUID.randomUUID();
        Player player1 = playerService.getPlayerOrCreateIfNotExists(dto.firstPlayerName());
        Player player2 = playerService.getPlayerOrCreateIfNotExists(dto.secondPlayerName());

        Match match = Match.builder()
                .maxMatchSets(dto.setsCount())
                .player1(player1)
                .player2(player2)
                .build();
        match.startMatch();

        matches.put(uuid, match);
        return uuid;
    }

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }

    public void endMatch(UUID uuid) {
        Match match = matches.remove(uuid);
        finishedMatchesPersistenceService.persistMatch(match);
    }
}
