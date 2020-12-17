package src.game;

import src.game.card.*;
import src.game.player.Player;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.AlphaComposite;
import java.awt.FontMetrics;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HUD {
    private Player player;

    private CardObject card;
    private BufferedImage[] assets;
    public BufferedImage[] tempAsset;

    public final int ATK = 9;
    public final int DEF = 10;
    public final int LEVEL = 11;

    public final int NORMALSUMMON = 0;
    public final int SET = 1;
    public final int LENS = 2;

    public final Color effectMonsterColor = new Color(183, 104, 61);
    public final Color normalMonsterColor = new Color(186, 145, 40);
    public final Color fusionMonsterColor = new Color(105, 80, 137);
    public final Color ritualMonsterColor = new Color(65, 98, 169);
    public final Color spellColor = new Color(28, 124, 112);
    public final Color trapColor = new Color(147, 56, 115);

    private final int playerImageX;
    private final int playerImageY;
    private final Rectangle cardHudRectangle; 

    public HUD(Match match) {
        player = match.getPlayer();
        card = null;
        assets = new BufferedImage[12];
        tempAsset = new BufferedImage[3];
        try {
            BufferedImage tmp = ImageIO.read(new File("src/img/assets.png"));
            for (int i = 0; i < 12; i++) {
                assets[i] = tmp.getSubimage(i * 20, 0, 20, 20);
            }
            tmp = ImageIO.read(new File("src/img/tempasset.png"));
            for (int i = 0; i < 3; i++) {
                tempAsset[i] = tmp.getSubimage(i * 20, 0, 20, 20);
            }
        } catch (IOException e) {
            System.out.println("errore nel caricamento degli assets.");
        }

        cardHudRectangle = new Rectangle(100, 50 + CardObject.cardHeight * 3 + 10, CardObject.cardWidth * 3, 275);
        playerImageX = 20;
        playerImageY = cardHudRectangle.y + cardHudRectangle.height + 19 + 5;
    }

    public void render(Graphics g) {
        if (card != null) {
            Graphics2D g2d = (Graphics2D) g;
            g.drawImage(card.getHUDImage(), 100, 50, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g.setColor(Color.black);
            g.fillRect(cardHudRectangle.x, cardHudRectangle.y, cardHudRectangle.width, cardHudRectangle.height);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            g.setColor(Color.white);
    
            //bisogna togliere da render ma non ho voglia occhei!
            FontMetrics fm = g.getFontMetrics(g.getFont());
            List<String> descriptionList = StringUtils.wrap(card.getDescription(), fm, cardHudRectangle.width - 10);
            List<String> nameList = StringUtils.wrap(card.getName(), fm, cardHudRectangle.width - 10);
            // Level
    
            if (card instanceof Monster) {
                // level row
                g.drawImage(assets[LEVEL], cardHudRectangle.x, cardHudRectangle.y, null);
                g.drawString("x" + Integer.toString(((Monster) card).getLevel()), cardHudRectangle.x + 30, cardHudRectangle.y + 15);
            }
    
            // atk def attribute row
            if (card instanceof Spell) {
                g.setColor(spellColor);
            } else if (card instanceof Trap) {
                g.setColor(trapColor);
            } else if (card instanceof Monster) {
                if (card instanceof NormalMonster) {
                    g.setColor(normalMonsterColor);
                } else if (card instanceof EffectMonster) {
                    g.setColor(effectMonsterColor);
                } else if (card instanceof FusionMonster) {
                    g.setColor(fusionMonsterColor);
                } else if (card instanceof RitualMonster) {
                    g.setColor(ritualMonsterColor);
                }
            }
    
            g.fillRect(cardHudRectangle.x, cardHudRectangle.y + 25, cardHudRectangle.width, 20);
    
            g.setColor(Color.black);
    
            if (card instanceof Monster) {
                g.drawImage(assets[ATK], cardHudRectangle.x, cardHudRectangle.y + 25, null);
                g.drawString(Integer.toString(((Monster) card).getAtk()), cardHudRectangle.x + 25, cardHudRectangle.y + 40);
                g.drawImage(assets[DEF], cardHudRectangle.x + 80, cardHudRectangle.y + 25, null);
                g.drawString(Integer.toString(((Monster) card).getDef()), cardHudRectangle.x + 100, cardHudRectangle.y + 40);
            }
    
            g.drawImage(assets[card.getAttribute().ordinal()], cardHudRectangle.x + 150, cardHudRectangle.y + 25, null);
    
            // printing name
            g.setColor(Color.yellow);
            for (int i = 0; i < nameList.size(); i++) {
                g.drawString(nameList.get(i), cardHudRectangle.x, cardHudRectangle.y + 40 + 20 + i * 10);
            }
    
            // printing type
            g.setColor(Color.green);
    
            if (card instanceof Monster) {
                if (card instanceof NormalMonster) {
                    g.drawString("[" + ((Monster) card).getType() + "]", cardHudRectangle.x, cardHudRectangle.y + 40 + 40);
                } else if (card instanceof EffectMonster) {
                    g.drawString("[" + ((Monster) card).getType() + "/Effect]", cardHudRectangle.x, cardHudRectangle.y + 40 + 40);
                } else if (card instanceof FusionMonster) {
                    g.drawString("[" + ((Monster) card).getType() + "/Fusion/Effect]", cardHudRectangle.x, cardHudRectangle.y + 40 + 40);
                } else if (card instanceof RitualMonster) {
                    g.drawString("[" + ((Monster) card).getType() + "/Ritual/Effect]", cardHudRectangle.x, cardHudRectangle.y + 40 + 40);
                }
    
            } else if (card instanceof Spell) {
                g.drawString("[SPELL CARD]", cardHudRectangle.x, cardHudRectangle.y + 40 + 40);
            } else if (card instanceof Trap) {
                g.drawString("[TRAP CARD]", cardHudRectangle.x, cardHudRectangle.y + 40 + 40);
            }
    
            // printing description
            g.setColor(Color.white);
            for (int i = 0; i < descriptionList.size(); i++) {
                g.drawString(descriptionList.get(i), cardHudRectangle.x, cardHudRectangle.y + 40 + 40 + (i + 1) * 20);
            }
            
        }

        // PLAYER HUD
        g.setColor(Color.white);
        g.fillRect(playerImageX - 5, playerImageY - 5, 90, 90);
        g.fillRect(playerImageX - 5, playerImageY - 5 + 50, 300, 40);
        g.fillPolygon(new Polygon(new int[] {15 + 300, 15+ 300, 15+ 300 + 60}, new int[] {50 + CardObject.cardHeight * 3 + 10 + 294 + 50, 50 + CardObject.cardHeight * 3 + 10 + 294 + 90, 50 + CardObject.cardHeight * 3 + 10 + 294 + 50}, 3));
        g.drawImage(player.getHUDImage(), playerImageX, playerImageY, null);
        g.setColor(Color.green);
        g.fillRect(110, 50 + CardObject.cardHeight * 3 + 10 + 294, 250, 45);
        g.setColor(Color.black);
        g.drawString(player.getName(), 120, 50 + CardObject.cardHeight * 3 + 10 + 294 + 20 + 50);
        g.drawString(Integer.toString(player.getLifePoints()), 120, 50 + CardObject.cardHeight * 3 + 10 + 294 + 20);

    }

    public void setCard(CardObject card) {
        this.card = card;
    }

    public CardObject getCard() {
        return card;
    }

}
