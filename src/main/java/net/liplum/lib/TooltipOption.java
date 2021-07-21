package net.liplum.lib;

public class TooltipOption {
    private final boolean shiftPressed;
    private final boolean altPressed;
    private final boolean ctrlPressed;
    private final boolean vanillaAdvanced;

    public TooltipOption(boolean shiftPressed, boolean altPressed, boolean ctrlPressed, boolean vanillaAdvanced) {
        this.shiftPressed = shiftPressed;
        this.altPressed = altPressed;
        this.ctrlPressed = ctrlPressed;
        this.vanillaAdvanced = vanillaAdvanced;
    }

    public boolean isMoreDetailsShown() {
        return shiftPressed;
    }

    public boolean isUnitShown() {
        return altPressed;
    }

    public boolean isWeaponSkillTipShown(){
        return ctrlPressed;
    }

    public boolean isVanillaAdvanced() {
        return vanillaAdvanced;
    }
}
