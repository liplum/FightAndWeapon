package net.liplum.items.weapons;

import java.util.Random;

public interface ITestInterface {
    default int Test(){
        Random r = new Random();
        return r.nextInt(100);
    }
}
