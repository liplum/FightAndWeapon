package net.liplum.masteries;

import net.liplum.attributes.IAttribute;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public final class AttrAmp {
    private static final Set<AttributeAmplifier> Pool = new HashSet<>();

    @Nonnull
    public static AttributeAmplifier newOne(@Nonnull IAttribute attribute, @Nonnull Number number) {
        AttributeAmplifier amp = attribute.newAttributeAmplifier(number);
        if (Pool.stream().noneMatch(amp::equals)) {
            Pool.add(amp);
        }
        return amp;
    }

    public static void empty() {
        Pool.clear();
    }
}
