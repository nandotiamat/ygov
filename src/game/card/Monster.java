package src.game.card;

import src.game.Hand;
import src.game.Table;

import java.awt.image.BufferedImage;

import java.awt.Graphics;
import java.awt.Color;

public abstract class Monster extends CardObject{

    protected int level;
    protected int atk;
    protected int def;
    protected String type;
    protected boolean isNormalSummonable;

    public Monster(String name, ATTRIBUTE attribute, int id, String description, BufferedImage image, int level, int atk, int def, String type) {
        super(name, attribute, id, description, image);
        this.level = level;
        this.atk = atk;
        this.def = def; 
        this.type = type;
        if ((this instanceof NormalMonster || this instanceof EffectMonster) && this.level < 5) {
            isNormalSummonable = true;
        } else
            isNormalSummonable = false;
    }

    public void normalSummon(Hand hand, Table table) {
        hand.getHand().remove(this); 
        hand.organizePositions();
        isNormalSummonable = false;
        table.summonMonster(this);
    }

    public void renderOptions(Graphics g) {
        if (this.isSelected) {       
            if (this.isNormalSummonable) {
                g.setColor(Color.white);
                g.fillRect(this.x + CardObject.cardWidth/2 - 15, this.y - 50, 30, 30);
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public String getType() {
        return type;
    }
    //implement getters and setters eventually

    public abstract void attack();
    public abstract void set();

    public boolean getIsNormalSummonable() {
        return isNormalSummonable;
    }

    public void setIsNormalSummonable(boolean bool) {
        isNormalSummonable = bool;
    }
}
