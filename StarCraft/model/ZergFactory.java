package model;

import java.util.ArrayList;
import java.util.List;

import util.RandomUnitGenerator;

public class ZergFactory extends Factory {
    private static final int UNIT_COUNT = 8;
    private static final int PLAYER_UNIT_COUNT = 6;

    private List<ZergUnit> zergUnitTypes = List.of(
        Zergling.create(),
        Hydralisk.create(),
        Ultralisk.create(),
        Mutalisk.create(),
        Guardian.creaet(),
        Queen.create()
    );
    private List<Unit> zergUnits;

    @Override
    public List<Unit> createUnits() {
        zergUnits = new ArrayList<>();
        for (int i = 0; i < UNIT_COUNT; i++) {
            zergUnits.add(zergUnitTypes.get(RandomUnitGenerator.generate(0, PLAYER_UNIT_COUNT)));
        }
        return zergUnits;
    }
}
