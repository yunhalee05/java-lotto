package lotto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum LottoRank {
    FIFTH(3, 5_000),
    FOURTH(4, 50_000),
    THIRD(5, 1_500_000),
    FIRST(6, 2_000_000_000),
    NONE(0,0);

    private final int matchCount;
    private final int matchReward;

    LottoRank(int matchCount, int matchReward) {
        this.matchCount = matchCount;
        this.matchReward = matchReward;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public int getMatchReward() {
        return matchReward;
    }

    public static List<LottoRank> ranks() {
        return Arrays.stream(LottoRank.values())
                .filter(rank -> rank != LottoRank.NONE)
                .collect(Collectors.toList());
    }

    public static LottoRank valueOf(int matchCount) {
        return Arrays.stream(values())
                .filter(rank -> rank.getMatchCount() == matchCount)
                .findFirst().orElse(NONE);
    }
}