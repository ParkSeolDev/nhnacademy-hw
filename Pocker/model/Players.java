package model;

import java.util.ArrayList;
import java.util.List;

public class Players {
    private List<Player> players = new ArrayList<>();

    private Players(final List<Player> players) {
        this.players = players;
    }
    
    public static Players from(final int playerCount, final int seedMoney) {
        List<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            newPlayers.add(Player.of(i, Money.from(seedMoney), new Hand()));
        }
        return new Players(newPlayers);
    }

    public Player player(final int playerNum) {
        return players.get(playerNum);
    }

    public List<Player> players() {
        return players;
    }

    public Players setPlayer(int index, Player player) {
        players.set(index, player);
        return new Players(players);
    }
}
