package net.liplum.masteries;

import net.liplum.attributes.DataType;
import net.liplum.attributes.IAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class AttrAmp {
    private static final Set<AttrAmp> Pool = new HashSet<>();
    @NotNull
    private String attributeName = "";
    @NotNull
    private DataType type = DataType.Int;
    @NotNull
    private Number value = 0;

    private AttrAmp() {
    }

    public AttrAmp(@NotNull String attributeName, @NotNull DataType type, @NotNull Number value) {
        this.attributeName = attributeName;
        this.type = type;
        this.value = value;
    }

    @NotNull
    public static AttrAmp create(@NotNull IAttribute attribute, @NotNull Number number) {
        AttrAmp amp = new AttrAmp(attribute.getRegisterName(), attribute.getDataType(), number);
        if (Pool.stream().noneMatch(amp::equals)) {
            Pool.add(amp);
        }
        return amp;
    }

    public static void emptyPool() {
        Pool.clear();
    }

    @NotNull
    public String getAttributeName() {
        return attributeName;
    }

    public AttrAmp setAttributeName(@NotNull String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @NotNull
    public DataType getType() {
        return type;
    }

    public AttrAmp setType(@NotNull DataType type) {
        this.type = type;
        return this;
    }

    @NotNull
    public Number getValue() {
        return value;
    }

    public AttrAmp setValue(@NotNull Number value) {
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
