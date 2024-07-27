package model;

public interface Equipped {
    Integer attackPower(Flyable flyable);

    Integer attackPower(Flightless flightless);
}
