package net.liplum.attributes;

public enum ComputeType {
    Full(true, true),
    Only_Rate( true, false),
    Only_Master(false, true),
    Only_Gemstone( true, false),
    Only_Base(false, false);

    public final boolean computeModifier;
    public final boolean computeMaster;

    ComputeType( boolean computeModifier, boolean computeMaster) {
        this.computeModifier = computeModifier;
        this.computeMaster = computeMaster;
    }
}
