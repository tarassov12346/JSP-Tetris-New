package jsp.tetris;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession currentSession = InitServlet.currentSession;
        switch (getSelectedCommand(req)) {
            case 0 -> {
                if (InitServlet.state.tryMoveDown(State.stepDownArray[0]).isEmpty()) {
                    if (InitServlet.state.newTetramino().isEmpty()) {
                        currentSession.setAttribute("isGameOn", false);
                        currentSession.setAttribute("gameStatus", "Game over");
                        InitServlet.state.stop();
                    } else InitServlet.state = InitServlet.state.newTetramino().orElse(InitServlet.state);
                }
                if (InitServlet.state.tryMoveDown(State.stepDownArray[0]).isPresent()) InitServlet.state =
                        InitServlet.state.tryMoveDown(State.stepDownArray[0]).orElse(InitServlet.state);
            }
            case 1 -> InitServlet.state = InitServlet.state.tryRotate().orElse(InitServlet.state);
            case 2 -> InitServlet.state = InitServlet.state.tryMoveLeft().orElse(InitServlet.state);
            case 3 -> InitServlet.state = InitServlet.state.tryMoveRight().orElse(InitServlet.state);
            case 4 -> InitServlet.state = InitServlet.state.tryDropDown().orElse(InitServlet.state);
        }
        char[][] cells = InitServlet.state.stage.drawTetraminoOnCells();
        currentSession.setAttribute("player", InitServlet.player.getPlayerName());
        currentSession.setAttribute("score", InitServlet.player.getPlayerScore());
        currentSession.setAttribute("bestplayer", Dao.bestPlayer);
        currentSession.setAttribute("bestscore", Dao.bestScore);
        currentSession.setAttribute("stepdown", State.stepDownArray[0]);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
                currentSession.setAttribute("cells" + i + "v" + j, cells[i][j] + ".png");
            }
        }
        resp.sendRedirect("/index.jsp");
    }

    private int getSelectedCommand(HttpServletRequest request) {
        String click = request.getParameter("click");
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(click) : 0;
    }
}
