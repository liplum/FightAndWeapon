package net.liplum.items.weapons.lance;

import net.liplum.Attributes;
import net.liplum.api.weapon.Modifier;
import net.liplum.attributes.Attribute;

import java.util.LinkedList;
import java.util.List;

public abstract class LanceModifier extends Modifier<LanceCore> {
    @Override
    protected List<Attribute> initAllAttributes() {
        LinkedList<Attribute> list = new LinkedList<>();
        list.add(Attributes.Lance.SprintStrength);
        return list;
    }

    @Override
    protected void buildAttributes(Modifier<LanceCore>.AttributeBuilder builder) {

    }

  /*  public float getSprintLengthDelta() {
        return 0;
    }

    public float getSprintLengthRate() {
        return 0;
    }*/

    public boolean releaseSkill(LanceCore core, LanceArgs args) {
        return core.releaseSkill(args);
    }
}
