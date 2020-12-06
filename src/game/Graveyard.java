package src.game;

import src.game.card.CardObject;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class Graveyard {
    private ArrayList<CardObject> graveyard;

    public Graveyard () {
        graveyard = new ArrayList<CardObject>();
    }

    public void render (Graphics g) {
        if (graveyard.size() > 0) {
            CardObject topCard = graveyard.get(graveyard.size() - 1);
            g.drawImage(topCard.getHandImage(), 100, 100, null);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 30));
            g.drawString(Integer.toString(graveyard.size()), 125, 125);
        }
    }

    public ArrayList<CardObject> getGraveyard() {
        return graveyard;
    }
    
}
