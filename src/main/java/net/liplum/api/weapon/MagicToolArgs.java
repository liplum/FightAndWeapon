package net.liplum.api.weapon;

public class MagicToolArgs<This extends MagicToolArgs> extends WeaponArgs<This> {
    private  float abilityPower = 0;
    public MagicToolArgs(){}

    public float getAbilityPower() {
        return abilityPower;
    }

    public This setAbilityPower(float abilityPower) {
        this.abilityPower = abilityPower;
        return (This) this;
    }
}
