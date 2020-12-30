package src.game;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.AlphaComposite;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import src.game.card.CardObject;
import src.game.card.Monster;

import javax.imageio.ImageIO;

public class Table {

    private Match match;
    public boolean isDrawable = false; 

    private float whratio = 0.68688f;
    private int cardWidth = 60;
    private int cardHeight = (int) (((float) cardWidth)/whratio); 
    private float alpha = 0.5f;
    private int spaceBetweenCards = 10;
    private int tableWidth = 6*spaceBetweenCards + 4*cardHeight + cardWidth;
    private int tableHeight = 3*spaceBetweenCards + 2 * (int) ( ((float) cardWidth)/whratio);

    private int[][][] opponentFieldCardPositions = new int[2][5][2];
    private int[][][] playerFieldCardPositions = new int[2][5][2];
    private Rectangle[] phasesRect;
    
    private ArrayList<CardObject> playerMonsterOnField = new ArrayList<CardObject>();
    private ArrayList<CardObject> playerSpellTrapOnField = new ArrayList<CardObject>();


    private BufferedImage wallpaper;
    private BufferedImage cardBack;
    private BufferedImage nonResizedGenericCard;
    private BufferedImage deckBack;
    private BufferedImage extraDeckBack;
    private BufferedImage graveyard;
    private static BufferedImage[] phases;
    private static BufferedImage[] activePhases;

    public Table(Match match) { 
        this.match = match;
        phases = new BufferedImage[6];
        activePhases = new BufferedImage[6];
        System.out.println("table width: " + tableWidth);
        System.out.println("phase width: " + tableWidth / 6);
        try {
            wallpaper = ImageIO.read(new File("src/img/wallpaper.jpg"));
            cardBack = ImageIO.read(new File("src/img/cardBack.png"));
            nonResizedGenericCard = ImageIO.read(new File("src/img/1.png"));
            deckBack = ImageIO.read(new File("src/img/deckBack.png"));
            extraDeckBack = ImageIO.read(new File("src/img/extraDeckBack.png"));
            graveyard = ImageIO.read(new File("src/img/graveyard.png"));
            
            BufferedImage tmp = ImageIO.read(new File("src/img/phases.png"));
            for (int i=0; i<6; i++) {
                phases[i] = tmp.getSubimage(78*i, 0, 78, 38);
                activePhases[i] = tmp.getSubimage(78*i, 38, 78, 38);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cardBack = resizeImage(cardBack, cardWidth, cardHeight);
            nonResizedGenericCard = resizeImage(nonResizedGenericCard, 200,(int) (200f/whratio));
            deckBack = resizeImage(deckBack, cardWidth, cardHeight);
            extraDeckBack = resizeImage(extraDeckBack, cardWidth, cardHeight);
            graveyard = resizeImage(graveyard, cardWidth, cardHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0; i<2; i++) {
            playerFieldCardPositions[i][0][0] = centerTableX() + spaceBetweenCards; 
            playerFieldCardPositions[i][0][1] = centerPlayerTableY() + 2*spaceBetweenCards + cardHeight - i*(spaceBetweenCards + cardHeight);
            for (int j=1; j<5; j++) {
                opponentFieldCardPositions[i][j][0] = centerTableX() + 5*spaceBetweenCards + 4*90 - (j*spaceBetweenCards + j*90);
                opponentFieldCardPositions[i][j][1] = centerOpponentTableY() + (i+1)*spaceBetweenCards + i*cardHeight;
                playerFieldCardPositions[i][j][0] = playerFieldCardPositions[i][j-1][0] + spaceBetweenCards + cardHeight;  
                playerFieldCardPositions[i][j][1] = playerFieldCardPositions[i][0][1];
            }
        }

        //Phases Rectangles
        phasesRect = new Rectangle[6];
        for(int i = 0; i < 6; i++) {
            phasesRect[i] = new Rectangle(centerTableX() + 78*i, centerPlayerTableY() - 38, 78, 38);
        }
    }

    public void render(Graphics g) {
        g.drawImage(wallpaper, 0, 0, null);
        
        for (int i=0; i<6; i++) {
            if (match.getPhase().ordinal() == i) {
                g.drawImage(activePhases[i], centerTableX() + 78*i, centerPlayerTableY() - 38, null);
            } else 
                g.drawImage(phases[i], centerTableX() + 78*i, centerPlayerTableY() - 38, null);
        }
        renderPlayerTable(g);
        //renderOpponentTable(g);
    }
 
    //DA RIMUOVERE E CREARE UN FILE APPOSITO CON QUESTA FUNZIONE CHE PUÒ SERVIRE UN PO IN GIRO!
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    int centerTableX() {
        return ( Game.WIDTH - tableWidth )/2;
    }

    int centerPlayerTableY() {
        return 5*spaceBetweenCards + 3*cardHeight + 38;
    }

    int centerOpponentTableY() { 
        return 2*spaceBetweenCards + cardHeight;
    }

    public void renderOpponentTable(Graphics g) {
        //opponent table
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.fillRect(centerTableX(), centerOpponentTableY(), tableWidth, tableHeight); 
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    public void renderPlayerTable(Graphics g) {
        //player table
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.fillRect(centerTableX(), centerPlayerTableY(), tableWidth, tableHeight); 
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        g.drawImage(graveyard, playerFieldCardPositions[1][4][0], playerFieldCardPositions[1][4][1], null);
        // RENDERING PLAYER MONSTER FIELD ZONE
        if (playerMonsterOnField.size() != 0) {
            for (int i=0; i<playerMonsterOnField.size(); i++) {
                playerMonsterOnField.get(i).render(g, CardObject.HANDSIZE);
            }
        }
        // RENDERING PLAYER SPELL TRAP FIELD ZONE 
        if (playerSpellTrapOnField.size() != 0) {
            for (int i=0; i<playerSpellTrapOnField.size(); i++) {
                playerSpellTrapOnField.get(i).render(g, CardObject.HANDSIZE);
            }
        }
    }

    public int[][][] getPlayerFieldCardPositions() {
        return playerFieldCardPositions;
    }

    public Rectangle[] getPhasesRect() {
        return phasesRect;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public void summonMonster(Monster card) {
        boolean[] isFree = new boolean[] {true, true, true};
        int diffX;
        int diffY;

        if (card.getIsDefensePosition()) {
            diffX = 15;
            diffY = 10;
        }
        else {

            diffX = 0;
            diffY = 0;
        }

        for (int i=0; i<playerMonsterOnField.size(); i++) {
            Monster monster = (Monster) playerMonsterOnField.get(i);
            if (monster.getIsDefensePosition()) {
                if (monster.getX() == playerFieldCardPositions[1][1][0] - diffX && monster.getY() == playerFieldCardPositions[1][1][1] + diffY) {
                    isFree[0] = false;
                } else if (monster.getX() == playerFieldCardPositions[1][2][0] - diffX && monster.getY() == playerFieldCardPositions[1][2][1] + diffY ) {
                    isFree[1] = false;
                } else if (monster.getX() == playerFieldCardPositions[1][3][0] - diffX && monster.getY() == playerFieldCardPositions[1][3][1] + diffY ) {
                    isFree[2] = false;
                }
            } else {
                if (monster.getX() == playerFieldCardPositions[1][1][0] && monster.getY() == playerFieldCardPositions[1][1][1]) {
                    isFree[0] = false;
                } else if (monster.getX() == playerFieldCardPositions[1][2][0] && monster.getY() == playerFieldCardPositions[1][2][1]) {
                    isFree[1] = false;
                } else if (monster.getX() == playerFieldCardPositions[1][3][0] && monster.getY() == playerFieldCardPositions[1][3][1]) {
                    isFree[2] = false;
                }
            }
        }
        if (isFree[0]) {
            card.setX(playerFieldCardPositions[1][1][0] - diffX);
            card.setY(playerFieldCardPositions[1][1][1] + diffY);
            playerMonsterOnField.add(card);
        } else if (isFree[1]) {
            card.setX(playerFieldCardPositions[1][2][0] - diffX);
            card.setY(playerFieldCardPositions[1][2][1] + diffY);
            playerMonsterOnField.add(card);
        } else if (isFree[2]) {
            card.setX(playerFieldCardPositions[1][3][0] - diffX);
            card.setY(playerFieldCardPositions[1][3][1] + diffY);
            playerMonsterOnField.add(card);
        }
    }

    public void setSpellTrap(CardObject card) {
        boolean[] isFree = new boolean[] {true, true, true};

        for (int i=0; i<playerSpellTrapOnField.size(); i++) {
            if (playerSpellTrapOnField.get(i).getX() == playerFieldCardPositions[0][1][0] && playerSpellTrapOnField.get(i).getY() == playerFieldCardPositions[0][1][1]) {
                isFree[0] = false;
            } else if (playerSpellTrapOnField.get(i).getX() == playerFieldCardPositions[0][2][0] && playerSpellTrapOnField.get(i).getY() == playerFieldCardPositions[0][2][1]) {
                isFree[1] = false;
            } else if (playerSpellTrapOnField.get(i).getX() == playerFieldCardPositions[0][3][0] && playerSpellTrapOnField.get(i).getY() == playerFieldCardPositions[0][3][1]) {
                isFree[2] = false;
            }
        }
        if (isFree[0]) {
            card.setX(playerFieldCardPositions[0][1][0]);
            card.setY(playerFieldCardPositions[0][1][1]);
            card.setIsCovered(true);
            playerSpellTrapOnField.add(card);
        } else if (isFree[1]) {
            card.setX(playerFieldCardPositions[0][2][0]);
            card.setY(playerFieldCardPositions[0][2][1]);
            card.setIsCovered(true);
            playerSpellTrapOnField.add(card);
        } else if (isFree[2]) {
            card.setX(playerFieldCardPositions[0][3][0]);
            card.setY(playerFieldCardPositions[0][3][1]);
            card.setIsCovered(true);
            playerSpellTrapOnField.add(card);
        }
    }

    public int getTableWidth() {
        return tableWidth;
    }

    public int getTableHeight() {
        return tableHeight;
    }

    public ArrayList<CardObject> getPlayerMonsterOnField() {
        return playerMonsterOnField;
    }

    public ArrayList<CardObject> getPlayerSpellTrapOnField() {
        return playerSpellTrapOnField;
    }
}
