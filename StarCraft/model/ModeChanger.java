package model;

public class ModeChanger extends Upgrader {
    private boolean isSiege;

    ModeChanger(Upgradable upgradable) {
        super(upgradable);
        if (!isSiege) {
            setRange(upgradable.range() + 2);
            isSiege = true;
        } else {
            setRange(upgradable.range() - 2);
            isSiege = false;
        }
    }
}
