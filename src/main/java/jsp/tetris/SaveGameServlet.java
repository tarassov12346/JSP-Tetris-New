package jsp.tetris;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@WebServlet(name = "SaveGameServlet", value = "/save")
public class SaveGameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        InitServlet.state.setPause();
        InitServlet.currentSession.setAttribute("isGameOn", false);
        SavedGame savedGame =
                new SavedGame(InitServlet.player.getPlayerName(), InitServlet.player.getPlayerScore(),
                        InitServlet.state.stage.getCells());
        FileOutputStream outputStream = new FileOutputStream("C:\\JavaProjects\\jsp-tetris\\save.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(savedGame);
        objectOutputStream.close();
        InitServlet.currentSession.setAttribute("gameStatus", "Game Saved");
        resp.sendRedirect("/index.jsp");
    }
}
