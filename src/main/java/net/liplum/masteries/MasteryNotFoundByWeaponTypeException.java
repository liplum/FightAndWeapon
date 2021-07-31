package net.liplum.masteries;

public class MasteryNotFoundByWeaponTypeException extends RuntimeException {
    public MasteryNotFoundByWeaponTypeException(String message) {
        super(message);
    }
}
