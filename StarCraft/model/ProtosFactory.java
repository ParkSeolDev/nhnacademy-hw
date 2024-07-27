package model;

import java.util.ArrayList;
import java.util.List;

import util.RandomUnitGenerator;

public class ProtosFactory extends Factory {
    private static final int UNIT_COUNT = 4;
    private static final int PLAYER_UNIT_COUNT = 6;

    private List<ProtosUnit> protosUnitTypes = List.of(
        Zealot.create(),
        Dragoon.create(),
        HighTempler.create(),
        Scout.create(),
        Corsair.create(),
        Carrier.create()
    );

    private List<Unit> protosUnits;

    @Override
    public List<Unit> createUnits() {
        protosUnits = new ArrayList<>();
        for (int i = 0; i < UNIT_COUNT; i++) {
            protosUnits.add(protosUnitTypes.get(RandomUnitGenerator.generate(0, PLAYER_UNIT_COUNT)));
        }
        return protosUnits;
    }
}
