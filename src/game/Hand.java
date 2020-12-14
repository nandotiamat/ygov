package src.game;

import src.game.card.*;

import java.util.ArrayList;


import java.awt.Graphics;

public class Hand {
    private ArrayList<CardObject> hand;

    public Hand() {
        hand = new ArrayList<CardObject>();
    }

    public void render(Graphics g) {
        if (Game.gameState == STATE.Match) {
            for (int i=0; i < hand.size(); i++) {
                CardObject card = hand.get(i);
                card.render(g, CardObject.HANDSIZE);
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

    public void organizePositions() {
        for (int i=0; i<hand.size(); i++) {
            hand.get(i).setX(Game.WIDTH / 2 - 10 -(CardObject.cardWidth / 2) - (hand.size()/2 - (i + 1)) * (CardObject.cardWidth + 10));               
            hand.get(i).setY(Game.HEIGHT - CardObject.cardHeight - 20);
        }
    }

}
