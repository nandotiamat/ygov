package src.game;

import src.game.card.*;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.IOException;

public class Hand {
    private ArrayList<CardObject> hand;
    private Deck deck;
    private int size = 1;

    public Hand(Deck deck) {
        this.deck = deck;
        hand = new ArrayList<CardObject>();
        hand.add(deck.draw());
    }

    public void render(Graphics g) {
        if (Game.gameState == STATE.Match) {
            for (int i=0; i < size; i++) {
                CardObject card = hand.get(i);
                BufferedImage image = card.getImage();
                try {
					image = resizeImage(image, CardObject.cardWidth, CardObject.cardHeight);
				} catch (IOException e) {
					e.printStackTrace();
				}
                g.drawImage(image, Game.WIDTH/2, Game.HEIGHT - CardObject.cardHeight - 10, null);
            }
        }
    }

    //DA RIMUOVERE E CREARE UN FILE APPOSITO CON QUESTA FUNZIONE CHE PUÒ SERVIRE UN PO IN GIRO!
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
    

}
