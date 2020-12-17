package src.game;

import src.game.player.Player;

public class Match {
    private Player player;
    private Table table;
    private HUD hud;
    private PHASES phase;

    public Match() {
        System.out.println("Creating match!");
        table = new Table();
        System.out.println("table created.");
        //player = new Player(new Deck(this), new Hand(), new Graveyard(table.getPlayerFieldCardPositions()[1][4]), new ExtraDeck(this, table.getPlayerFieldCardPositions()[0][0]), table);
        player = new Player(this);
        System.out.println("Created player.");
        hud = new HUD(this);
        System.out.println("Created hud.");
        beginMatch();
        beginTurn();
    }

    private void beginMatch() {
        player.draw();
        player.draw();
        player.draw();
        System.out.println("Match starts!");
    }

    private void beginTurn() {
        phase = PHASES.DrawPhase;
        System.out.println(phase + " for player: player draw");
        player.draw();
        phase = PHASES.StandbyPhase;
        System.out.println(phase + " for player.");
        phase = PHASES.MainPhase1;
        System.out.println(phase + " for player");
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
