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

    public void render(Graphics g, int[] pos) {
        if (graveyard.size() > 0) {
            CardObject topCard = graveyard.get(graveyard.size() - 1);
            g.drawImage(topCard.getHandImage(), pos[0], pos[1], null);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 30));
            g.drawString(Integer.toString(graveyard.size()), pos[0] + 20, pos[1] + 40);
        }
    }

    public ArrayList<CardObject> getGraveyard() {
        return graveyard;
    }
    
}
