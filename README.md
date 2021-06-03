# [FAW] Fight And Weapon

Major Author : Liplum , Frost_Ansel , B1ackTea233

Programing : Liplum , B1ackTea233

Designer : Frost_Ansel

## How to create a new Weapon Core.

```Java
//Create a new class or just use a anonymous class. Make it extend a CONCRETE IWeaponCore class which you want
ILanceCore newLanceCore = new ILanceCore() {
    @Override
    public int getCoolDown() {
        return 20*6;//It means 120 ticks which is equivalent to 6 seconds in reality. 
    }
    
    @Override
    public boolean releaseSkill(LanceArgs args) {
        //Do something to show the player what this weapon can do.
        return true;
    }
    
    //Set the base Attribute. It will decide one or some certain final effect(s).
    @Override
    public float getSprintLength() {
        return 5;
    }
};

//Then new a weapon object, which expands the WeaponBaseItem<CoreType extends IWeaponCore>, by its constructor.
Item newLance = new LanceItem(LanceCoreTypes.newLanceCore)

//Finally, register this item into Minecraft Forge.
@SubscribeEvent
public static void onItemRegistry(RegistryEvent.Register<Item> event){
    IForgeRegistry<Item> items = event.getRegistry();
    items.register(newLance);
}
//Now you can see the new weapon in the game.
```

## How to create a new Gemstone.

```Java
//New a gemstone object. Please only use lower case letters and underline.
IGemstone newGem = new Gemstone("new_gem");

//And add one or some modifiers to the gemstone.
newGem.addModifier(newModifier);

//Finally, register it. But you can still add more modifiers or other things after registering.
GemstoneRegistry.Instance().register(newGem);
```

## How to create a new Modifier.

```Java
//Create a new class or just use a anonymous class. Make it extend a Modifier class which you want
LanceModifier newModifier = new LanceModifier(){
    //Claim this modifier is specific for which weapon core.
    @Override
    public CoreType getCoreType(){
        return WeaponCoreTypes.LanceCore;
    }
    
    @Override
    public float getStrengthDelta() {
        return 5;//It means that if there are a weapon with it , the weapon's Strength Attribute will increase 5.
    }
    
    //If you didn't overwrite it, it would release the skill in the original weapon core's version.
    @Override
    public boolean releaseSkill(ILanceCore core, LanceArgs args){
        //Do somethings.
        //Or you can even call the original weapon core's releaseSkill(LanceArgs) and add more(such as more particles).
        return true;//It stand for success of this releasing.
    }

}

//Please see the previous tutorial -- "How to create a new Gemstone".
//Now add the new modifier to a gemstone and register the gemstone.
NewGem.addModifier(yyyModifier);
```