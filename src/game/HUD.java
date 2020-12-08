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

public class HUD {
    private Table table;
    private CardObject card;

    public HUD(Table table, CardObject card) {
        this.table = table;
        this.card = card; 
        // risolvere questo fatto qua.
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
            g.drawString("x" + Integer.toString(((EffectMonster) card).getLevel()), 100, 50 + CardObject.cardHeight * 3 + 30);
            g.setColor(Color.orange);
            g.fillRect(100, 50 + CardObject.cardHeight * 3 + 35, CardObject.cardWidth * 3, 20);
            g.setColor(Color.black);
            g.drawString("ATK/" + Integer.toString(((EffectMonster) card).getAtk()), 100, 50 + CardObject.cardHeight * 3 + 50);
            g.drawString("DEF/" + Integer.toString(((EffectMonster) card).getDef()), 150, 50 + CardObject.cardHeight * 3 + 50);
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
