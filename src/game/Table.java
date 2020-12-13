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
                opponentFieldCardPositions[i][j][0] = centerTableX(tableWidth) + 5*spaceBetweenCards + 4*cardWidth - (j*spaceBetweenCards + j*cardWidth);
                opponentFieldCardPositions[i][j][1] = centerOpponentTableY() + (i+1)*spaceBetweenCards + i*cardHeight;
                playerFieldCardPositions[i][j][0] = centerTableX(tableWidth) + (j+1)*spaceBetweenCards + j*cardWidth;
                playerFieldCardPositions[i][j][1] = centerPlayerTableY() + 2*spaceBetweenCards + cardHeight - i*(spaceBetweenCards + cardHeight);
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(wallpaper, 0, 0, null);
        renderPlayerTable(g);
        //renderOpponentTable(g);
        if (isDrawable) g.drawImage(nonResizedGenericCard, 100, 50, null);
    }

    //DA RIMUOVERE E CREARE UN FILE APPOSITO CON QUESTA FUNZIONE CHE PUÒ SERVIRE UN PO IN GIRO!
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    int centerTableX(int width) {
        return ( Game.WIDTH - width )/2;
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
        g.fillRect(centerTableX(tableWidth), centerOpponentTableY(), tableWidth, tableHeight); 
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    public void renderPlayerTable(Graphics g) {
        //player table
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.fillRect(centerTableX(tableWidth), centerPlayerTableY(), tableWidth, tableHeight); 
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        g.drawImage(graveyard, playerFieldCardPositions[1][4][0], playerFieldCardPositions[1][4][1], null);
        if (playerMonsterOnField.size() != 0) {
            for (int i=0; i<playerMonsterOnField.size(); i++) {
                playerMonsterOnField.get(i).render(g, CardObject.HANDSIZE);
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
        card.setX(playerFieldCardPositions[1][1][0]);
        card.setY(playerFieldCardPositions[1][1][1]);
        playerMonsterOnField.add(card);
    }


}
