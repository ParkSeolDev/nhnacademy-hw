package model;

public class RangeUpgrader extends Upgrader {

    RangeUpgrader(Upgradable upgradable) {
        super(upgradable);
        setRange(upgradable.range() + 1);
    }
}
