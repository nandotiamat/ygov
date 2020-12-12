package src.game;

import src.game.player.Player;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    
    private Table table;
    private Player player;
    private Game game;
    private HUD hud;

    public Mouse(Table table, Player player, Game game, HUD hud) {
        this.table = table;
        this.player = player;
        this.game = game;
        this.hud = hud;
    }

    public void mouseClicked(MouseEvent e) {
        if (Game.gameState == STATE.MainMenu) {
            if (e.getX() > Game.WIDTH/2 - 50 && e.getX() < Game.WIDTH + 50) {
                if (e.getY() > Game.HEIGHT/2 - 30 && e.getY() < Game.HEIGHT/2 + 30){ 
                    Game.gameState = STATE.Match;
                    game.removeMouseListener(this);
                    game.addMouseListener(new MouseMatch(table, player, game, hud));
                }
            }
            //Game.WIDTH/2 - 50, Game.HEIGHT/2 - 30, 100, 60
        }
        if (Game.gameState == STATE.Match) {
            System.out.println("match");
            if (e.getX() > table.getPlayerFieldCardPositions()[1][3][0] && e.getX() < table.getPlayerFieldCardPositions()[1][3][0] + table.getCardWidth()) {
                if (e.getY() > table.getPlayerFieldCardPositions()[1][3][1] && e.getY() < table.getPlayerFieldCardPositions()[1][3][1] + table.getCardHeight()) {
                    table.isDrawable = true;
                }
            } 
            else table.isDrawable = false;

            if (e.getX() > 0 && e.getX() < 100) {
                if (e.getY() > 0 && e.getY() < 100) {
                    player.draw();;
                }   
            }
        }
    }
}