package net.liplum.api.fight;

import net.liplum.masters.Routine;

public interface IMasterReader {
    Routine readFrom(String path);
}
