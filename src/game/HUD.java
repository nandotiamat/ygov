package src.game;

import src.game.card.*;
import src.game.StringUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class HUD {
    private Table table;
    private CardObject card;
    private BufferedImage[] assets;

    public static final int WIND = 0;
    public static final int WATER = 1;
    public static final int EARTH = 2;
    public static final int FIRE = 3;
    public static final int DARK = 4;
    public static final int LIGHT = 5;
    public static final int DIVINE = 6;
    public static final int SPELL = 7;
    public static final int TRAP = 8;
    public static final int ATK = 9;
    public static final int DEF = 10;    
    public static final int LEVEL = 11;
    

    public HUD(Table table, CardObject card) {
        this.table = table;
        this.card = card; 
        assets = new BufferedImage[12];
        try {
            BufferedImage tmp = ImageIO.read(new File("src/img/assets.png"));
            for (int i = 0; i < 12; i++) {
                assets[i] = tmp.getSubimage(i*20, 0, 20, 20);
            }
        } catch (IOException e) {
            System.out.println("errore");
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(card.getHUDImage(), 100, 50, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.setColor(Color.black);
        g.fillRect(100, 50 + CardObject.cardHeight * 3 + 10, CardObject.cardWidth * 3, 400);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        g.setColor(Color.white);

        FontMetrics fm= g.getFontMetrics(g.getFont());
        // Rectangle2D rect=fm.getStringBounds(card.getDescription(),g);
        List<String> textList = StringUtils.wrap(card.getDescription(), fm, CardObject.cardWidth*3 - 10);

        // Level
        if (card instanceof EffectMonster) {
            g.drawImage(assets[LEVEL], 100, 50 + CardObject.cardHeight * 3 + 10, null);
            g.drawString("x" + Integer.toString(((EffectMonster) card).getLevel()), 130, 50 + CardObject.cardHeight * 3 + 25);
            g.setColor(Color.orange);
            g.fillRect(100, 50 + CardObject.cardHeight * 3 + 35, CardObject.cardWidth * 3, 20);
            g.setColor(Color.black);
            g.drawImage(assets[ATK], 100, 50 + CardObject.cardHeight * 3 + 35, null);
            g.drawString(Integer.toString(((EffectMonster) card).getAtk()), 125, 50 + CardObject.cardHeight * 3 + 50);
            g.drawImage(assets[DEF], 180, 50 + CardObject.cardHeight * 3 + 35, null);
            g.drawString(Integer.toString(((EffectMonster) card).getDef()), 200, 50 + CardObject.cardHeight * 3 + 50);
            g.drawImage(assets[card.getAttribute().ordinal()], 250, 50 + CardObject.cardHeight * 3 + 35, null);
            g.setColor(Color.yellow);
            g.drawString(card.getName(), 100, 50 + CardObject.cardHeight*3 + 50 + 20);
            g.setColor(Color.green);
            g.drawString("[" + ((EffectMonster) card).getType() + "/Effect]", 100, 50 + CardObject.cardHeight*3 + 50 + 40);
            //CHANGE TYPE TO ATTRIBUTE AND ADD TYPE VAR
            g.setColor(Color.white);
            for(int i = 0; i < textList.size(); i++) {
                g.drawString(textList.get(i), 100, 50 + CardObject.cardHeight*3 + 50 + 40 + (i+1)*20);
            }
        }
    }

}
