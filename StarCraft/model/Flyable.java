package model;

public interface Flyable {
    Integer attackPower(Flyable flyable);

    Integer attackPower(Flightless flightless);
}
