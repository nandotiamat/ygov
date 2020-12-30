package src.game.card;

import src.game.Hand;
import src.game.Match;
import src.game.Table;
import src.game.HUD;

import java.awt.image.BufferedImage;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public abstract class Monster extends CardObject{

    protected int level;
    protected int atk;
    protected int def;
    protected String type;
    protected boolean isNormalSummonable;
    protected boolean isSettable;
    protected boolean isDefensePosition;
    protected boolean canChangePosition;
    protected BufferedImage rotatedImage;
    protected static BufferedImage rotatedBackImage;

    static {
        rotatedBackImage = rotateImageByDegrees(handBackImage, 90);
    }

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
        isDefensePosition = false;
        canChangePosition = false;
        rotatedImage = rotateImageByDegrees(handImage, 90);
    }
    public static BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return rotated;
    }

    public void render(Graphics g, int size) {
        if (isDefensePosition) {
            if (isCovered) 
                g.drawImage(rotatedBackImage, x, y, null);
            else
                g.drawImage(rotatedImage, x, y, null);
        } else {
            if (size == HANDSIZE) {
                if (isCovered) 
                    g.drawImage(handBackImage, x, y, null);
                else 
                    g.drawImage(handImage, x, y, null); 
            } else if (size == HUDSIZE) {
                if (isCovered) 
                    g.drawImage(hudBackImage, x, y, null);
                else
                    g.drawImage(hudImage, x, y, null);
            }
        }
        renderOptions(g);
    }

    public void normalSummon(Hand hand, Table table) {
        hand.getHand().remove(this); 
        hand.organizePositions();
        isNormalSummonable = false;
        table.summonMonster(this);
    }    

    public void changePosition() {
        if (isDefensePosition) {
            x += 15;
            y -= 10;
            if (isCovered) {
                isCovered = !isCovered;
            }
        } else {
            x -= 15;
            y += 10;
        }
        isDefensePosition = !isDefensePosition;
        canChangePosition = false;
    }
    
    public void set() {
        Hand hand = match.getPlayer().getHand();
        hand.getHand().remove(this);
        hand.organizePositions();
        isNormalSummonable = false;
        isCovered = true;
        isDefensePosition = true;
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
                g.drawImage(HUD.tempAsset[match.getHUD().NORMALSUMMON], x, y - 40, null);
            }
            if (this.isSettable && match.getTable().getPlayerMonsterOnField().size() < 3 && match.getPlayer().getCanNormalSummon()) {
                g.drawImage(HUD.tempAsset[match.getHUD().SET], x + 30, y - 40, null);
            }
            if (canChangePosition) {
                g.fillRect(x + 60, y - 40, 20, 20);
            }
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        if (isDefensePosition) {
            System.out.println("I FEEL LIKE GOD!");
            return new Rectangle(x, y, cardHeight, cardWidth);
        } else {
            System.out.println("returning vanilla rect");
            return super.getRectangle();
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
    public Rectangle getChangePositionRect() {
        return new Rectangle(x + 60, y - 40, 20, 20);
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

    public boolean getIsDefensePosition() {
        return isDefensePosition;
    }
    
    public void setIsDefensePosition(boolean bool) {
        isDefensePosition = bool;
    }

    public boolean getCanChangePosition() {
        return canChangePosition;
    }

    public void setCanChangePosition(boolean bool) {
        canChangePosition = bool;
    }
}
