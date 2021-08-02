package net.liplum.api.weapon;

import net.liplum.attributes.AttrModifier;
import net.liplum.attributes.IAttrModifierBuilder;
import net.liplum.attributes.IAttribute;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class SimpleModifier implements IAttrModifierBuilder {
    @Nonnull
    private final Map<IAttribute, AttrModifier> waitingForBeAdded = new HashMap<>();
    @Nonnull
    private final WeaponCore weaponCore;

    public SimpleModifier(@Nonnull WeaponCore weaponCore) {
        this.weaponCore = weaponCore;
    }

    @Nonnull
    public SimpleModifier set(@Nonnull IAttribute attribute, @Nonnull AttrModifier modifier) {
        waitingForBeAdded.put(attribute, modifier);
        return this;
    }

    @Nonnull
    public Modifier build() {
        return new Modifier() {
            @Override
            public WeaponCore getCore() {
                return weaponCore;
            }

            @Override
            protected void build(ModifierBuilder builder) {
                super.build(builder);
                for (Map.Entry<IAttribute, AttrModifier> entry : waitingForBeAdded.entrySet()) {
                    builder.set(entry.getKey(), entry.getValue());
                }
            }
        };
    }
}