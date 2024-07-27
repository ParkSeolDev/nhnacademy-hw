package model;

import java.util.ArrayList;
import java.util.List;

import util.RandomUnitGenerator;

public class TerranFactory extends Factory {
    private static final int UNIT_COUNT = 5;
    private static final int PLAYER_UNIT_COUNT = 6;

    private List<TerranUnit> terranUnitTypes = List.of(
        Marine.create(),
        Tank.create(),
        Goliath.create(),
        Wraith.create(),
        Valkyrie.create(),
        BattleCruzer.create()
    );
    private List<Unit> terranUnits;

    @Override
    public List<Unit> createUnits() {
        terranUnits = new ArrayList<>();
        for (int i = 0; i < UNIT_COUNT; i++) {
            terranUnits.add(terranUnitTypes.get(RandomUnitGenerator.generate(0, PLAYER_UNIT_COUNT)));
        }
        
        return terranUnits;
    }
}