package src.game;

import src.game.card.CardObject;
import src.game.player.Player;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseMatch extends MouseAdapter {

    private Table table;
    private Player player;
    private Game game;
    private HUD hud;

    public MouseMatch(Table table, Player player, Game game, HUD hud) {
        this.table = table;
        this.player = player;
        this.game = game;
        this.hud = hud;
    }

    public int inRectangle(int x, int y, ArrayList<int[]> positions) {
        for (int i=0; i<positions.size(); i++) {
            if(x > positions.get(i)[0] && x < positions.get(i)[0] + CardObject.cardWidth) {
                if (y > positions.get(i)[1] && y < positions.get(i)[1] + CardObject.cardHeight) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void mouseClicked(MouseEvent e) {
        if (Game.gameState == STATE.Match) {
            if (e.getX() > table.getPlayerFieldCardPositions()[1][3][0]
                    && e.getX() < table.getPlayerFieldCardPositions()[1][3][0] + table.getCardWidth()) {
                if (e.getY() > table.getPlayerFieldCardPositions()[1][3][1]
                        && e.getY() < table.getPlayerFieldCardPositions()[1][3][1] + table.getCardHeight()) {
                    table.isDrawable = true;
                }
            } else
                table.isDrawable = false;
            
            // draw button
            if (e.getX() > table.getPlayerFieldCardPositions()[0][4][0] && e.getX() < table.getPlayerFieldCardPositions()[0][4][0] + CardObject.cardWidth) {
                if (e.getY() > table.getPlayerFieldCardPositions()[0][4][1] && e.getY() < table.getPlayerFieldCardPositions()[0][4][1] + CardObject.cardHeight) {
                    player.draw();
                }
            }

            // testing in rectangle
            int i = inRectangle(e.getX(), e.getY(), player.getHand().getPositions());
            if (i != -1) {
                hud.setCard(player.getHand().getHand().get(i));
            } else
                hud.setCard(null);
        }
    }
}