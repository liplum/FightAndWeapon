package net.liplum.attributes;

public enum ComputeType {
    Full(true, true),
    Only_Rate(true, false),
    Only_Mastery(false, true),
    Only_Gemstone(true, false),
    Only_Base(false, false);

    public final boolean computeModifier;
    public final boolean computeMastery;

    ComputeType(boolean computeModifier, boolean computeMastery) {
        this.computeModifier = computeModifier;
        this.computeMastery = computeMastery;
    }
}
