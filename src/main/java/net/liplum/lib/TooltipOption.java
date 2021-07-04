package net.liplum.lib;

public class TooltipOption {
    private final boolean shiftPressed;
    private final boolean altPressed;
    private final boolean vanillaAdvanced;

    public TooltipOption(boolean shiftPressed, boolean altPressed, boolean vanillaAdvanced) {
        this.shiftPressed = shiftPressed;
        this.altPressed = altPressed;
        this.vanillaAdvanced = vanillaAdvanced;
    }

    public boolean isMoreDetailsShown() {
        return shiftPressed;
    }

    public boolean isUnitShown() {
        return altPressed;
    }

    public boolean isVanillaAdvanced() {
        return vanillaAdvanced;
    }
}
