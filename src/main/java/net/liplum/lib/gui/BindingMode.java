package net.liplum.lib.gui;

public enum BindingMode {
    TWO_WAY(true, true),
    ONLY_SEND(false, true),
    ONLY_GET(true, false);
    private final boolean sourceToTarget;
    private final boolean targetToSource;

    BindingMode(boolean sourceToTarget, boolean targetToSource) {
        this.sourceToTarget = sourceToTarget;
        this.targetToSource = targetToSource;
    }

    public boolean isSourceToTarget() {
        return sourceToTarget;
    }

    public boolean isTargetToSource() {
        return targetToSource;
    }
}
