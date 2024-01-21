package jsp.tetris;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dao {
    private final String url = "jdbc:postgresql:game";
    private final String user = "postgres";
    private final String password = "mine";

    List<Player> playerList=new ArrayList<>();
    static public String bestPlayer = "To be shown";
    static public int bestScore;

    public void recordScore(Player player) {
        String recordScoreQuery = "INSERT INTO player1(name, score) VALUES(?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(recordScoreQuery)) {
            pst.setString(1, player.getPlayerName());
            pst.setInt(2, player.getPlayerScore());
            pst.executeUpdate();
            System.out.println(player.getPlayerName()+player.getPlayerScore());
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(State.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void retrieveScores() {
        String retrieveScoresQuery = "SELECT * FROM player1";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(retrieveScoresQuery);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                playerList.add(new Player(rs.getString(1),rs.getInt(2)));
                Collections.sort(playerList,Comparator.comparingInt(Player::getPlayerScore));
                bestPlayer=playerList.get(playerList.size()-1).getPlayerName();
                bestScore=playerList.get(playerList.size()-1).getPlayerScore();
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(State.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        System.out.println("Best player " + bestPlayer + " with score " + bestScore);
    }

}
