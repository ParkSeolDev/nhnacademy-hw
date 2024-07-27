package model;

import java.util.ArrayList;
import java.util.List;

public class PlayerUnits implements Iterable {
    private List<Unit> playerUnits;

    private Iterator iterator;

    private PlayerUnits(List<Unit> units) {
        this.playerUnits = units;
    }

    public static PlayerUnits from(List<Unit> units) {
        return new PlayerUnits(units);
    }

    public Iterator iterator() {
        return new PlayerUnitsIterator(this);
    }

    public Unit playerUnit(int index) {
        return playerUnits.get(index);
    }

    public int size() {
        return playerUnits.size();
    }

    public <T> PlayerUnits of(int index, T unit) { 
        return remove(index, new PlayerUnits(this.playerUnits), (Unit) unit);
    }
    
    private PlayerUnits remove(int index, PlayerUnits playerUnits, Unit attackedUnit) {
        List<Unit> units = new ArrayList<>();
        Iterator iterator = playerUnits.iterator();
        int i = 0;
        while(iterator.hasNext()) {
            Unit unit = iterator.next();
            if (index == i && attackedUnit.defense() == 0)
                ;
            else if (index == i && attackedUnit.defense() != 0) {
                units.add(attackedUnit);
            }
            else {
                units.add(unit);
            }
            i++;
        }

        return new PlayerUnits(units);
    }

    public boolean endGame() {
        if (playerUnits.size() == 0) {
            return true;
        }
        return false;
    }

    private static class PlayerUnitsIterator implements Iterator {
        private int index = 0;
        private PlayerUnits playerUnits;

        public PlayerUnitsIterator(PlayerUnits playerUnits) {
            this.playerUnits = playerUnits;
        }

        @Override
        public boolean hasNext() {
            return (this.index < this.playerUnits.size());
        }

        @Override
        public Unit next() {
            return playerUnits.playerUnit(this.index++);
        }
    }
}
