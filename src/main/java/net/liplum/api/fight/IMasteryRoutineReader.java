package net.liplum.api.fight;

import net.liplum.masteries.Routine;

public interface IMasteryRoutineReader {
    Routine readFrom(String path);
}
