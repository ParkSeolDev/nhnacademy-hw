package model;

public abstract class Upgrader implements Upgradable {
    private Integer range;

    Upgrader(Upgradable upgradable) {
        setRange(upgradable.range());
    }

    protected void setRange(Integer range) {
        this.range = range;
    }
    
    public Integer range() {
        return range;
    }
}
