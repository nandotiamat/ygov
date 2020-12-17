package src.game.card;

import src.game.Hand;
import src.game.Match;
import src.game.Table;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import java.awt.Graphics;

public abstract class Monster extends CardObject{

    protected int level;
    protected int atk;
    protected int def;
    protected String type;
    protected boolean isNormalSummonable;
    protected boolean isSettable;

    public Monster(String name, ATTRIBUTE attribute, int id, String description, BufferedImage image, Match match, int level, int atk, int def, String type) {
        super(name, attribute, id, description, image, match);
        this.level = level;
        this.atk = atk;
        this.def = def; 
        this.type = type;
        if ((this instanceof NormalMonster || this instanceof EffectMonster) && this.level < 5) {
            isNormalSummonable = true;
            isSettable = true;
        } else {
            isNormalSummonable = false;
            isSettable = false;
        }
    }

    public void normalSummon(Hand hand, Table table) {
        hand.getHand().remove(this); 
        hand.organizePositions();
        isNormalSummonable = false;
        table.summonMonster(this);
    }    
    
    public void set() {
        Hand hand = match.getPlayer().getHand();
        System.out.println("ongod");
        hand.getHand().remove(this);
        hand.organizePositions();
        isNormalSummonable = false;
        isCovered = true;
        match.getTable().summonMonster(this);
    };

    public void renderOptions(Graphics g) {
        // if (this.isSelected) {      
        //     if (this.isNormalSummonable && match.getTable().getPlayerMonsterOnField().size() < 3 && match.getPlayer().getCanNormalSummon()) {
        //         g.drawImage(match.getHUD().tempAsset[match.getHUD().NORMALSUMMON], this.x + CardObject.cardWidth/2 - 10, this.y - 40, null);
        //     }
        //     if (this.isSettable && match.getTable().getPlayerMonsterOnField().size() < 3 && match.getPlayer().getCanNormalSummon()) {
        //         g.drawImage(match.getHUD().tempAsset[match.getHUD().SET], this.x + CardObject.cardWidth/2 - 10, this.y - 40, null);
        //     }
        // }
        if (this.isSelected) {      
            if (this.isNormalSummonable && match.getTable().getPlayerMonsterOnField().size() < 3 && match.getPlayer().getCanNormalSummon()) {
                g.drawImage(match.getHUD().tempAsset[match.getHUD().NORMALSUMMON], x, y - 40, null);
            }
            if (this.isSettable && match.getTable().getPlayerMonsterOnField().size() < 3 && match.getPlayer().getCanNormalSummon()) {
                g.drawImage(match.getHUD().tempAsset[match.getHUD().SET], x + 30, y - 40, null);
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

    public Rectangle getNormalSummonButtonRect() {
        return new Rectangle(x, y - 40, 20, 20);
    }    
    public Rectangle getSetButtonRect() {
        return new Rectangle(x + 30, y - 40, 20, 20);
    }

    //implement getters and setters eventually
    public abstract void attack();




    public boolean getIsNormalSummonable() {
        return isNormalSummonable;
    }

    public void setIsNormalSummonable(boolean bool) {
        isNormalSummonable = bool;
    }

    public boolean getIsSettable() {
        return isSettable;
    }

    public void setIsSettable(boolean bool) {
        isSettable = bool;
    }
}
