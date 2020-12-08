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

    public Player(Deck deck, Hand hand, Graveyard graveyard, ExtraDeck extraDeck, Table table) {
        this.deck = deck;
        this.hand = hand;
        this.graveyard = graveyard;
        this.extraDeck = extraDeck;
        this.table = table;
    }

    public void render(Graphics g) {
        table.renderPlayerTable(g);
        hand.render(g);
        deck.render(g, table.getPlayerFieldCardPositions()[0][4]);
        extraDeck.render(g, table.getPlayerFieldCardPositions()[0][0]);
        graveyard.render(g, table.getPlayerFieldCardPositions()[1][4]);
    }

    public void draw() {
        CardObject cardDrawed = deck.draw();
        if (cardDrawed != null) {
            hand.getHand().add(cardDrawed);
            if (hand.getHand().size() >= 5) {
                graveyard.getGraveyard().add(hand.getHand().remove(0));
            }
        }
    }

    public ArrayList<CardObject> getDeck() {
        return deck.getDeck();
    }
    
}
