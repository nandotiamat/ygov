package src.game;

import src.game.player.Player;

public class Match {
    private Player player;
    private Table table;
    private HUD hud;
    private PHASES phase;

    public Match() {
        System.out.println("Creating match!");
        table = new Table(this);
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
        setPhase(PHASES.DrawPhase);
        System.out.println(phase + " for player: player draw");
        player.draw();
        setPhase(PHASES.StandbyPhase);
        System.out.println(phase + " for player.");
        setPhase(PHASES.MainPhase1);
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

    public PHASES getPhase() {
        return phase;
    }    
    
    public void setPhase(PHASES phase) {
        this.phase = phase;
        long beginning = System.currentTimeMillis();
        long now;
        do {
            now = System.currentTimeMillis();
        }while (now - beginning < 1000); 

        if (this.phase == PHASES.EndPhase ) {
            System.out.println("New turn!");
            player.resetForBeginTurn();
            beginTurn();
        }
    }

}
