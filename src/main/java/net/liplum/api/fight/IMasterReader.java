package net.liplum.api.fight;

import net.liplum.masteries.Routine;

public interface IMasterReader {
    Routine readFrom(String path);
}
