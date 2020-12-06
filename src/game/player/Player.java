package src.game.player;

import src.game.card.CardObject;
import src.game.card.Deck;
import src.game.card.ExtraDeck;
import src.game.Graveyard;
import src.game.Hand;

import java.awt.Graphics;

public class Player {
    private Deck deck;
    private Hand hand;
    private Graveyard graveyard;
    private ExtraDeck extraDeck;

    public Player(Deck deck, Hand hand, Graveyard graveyard, ExtraDeck extraDeck) {
        this.deck = deck;
        this.hand = hand;
        this.graveyard = graveyard;
        this.extraDeck = extraDeck;
    }

    public void render(Graphics g) {
        hand.render(g);
        graveyard.render(g);
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
    
}
