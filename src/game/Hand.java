package src.game;

import src.game.card.*;

import java.util.ArrayList;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Hand {
    private ArrayList<CardObject> hand;

    public Hand() {
        hand = new ArrayList<CardObject>();
    }

    public void render(Graphics g) {
        if (Game.gameState == STATE.Match) {
            for (int i=0; i < hand.size(); i++) {
                CardObject card = hand.get(i);
                BufferedImage image = card.getHandImage();
                g.drawImage(image, card.getX(), card.getY(), null);
            }
        }
    }

    public ArrayList<CardObject> getHand() {
        return hand;
    }

    public ArrayList<int[]> getPositions() {
        ArrayList<int[]> positions = new ArrayList<int[]>();
        for (int i=0; i<hand.size(); i++) {
            positions.add(new int[] {hand.get(i).getX(), hand.get(i).getY()});
        }
        return positions;
    }

}
