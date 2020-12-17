package src.game.card;

import java.awt.image.BufferedImage;

import src.game.Hand;
import src.game.Match;
import src.game.Table;

import java.awt.Graphics;
public abstract class Spell extends CardObject{

    protected boolean isSettable;

    public Spell(String name, int id, String description, BufferedImage image, Match match) {
        super(name, ATTRIBUTE.Spell, id, description, image, match);
        isSettable = true;
    }

    public void renderOptions(Graphics g) {
        if (this.isSelected) {       
            if (this.isSettable && match.getTable().getPlayerSpellTrapOnField().size() < 3 ) {
                g.drawImage(match.getHUD().tempAsset[match.getHUD().SET], this.x + CardObject.cardWidth/2 - 10, this.y - 30, null);
            }
        }
    }

    public void set(Hand hand, Table table) {
        hand.getHand().remove(this);
        hand.organizePositions();
        isSettable = false;
        table.setSpellTrap(this);
    }

    public boolean getIsSettable() {
        return isSettable;
    }

    public void setIsSettable(boolean bool) {
        isSettable = bool;
    }
}
