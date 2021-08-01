package net.liplum.modifiers;

import net.liplum.api.annotations.Developing;
import net.liplum.api.weapon.Modifier;
import net.liplum.items.weapons.lance.LanceCore;
import net.liplum.items.weapons.lance.LanceCores;
import net.liplum.items.weapons.lance.LanceModifier;

import static net.liplum.Attributes.Generic.Strength;

@Developing
public final class RubyModifier {
    public final static LanceModifier Light_Lance = new LanceModifier() {

        @Override
        protected void build(Modifier.ModifierBuilder builder) {
            super.build(builder);
            builder.set(
                    Strength, 2, 0.2F
            );
        }

        @Override
        public LanceCore getCore() {
            return LanceCores.LightLance;
        }
    };
}
