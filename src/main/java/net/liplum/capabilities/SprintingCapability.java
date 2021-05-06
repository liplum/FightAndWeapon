package net.liplum.capabilities;

public class SprintingCapability{
    private boolean isSprinting = false;

    public SprintingCapability() {
    }

    public boolean isSprinting() {
        return isSprinting;
    }

    public void setSprinting(boolean sprinting) {
        isSprinting = sprinting;
    }
}
