package src.game.player;

import src.game.card.CardObject;
import src.game.card.Deck;
import src.game.card.ExtraDeck;
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

    private boolean canNormalSummon;

    public Player(Deck deck, Hand hand, Graveyard graveyard, ExtraDeck extraDeck, Table table) {
        this.deck = deck;
        this.hand = hand;
        this.graveyard = graveyard;
        this.extraDeck = extraDeck;
        this.table = table;

        canNormalSummon = true;
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

            hand.organizePositions();
        }
    }

    public ArrayList<CardObject> getDeck() {
        return deck.getDeck();
    }


    public Hand getHand() {
        return hand;
    }

    public boolean getCanNormalSummon() {
        return canNormalSummon;
    }

    public void setCanNormalSummon(boolean bool) {
        canNormalSummon = bool;
    }
    // public ArrayList<CardObject> getHand() {
    //     return hand.getHand();
    // }
    
}
