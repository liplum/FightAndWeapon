package net.liplum.modifiers;

import net.liplum.api.weapon.Modifier;
import net.liplum.items.weapons.lance.LanceCoreTypes;
import net.liplum.items.weapons.lance.LanceCore;
import net.liplum.items.weapons.lance.LanceModifier;
import static net.liplum.Attributes.Lance.*;
import static net.liplum.Attributes.Generic.*;

public final class RubyModifier {
    public final static LanceModifier Light_Lance = new LanceModifier() {

        @Override
        protected void buildAttributes(Modifier<LanceCore>.AttributeBuilder builder) {
            super.buildAttributes(builder);
            builder.set(
                    Strength, Strength.newAttrModifier(2,0.2F)
            );
        }

        @Override
        public LanceCore getCoreType() {
            return LanceCoreTypes.LightLance;
        }
    };
}
