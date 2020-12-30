package src.game;


import src.game.HUD;
import src.game.card.CardObject;
import src.game.card.Monster;
import src.game.card.Spell;
import src.game.card.Trap;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Graveyard {
    private Match match;
    private ArrayList<CardObject> graveyard;
    private boolean isSelected; 
    private boolean canRenderList;
    private int x;
    private int y;
    private int row;
    private int column;
    private int width;
    private int height;
    private Rectangle listRectangle;
    private BufferedImage image;

    public Graveyard (Match match) {
        graveyard = new ArrayList<CardObject>();
        isSelected = false;
        canRenderList = false;
        x = match.getTable().getPlayerFieldCardPositions()[1][4][0];
        y = match.getTable().getPlayerFieldCardPositions()[1][4][1];
        image = HUD.tempAsset[HUD.LENS];
    }

    public void render(Graphics g) {
        if (graveyard.size() > 0) {
            CardObject topCard = graveyard.get(graveyard.size() - 1);
            g.drawImage(topCard.getHandImage(), x, y, null);
            g.setColor(Color.white);
            Font tmp = g.getFont();
            g.setFont(new Font("Arial", 1, 30));
            g.drawString(Integer.toString(graveyard.size()), x + 20, y + 40);
            g.setFont(tmp);
            renderOption(g);
            if (canRenderList) {
                g.setColor(Color.white);
                g.fillRect((Game.WIDTH - width)/2, (Game.HEIGHT - height)/2, width, height);
                for (int i=0; i<graveyard.size(); i++) {
                    graveyard.get(i).render(g, CardObject.HANDSIZE);
                }
            }
        }
    }

    public void renderOption(Graphics g) {
        if (isSelected) {
            g.drawImage(image, x + CardObject.cardWidth/2 - 15, y - 50, null);
            // g.setColor(Color.red);
            // g.fillRect(x + CardObject.cardWidth/2 - 15, y - 50, 30, 30);
        }
    }

    public void organizeCardPositions() {
        int tmprow = 0;
        for (int i=0; i<graveyard.size(); i++) {
            if (i % 6 == 0 && i != 0) 
                tmprow++;
            graveyard.get(i).setX((Game.WIDTH - width)/2 + 10 + (i - 6*tmprow)*(CardObject.cardWidth + 10));
            graveyard.get(i).setY((Game.HEIGHT - height)/2 + 10 + (tmprow) *(CardObject.cardHeight + 10));
        }
    }

    // DA RIVISITARE!
    public void add (CardObject card) {
        graveyard.add(card);

        if (card instanceof Monster) {
            Monster monster = (Monster) card; 
            monster.setIsNormalSummonable(false);
            monster.setIsSettable(false);
            monster.setIsDefensePosition(false);
            monster.setIsCovered(false);
            
        } else if (card instanceof Spell) {
            Spell spell = (Spell) card;
            spell.setIsSettable(false);
        } else if (card instanceof Trap) {
            Trap trap = (Trap) card;
            trap.setIsSettable(false);
        }

        row = (graveyard.size() / 6) + 1;
        height = row*(CardObject.cardHeight + 10) + 10;
        if (graveyard.size() <= 6) {
            column = graveyard.size();
            width = column*(CardObject.cardWidth + 10) + 10;
        }
        organizeCardPositions();
        listRectangle = new Rectangle((Game.WIDTH - width) / 2, (Game.HEIGHT - height) / 2, width, height);
    }

    public ArrayList<CardObject> getGraveyard() {
        return graveyard;
    }

    public boolean getIsSelected() {
        return isSelected;
    }
    
    public void setIsSelected(boolean bool) {
        isSelected = bool;
    }

    public boolean getCanRenderList() {
        return canRenderList;
    }
    
    public void setCanRenderList(boolean bool) {
        canRenderList = bool;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getListRectangle() {
        return listRectangle;
    }
    
    public ArrayList<int[]> getCardPositions() {
        ArrayList<int[]> list = new ArrayList<int[]>();
        for (int i = 0; i < graveyard.size(); i++) {
            list.add(new int[] {graveyard.get(i).getX(), graveyard.get(i).getY()});
        }
        return list;
    }
}
