package net.liplum.api.weapon;

import net.liplum.attributes.Attribute;
import net.liplum.attributes.BasicAttrValue;
import net.liplum.attributes.IAttributeProvider;
import net.liplum.lib.items.WeaponBaseItem;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public abstract class WeaponCore implements IAttributeProvider<BasicAttrValue> {
    private final Map<Attribute, BasicAttrValue> allAttributes = new HashMap<>();

    public WeaponCore() {
        buildAttributes(Attribute.genAllBasicAttributes(new AttributeBuilder()));
    }

    /**
     * Get the corresponding value via the attribute
     *
     * @param attribute the attribute
     * @return the value or delta which can be used to compute final attribute value.<br/>
     * If this didn't contain any attribute value which can match the attribute type, it would return null.
     */
    @Override
    public BasicAttrValue getValue(@Nonnull Attribute attribute) {
        return allAttributes.get(attribute);
    }

    protected abstract void buildAttributes(AttributeBuilder builder);
    /*

     */
/**
 * Gets the cool down time of weapon.
 *
 * @return The cool down time of weapon(by tick)
 *//*

    public abstract int getCoolDown();

    public float getStrength() {
        return AttributeDefault.Generic.Strength;
    }

    public double getAttackReach() {
        return AttributeDefault.Generic.AttackReach;
    }

    */
/**
 * Gets the upcoming invincible time of a enemy who was attacked by this weapon.
 *
 * @return the invincible time(by tick)
 *//*

    public int getEnemyBreakingTime() {
        return AttributeDefault.Generic.EnemyBreakingTime;
    }

    */

    /**
     * Gets the knock back.the basic is 1.0F.
     *
     * @return the strength of knock back
     *//*

    public float getKnockbackStrength() {
        return AttributeDefault.Generic.KnockbackStrength;
    }
*/
    @Nonnull
    public abstract Class<? extends WeaponBaseItem<?>> getWeaponType();

    public class AttributeBuilder {
        public AttributeBuilder add(@Nonnull Attribute attribute, @Nonnull BasicAttrValue value) {
            allAttributes.put(attribute, value);
            return this;
        }
    }
}
