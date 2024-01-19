package jsp.tetris;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@WebServlet(name = "RestartGameServlet", value = "/restart")
public class RestartGameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("C:\\JavaProjects\\jsp-tetris\\save.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        try {
            SavedGame savedGame = (SavedGame) objectInputStream.readObject();
            InitServlet.player = new Player(savedGame.getPlayerName(), savedGame.getPlayerScore());
            InitServlet.state =
                    new State(Stage.recreateStage(savedGame.getCells(), InitServlet.player.getPlayerScore() / 10), true, InitServlet.player).
                            restartWithNewTetramino().
                            orElse(new State(Stage.recreateStage(savedGame.getCells(), InitServlet.player.getPlayerScore() / 10), true, new Player(savedGame.getPlayerName(), savedGame.getPlayerScore())));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        State.stepDownArray[0] = InitServlet.player.getPlayerScore() / 10 + 1;
        char[][] cells = InitServlet.state.stage.drawTetraminoOnCells();
        InitServlet.currentSession.setAttribute("gameStatus", "Game is ON");
        InitServlet.currentSession.setAttribute("isGameOn", true);
        InitServlet.state.unSetPause();
        InitServlet.currentSession.setAttribute("player", InitServlet.player.getPlayerName());
        InitServlet.currentSession.setAttribute("score", InitServlet.player.getPlayerScore());
        InitServlet.currentSession.setAttribute("bestplayer", Dao.bestPlayer);
        InitServlet.currentSession.setAttribute("bestscore", Dao.bestScore);
        InitServlet.currentSession.setAttribute("stepdown", State.stepDownArray[0]);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
                InitServlet.currentSession.setAttribute("cells" + i + "v" + j, cells[i][j] + ".png");
            }
        }
        resp.sendRedirect("/index.jsp");
    }
}
