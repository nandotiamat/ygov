package src.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.AlphaComposite;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import src.game.card.CardObject;
import javax.imageio.ImageIO;

public class Table {

    public boolean isDrawable = false; 

    private float whratio = 0.68688f;
    private int cardWidth = 60;
    private int cardHeight = (int) (((float) cardWidth)/whratio); 
    private float alpha = 0.5f;
    private int spaceBetweenCards = 10;
    private int tableWidth = 6*spaceBetweenCards + 5*cardWidth;
    private int tableHeight = 3*spaceBetweenCards + 2 * (int) ( ((float) cardWidth)/whratio);

    private int[][][] opponentFieldCardPositions = new int[2][5][2];
    private int[][][] playerFieldCardPositions = new int[2][5][2];
    
    private ArrayList<CardObject> playerMonsterOnField = new ArrayList<CardObject>();
    private ArrayList<CardObject> playerSpellTrapOnField = new ArrayList<CardObject>();


    private BufferedImage wallpaper;
    private BufferedImage cardBack;
    private BufferedImage nonResizedGenericCard;
    private BufferedImage deckBack;
    private BufferedImage extraDeckBack;
    private BufferedImage graveyard;

    public Table() { 
        try {
            wallpaper = ImageIO.read(new File("src/img/wallpaper.jpg"));
            cardBack = ImageIO.read(new File("src/img/cardBack.png"));
            nonResizedGenericCard = ImageIO.read(new File("src/img/1.png"));
            deckBack = ImageIO.read(new File("src/img/deckBack.png"));
            extraDeckBack = ImageIO.read(new File("src/img/extraDeckBack.png"));
            graveyard = ImageIO.read(new File("src/img/graveyard.png"));
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
            for (int j=0; j<5; j++) {
                opponentFieldCardPositions[i][j][0] = centerTableX() + 5*spaceBetweenCards + 4*cardWidth - (j*spaceBetweenCards + j*cardWidth);
                opponentFieldCardPositions[i][j][1] = centerOpponentTableY() + (i+1)*spaceBetweenCards + i*cardHeight;
                playerFieldCardPositions[i][j][0] = centerTableX() + (j+1)*spaceBetweenCards + j*cardWidth;
                playerFieldCardPositions[i][j][1] = centerPlayerTableY() + 2*spaceBetweenCards + cardHeight - i*(spaceBetweenCards + cardHeight);
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(wallpaper, 0, 0, null);
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

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public void summonMonster(CardObject card) {
        boolean[] isFree = new boolean[] {true, true, true};

        for (int i=0; i<playerMonsterOnField.size(); i++) {
            if (playerMonsterOnField.get(i).getX() == playerFieldCardPositions[1][1][0] && playerMonsterOnField.get(i).getY() == playerFieldCardPositions[1][1][1]) {
                isFree[0] = false;
            } else if (playerMonsterOnField.get(i).getX() == playerFieldCardPositions[1][2][0] && playerMonsterOnField.get(i).getY() == playerFieldCardPositions[1][2][1]) {
                isFree[1] = false;
            } else if (playerMonsterOnField.get(i).getX() == playerFieldCardPositions[1][3][0] && playerMonsterOnField.get(i).getY() == playerFieldCardPositions[1][3][1]) {
                isFree[2] = false;
            }
        }
        if (isFree[0]) {
            card.setX(playerFieldCardPositions[1][1][0]);
            card.setY(playerFieldCardPositions[1][1][1]);
            playerMonsterOnField.add(card);
        } else if (isFree[1]) {
            card.setX(playerFieldCardPositions[1][2][0]);
            card.setY(playerFieldCardPositions[1][2][1]);
            playerMonsterOnField.add(card);
        } else if (isFree[2]) {
            card.setX(playerFieldCardPositions[1][3][0]);
            card.setY(playerFieldCardPositions[1][3][1]);
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
