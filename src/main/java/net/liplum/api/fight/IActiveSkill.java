package net.liplum.api.fight;

import javax.annotation.Nonnull;

public interface IActiveSkill extends ISkill {
    @Nonnull
    String getRegisterName();
}
