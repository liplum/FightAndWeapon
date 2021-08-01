package net.liplum.masteries;

import net.liplum.attributes.DataType;
import net.liplum.attributes.IAttribute;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class AttrAmp {
    private static final Set<AttrAmp> Pool = new HashSet<>();
    @Nonnull
    private String attributeName = "";
    @Nonnull
    private DataType type = DataType.Int;
    @Nonnull
    private Number value = 0;

    private AttrAmp() {
    }

    public AttrAmp(@Nonnull String attributeName, @Nonnull DataType type, @Nonnull Number value) {
        this.attributeName = attributeName;
        this.type = type;
        this.value = value;
    }

    @Nonnull
    public static AttrAmp create(@Nonnull IAttribute attribute, @Nonnull Number number) {
        AttrAmp amp = new AttrAmp(attribute.getRegisterName(), attribute.getDataType(), number);
        if (Pool.stream().noneMatch(amp::equals)) {
            Pool.add(amp);
        }
        return amp;
    }

    public static void emptyPool() {
        Pool.clear();
    }

    @Nonnull
    public String getAttributeName() {
        return attributeName;
    }

    public AttrAmp setAttributeName(@Nonnull String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @Nonnull
    public DataType getType() {
        return type;
    }

    public AttrAmp setType(@Nonnull DataType type) {
        this.type = type;
        return this;
    }

    @Nonnull
    public Number getValue() {
        return value;
    }

    public AttrAmp setValue(@Nonnull Number value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AttrAmp) {
            AttrAmp amp = (AttrAmp) obj;
            return this.attributeName.equals(amp.attributeName) &&
                    this.type.equals(amp.getType()) &&
                    this.value.equals(amp.value);
        }
        return false;
    }
}
