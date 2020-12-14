package src.game.card;

import java.awt.image.BufferedImage;

import src.game.Hand;
import src.game.Table;

import java.awt.Graphics;
import java.awt.Color;

public abstract class Spell extends CardObject{

    protected boolean isSettable;

    public Spell(String name, int id, String description, BufferedImage image) {
        super(name, ATTRIBUTE.Spell, id, description, image);
        isSettable = true;
    }

    public void renderOptions(Graphics g) {
        if (this.isSelected) {       
            if (this.isSettable) {
                g.setColor(Color.red);
                g.fillRect(this.x + CardObject.cardWidth/2 - 15, this.y - 50, 30, 30);
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
}
