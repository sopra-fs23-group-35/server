package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class GameGetDTO {

    private Long gameId;
    private int currentRound;
    // need a scoreboard
    // private Map<String, int> currentScore;

    public Long getGameId() {return gameId;}
    public void setGameId(Long gameId) {this.gameId = gameId;}

    public int getCurrentRound() {return currentRound;}
    public void setCurrentRound(int currentRound) {this.currentRound = currentRound;}
}
