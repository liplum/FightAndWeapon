package net.liplum.api.weapon;

import net.liplum.attributes.AttrModifier;
import net.liplum.attributes.IAttrModifierBuilder;
import net.liplum.attributes.IAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SimpleModifier implements IAttrModifierBuilder {
    @NotNull
    private final Map<IAttribute, AttrModifier> waitingForBeAdded = new HashMap<>();
    @NotNull
    private final WeaponCore weaponCore;

    public SimpleModifier(@NotNull WeaponCore weaponCore) {
        this.weaponCore = weaponCore;
    }

    @NotNull
    public SimpleModifier set(@NotNull IAttribute attribute, @NotNull AttrModifier modifier) {
        waitingForBeAdded.put(attribute, modifier);
        return this;
    }

    @NotNull
    public Modifier build() {
        return new Modifier() {
            @Override
            public WeaponCore getCore() {
                return weaponCore;
            }

            @Override
            protected void build(@NotNull ModifierBuilder builder) {
                super.build(builder);
                for (Map.Entry<IAttribute, AttrModifier> entry : waitingForBeAdded.entrySet()) {
                    builder.set(entry.getKey(), entry.getValue());
                }
            }
        };
    }
}