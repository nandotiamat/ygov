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
                g.drawImage(image, Game.WIDTH / 2 - 10 -(CardObject.cardWidth / 2) - (hand.size()/2 - (i + 1)) * (CardObject.cardWidth + 10), Game.HEIGHT - CardObject.cardHeight - 20, null);
            }   
        }
    }

    public ArrayList<CardObject> getHand() {
        return hand;
    }

}
