package src.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    
    private Game game;

    public Mouse(Game game) {
        this.game = game;
    }

    public void mouseClicked(MouseEvent e) {
        if (Game.gameState == STATE.MainMenu) {
            if (e.getX() > Game.WIDTH/2 - 50 && e.getX() < Game.WIDTH + 50) {
                if (e.getY() > Game.HEIGHT/2 - 30 && e.getY() < Game.HEIGHT/2 + 30){ 
                    game.match = new Match();
                    Game.gameState = STATE.Match;
                    game.removeMouseListener(this);
                    game.addMouseListener(new MouseMatch(game.match, game));
                }
            }
            //Game.WIDTH/2 - 50, Game.HEIGHT/2 - 30, 100, 60
        }
    }
}