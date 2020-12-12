package src.game;

import src.game.card.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.FontMetrics;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HUD {
    private Table table;
    private CardObject card;
    private BufferedImage[] assets;

    public final int ATK = 9;
    public final int DEF = 10;
    public final int LEVEL = 11;

    public final Color effectMonsterColor = new Color(183, 104, 61);
    public final Color normalMonsterColor = new Color(186, 145, 40);
    public final Color fusionMonsterColor = new Color(105, 80, 137);
    public final Color ritualMonsterColor = new Color(65, 98, 169);
    public final Color spellColor = new Color(28, 124, 112);
    public final Color trapColor = new Color(147, 56, 115);

    public HUD(Table table, CardObject card) {
        this.table = table;
        //this.card = card;
        assets = new BufferedImage[12];
        try {
            BufferedImage tmp = ImageIO.read(new File("src/img/assets.png"));
            for (int i = 0; i < 12; i++) {
                assets[i] = tmp.getSubimage(i * 20, 0, 20, 20);
            }
        } catch (IOException e) {
            System.out.println("errore nel caricamento degli assets.");
        }
    }

    public void render(Graphics g) {
        if (card != null) {
            Graphics2D g2d = (Graphics2D) g;
            g.drawImage(card.getHUDImage(), 100, 50, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g.setColor(Color.black);
            g.fillRect(100, 50 + CardObject.cardHeight * 3 + 10, CardObject.cardWidth * 3, 400);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            g.setColor(Color.white);
    
            FontMetrics fm = g.getFontMetrics(g.getFont());
    
            // Rectangle2D rect=fm.getStringBounds(card.getDescription(),g);
            // sto robo non serve a uncazzo ^^^^
    
            List<String> descriptionList = StringUtils.wrap(card.getDescription(), fm, CardObject.cardWidth * 3 - 10);
            List<String> nameList = StringUtils.wrap(card.getName(), fm, CardObject.cardWidth * 3 - 10);
            // Level
    
            if (card instanceof Monster) {
                // level row
                g.drawImage(assets[LEVEL], 100, 50 + CardObject.cardHeight * 3 + 10, null);
                g.drawString("x" + Integer.toString(((Monster) card).getLevel()), 130, 50 + CardObject.cardHeight * 3 + 25);
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
    
            g.fillRect(100, 50 + CardObject.cardHeight * 3 + 35, CardObject.cardWidth * 3, 20);
    
            g.setColor(Color.black);
    
            if (card instanceof Monster) {
                g.drawImage(assets[ATK], 100, 50 + CardObject.cardHeight * 3 + 35, null);
                g.drawString(Integer.toString(((Monster) card).getAtk()), 125, 50 + CardObject.cardHeight * 3 + 50);
                g.drawImage(assets[DEF], 180, 50 + CardObject.cardHeight * 3 + 35, null);
                g.drawString(Integer.toString(((Monster) card).getDef()), 200, 50 + CardObject.cardHeight * 3 + 50);
            }
    
            g.drawImage(assets[card.getAttribute().ordinal()], 250, 50 + CardObject.cardHeight * 3 + 35, null);
    
            // printing name
            g.setColor(Color.yellow);
            for (int i = 0; i < nameList.size(); i++) {
                g.drawString(nameList.get(i), 100, 50 + CardObject.cardHeight * 3 + 50 + 20 + i * 10);
            }
    
            // printing type
            g.setColor(Color.green);
    
            if (card instanceof Monster) {
                if (card instanceof NormalMonster) {
                    g.drawString("[" + ((Monster) card).getType() + "]", 100, 50 + CardObject.cardHeight * 3 + 50 + 40);
                } else if (card instanceof EffectMonster) {
                    g.drawString("[" + ((Monster) card).getType() + "/Effect]", 100,
                            50 + CardObject.cardHeight * 3 + 50 + 40);
                } else if (card instanceof FusionMonster) {
                    g.drawString("[" + ((Monster) card).getType() + "/Fusion/Effect]", 100,
                            50 + CardObject.cardHeight * 3 + 50 + 40);
                } else if (card instanceof RitualMonster) {
                    g.drawString("[" + ((Monster) card).getType() + "/Ritual/Effect]", 100,
                            50 + CardObject.cardHeight * 3 + 50 + 40);
                }
    
            } else if (card instanceof Spell) {
                g.drawString("[SPELL CARD]", 100, 50 + CardObject.cardHeight * 3 + 50 + 40);
            } else if (card instanceof Trap) {
                g.drawString("[TRAP CARD]", 100, 50 + CardObject.cardHeight * 3 + 50 + 40);
            }
    
            // printing description
            g.setColor(Color.white);
            for (int i = 0; i < descriptionList.size(); i++) {
                g.drawString(descriptionList.get(i), 100, 50 + CardObject.cardHeight * 3 + 50 + 40 + (i + 1) * 20);
            }
        }

    }

    public void setCard(CardObject card) {
        this.card = card;
    }

}
