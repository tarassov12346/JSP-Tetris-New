package jsp.tetris.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Player {
    private String playerName;
    private int playerScore;

    public Player() {
    }

    public Player(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerScore == player.playerScore && playerName.equals(player.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, playerScore);
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getPlayerScore() {
        return this.playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore * 10;
    }

    public Player createPlayer() {
        List<String> list = new ArrayList<>();
        list.add("Oswaldo");
        list.add("Tommy");
        list.add("Dunny");
        list.add("Bonny");
        list.add("Ira");
        list.add("Wolfy");
        playerName = list.get(new Random().nextInt(list.size()));
        return new Player(playerName, playerScore);
    }
}
