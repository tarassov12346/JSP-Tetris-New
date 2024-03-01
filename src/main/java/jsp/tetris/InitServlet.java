package jsp.tetris;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "InitServlet", value = "/start111")
public class InitServlet extends HttpServlet {
    static HttpSession currentSession;
    static Player player;
    static State state;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        currentSession = req.getSession(true);
        player = new Player().createPlayer();
        state = State.initialState(player).start().newTetramino().orElse(State.initialState(player));
        state.unSetPause();
        currentSession.setAttribute("isGameOn", true);
        currentSession.setAttribute("gameStatus", "Game is ON");
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
