package src.game.player;

import src.game.card.CardObject;
import src.game.card.Deck;
import src.game.card.ExtraDeck;
import src.game.Game;
import src.game.Graveyard;
import src.game.Hand;
import src.game.Table;

import java.awt.Graphics;
import java.util.ArrayList;

public class Player {
    private Deck deck;
    private Hand hand;
    private Graveyard graveyard;
    private ExtraDeck extraDeck;
    private Table table;

    public Player(Deck deck, Hand hand, Graveyard graveyard, ExtraDeck extraDeck, Table table) {
        this.deck = deck;
        this.hand = hand;
        this.graveyard = graveyard;
        this.extraDeck = extraDeck;
        this.table = table;
    }

    public void render(Graphics g) {
        hand.render(g);
        deck.render(g, table.getPlayerFieldCardPositions()[0][4]);
        extraDeck.render(g, table.getPlayerFieldCardPositions()[0][0]);
        graveyard.render(g, table.getPlayerFieldCardPositions()[1][4]);
    }

    public void draw() {
        CardObject cardDrawed = deck.draw();
        if (cardDrawed != null) {
            ArrayList<CardObject> cards = hand.getHand();
            cards.add(cardDrawed);

            if (cards.size() >= 5) {
                graveyard.getGraveyard().add(cards.remove(0));
            }

            for (int i=0; i<cards.size(); i++) {
                cards.get(i).setX(Game.WIDTH / 2 - 10 -(CardObject.cardWidth / 2) - (cards.size()/2 - (i + 1)) * (CardObject.cardWidth + 10));               
                cards.get(i).setY(Game.HEIGHT - CardObject.cardHeight - 20);
            }

        }
    }

    public ArrayList<CardObject> getDeck() {
        return deck.getDeck();
    }


    public Hand getHand() {
        return hand;
    }

    // public ArrayList<CardObject> getHand() {
    //     return hand.getHand();
    // }
    
}
