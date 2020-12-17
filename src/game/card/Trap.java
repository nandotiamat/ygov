package src.game.card;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import src.game.Hand;
import src.game.Match;
import src.game.Table;

public class Trap extends CardObject {

    protected boolean isSettable;

    public Trap(String name, int id, String description, BufferedImage image, Match match) {
        super(name, ATTRIBUTE.Trap, id, description, image, match);
        isSettable = true;
    }

    public void set(Hand hand, Table table) {
        hand.getHand().remove(this);
        hand.organizePositions();
        isSettable = false;
        table.setSpellTrap(this);
    }

    public void destroy() {
    }

    public void renderOptions(Graphics g) {
        if (this.isSelected) {       
            if (this.isSettable && match.getTable().getPlayerSpellTrapOnField().size() < 3) {
                g.drawImage(match.getHUD().tempAsset[match.getHUD().SET], x + CardObject.cardWidth/2 - 10, y - 30, null);
            }
        }
    }

    public boolean getIsSettable() {
        return isSettable;
    }    

    public void setIsSettable(boolean bool) {
        isSettable = bool;
    }
}
