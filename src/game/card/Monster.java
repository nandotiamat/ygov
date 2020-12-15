package src.game.card;

import src.game.Hand;
import src.game.Match;
import src.game.Table;

import java.awt.image.BufferedImage;

import java.awt.Graphics;

public abstract class Monster extends CardObject{

    protected int level;
    protected int atk;
    protected int def;
    protected String type;
    protected boolean isNormalSummonable;

    public Monster(String name, ATTRIBUTE attribute, int id, String description, BufferedImage image, Match match, int level, int atk, int def, String type) {
        super(name, attribute, id, description, image, match);
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
            if (this.isNormalSummonable && match.getTable().getPlayerMonsterOnField().size() < 3 && match.getPlayer().getCanNormalSummon()) {
                g.drawImage(match.getHUD().tempAsset[match.getHUD().NORMALSUMMON], this.x + CardObject.cardWidth/2 - 10, this.y - 40, null);
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
