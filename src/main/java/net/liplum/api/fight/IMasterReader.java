package net.liplum.api.fight;

import net.liplum.lib.masters.Routine;

public interface IMasterReader {
    Routine readFrom(String path);
}
