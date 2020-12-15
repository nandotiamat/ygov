package src.game;

import src.game.card.CardObject;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Rectangle;

public class Graveyard {
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

    public Graveyard (int[] position) {
        graveyard = new ArrayList<CardObject>();
        isSelected = false;
        canRenderList = false;
        x = position[0];
        y = position[1];
    }

    public void render(Graphics g, int[] pos) {
        if (graveyard.size() > 0) {
            CardObject topCard = graveyard.get(graveyard.size() - 1);
            g.drawImage(topCard.getHandImage(), pos[0], pos[1], null);
            g.setColor(Color.white);
            Font tmp = g.getFont();
            g.setFont(new Font("Arial", 1, 30));
            g.drawString(Integer.toString(graveyard.size()), pos[0] + 20, pos[1] + 40);
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
            g.setColor(Color.red);
            g.fillRect(x + CardObject.cardWidth/2 - 15, y - 50, 30, 30);
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
    
}
