package src.game;

import src.game.card.*;

import java.util.ArrayList;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Hand {
    private ArrayList<CardObject> hand;
    private ArrayList<int[]> positions;

    public Hand() {
        hand = new ArrayList<CardObject>();
        positions = new ArrayList<int[]>();
    }

    public void render(Graphics g) {
        if (Game.gameState == STATE.Match) {
            for (int i=0; i < hand.size(); i++) {
                CardObject card = hand.get(i);
                BufferedImage image = card.getHandImage();
                int x = Game.WIDTH / 2 - 10 -(CardObject.cardWidth / 2) - (hand.size()/2 - (i + 1)) * (CardObject.cardWidth + 10);
                int y = Game.HEIGHT - CardObject.cardHeight - 20;
                g.drawImage(image, x, y, null);
            }
        }
    }

    public ArrayList<CardObject> getHand() {
        return hand;
    }

    public ArrayList<int[]> getPositions() {
        return positions;
    }

}
