package com.cofisweak.dto;

import java.util.List;

public record MatchesDto(List<MatchDto> matches,
                         int page,
                         String query,
                         int pageCount) {
}
