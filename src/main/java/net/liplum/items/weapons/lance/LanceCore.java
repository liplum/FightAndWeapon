package net.liplum.items.weapons.lance;

import net.liplum.Attributes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.attributes.Attribute;

import javax.annotation.Nonnull;

public abstract class LanceCore extends WeaponCore {
    public abstract boolean releaseSkill(LanceArgs args);
    /*

     */

    @Override
    protected void buildAttributes(AttributeBuilder builder) {
        Attribute sprintStrength = Attributes.Lance.SprintStrength;
        builder.add(
                sprintStrength, sprintStrength.genBasicAttrValue()
        );
    }

    /**
     * The double value of this is the true length of a sprint.
     *
     * @return
     *//*

    public abstract float getSprintLength();
*/
    @Nonnull
    @Override
    public Class<LanceItem> getWeaponType() {
        return LanceItem.class;
    }
}
