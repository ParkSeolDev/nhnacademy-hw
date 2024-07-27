package com.nhnacademy;

public class MatchHistory {
    private int matchCount;
    private int winCount;

    public MatchHistory(int matchCount, int winCount) {
        this.matchCount = matchCount;
        this.winCount = winCount;
    }
    
    public int getMatchCount() {
        return matchCount;
    }
    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }
    public int getWinCount() {
        return winCount;
    }
    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }
    
}
