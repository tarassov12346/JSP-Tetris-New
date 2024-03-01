package jsp.tetris.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsp.tetris.Dao;
import jsp.tetris.InitServlet;
import jsp.tetris.Player;
import jsp.tetris.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class HomeController {

    static HttpSession currentSession;
    static Player player;
    static State state;

    @RequestMapping(value="/start")
    public ModelAndView initView(HttpServletRequest req){
        currentSession = req.getSession(true);
        player = new Player().createPlayer();
        state = State.initialState(player).start().newTetramino().orElse(State.initialState(player));
        state.unSetPause();
        currentSession.setAttribute("isGameOn", true);
        currentSession.setAttribute("gameStatus", "Game is ON");
        return new ModelAndView("index");
    }
/*
    @RequestMapping(value="/logic")
    public ModelAndView gameView(HttpServletRequest req){
        HttpSession currentSession = HomeController.currentSession;
        switch (getSelectedCommand(req)) {
            case 0 -> {
                if (HomeController.state.tryMoveDown(State.stepDownArray[0]).isEmpty()) {
                    if (HomeController.state.newTetramino().isEmpty()) {
                        currentSession.setAttribute("isGameOn", false);
                        currentSession.setAttribute("gameStatus", "Game over");
                        HomeController.state.stop();
                    } else HomeController.state = HomeController.state.newTetramino().orElse(HomeController.state);
                }
                if (HomeController.state.tryMoveDown(State.stepDownArray[0]).isPresent()) HomeController.state =
                        HomeController.state.tryMoveDown(State.stepDownArray[0]).orElse(HomeController.state);
            }
            case 1 -> HomeController.state = HomeController.state.tryRotate().orElse(HomeController.state);
            case 2 -> HomeController.state = HomeController.state.tryMoveLeft().orElse(HomeController.state);
            case 3 -> HomeController.state = HomeController.state.tryMoveRight().orElse(HomeController.state);
            case 4 -> HomeController.state = HomeController.state.tryDropDown().orElse(HomeController.state);
        }
        char[][] cells = HomeController.state.stage.drawTetraminoOnCells();
        currentSession.setAttribute("player", HomeController.player.getPlayerName());
        currentSession.setAttribute("score", HomeController.player.getPlayerScore());
        currentSession.setAttribute("bestplayer", Dao.bestPlayer);
        currentSession.setAttribute("bestscore", Dao.bestScore);
        currentSession.setAttribute("stepdown", State.stepDownArray[0]);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
                currentSession.setAttribute("cells" + i + "v" + j, cells[i][j] + ".png");
            }
        }
        return new ModelAndView("index");
    }
    private int getSelectedCommand(HttpServletRequest request) {
        String click = request.getParameter("click");
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(click) : 0;
    }*/
}
