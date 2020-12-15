package src.game;

import src.game.card.Deck;
import src.game.card.ExtraDeck;
import src.game.player.Player;

public class Match {
    private Player player;
    private Table table;
    private HUD hud;

    public Match() {
        System.out.println("Creating match!");
        table = new Table();
        System.out.println("table created.");
        player = new Player(new Deck(this), new Hand(), new Graveyard(table.getPlayerFieldCardPositions()[1][4]), new ExtraDeck(this, table.getPlayerFieldCardPositions()[0][0]), table);
        System.out.println("Created player.");
        hud = new HUD();
        System.out.println("Created hud.");
    }

    public Player getPlayer() {
        return player;
    }

    public Table getTable() {
        return table;
    }

    public HUD getHUD() {
        return hud;
    }

}
