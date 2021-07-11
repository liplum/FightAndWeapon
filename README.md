# [FAW] Fight And Weapon

Major Author : Liplum , Frost_Ansel

Programing : Liplum

Designer : Frost_Ansel

## How to create a new Weapon Core.

```Java
// Create a new class or just use a anonymous class.
// Make it extend a CONCRETE IWeaponCore class which you want.
ILanceCore newLanceCore = new ILanceCore() {
    @Override
    public int getCoolDown() {
        // It means 120 ticks which is equivalent to 6 seconds in reality. 
        return 20*6;
    }
    
    @Override
    public boolean releaseSkill(LanceArgs args) {
        // Do something to show the player what this weapon can do.
        return true;
    }
    
    // Set the base Attribute. It will decide one or some certain final effect(s).
    @Override
    public float getSprintLength() {
        return 5;
    }
};

// Then new a weapon object, which expands the WeaponBaseItem<CoreType extends IWeaponCore>,
// by its constructor.
Item newLance = new LanceItem(LanceCoreTypes.newLanceCore)

// Finally, register this item into Minecraft Forge.
@SubscribeEvent
public static void onItemRegistry(RegistryEvent.Register<Item> event){
    IForgeRegistry<Item> items = event.getRegistry();
    items.register(newLance);
}
// Now you can see the new weapon in the game.
```

## How to create a new Gemstone.

```Java
// New a gemstone object. Please only use lower case letters and underline.
IGemstone newGem = new Gemstone("new_gem");

// And add one or some modifiers to the gemstone.
newGem.addModifier(newModifier);

// Finally, register it. But you can still add more modifiers or other things after registering.
GemstoneRegistry.Instance().register(newGem);
```

## How to create a new Modifier.

```Java
// Create a new class or just use a anonymous class.
// Make it extend a Modifier class which you want.
LanceModifier newModifier = new LanceModifier(){
    // Claim this modifier is specific for which weapon core.
    @Override
    public CoreType getCoreType(){
        return WeaponCoreTypes.LanceCore;
    }
    
    @Override
    public float getStrengthDelta() {
        // It means that if there are a weapon with it , the weapon's Strength Attribute will increase 5.
        return 5;
    }
    
    // If you didn't overwrite it, it would release the skill in the original weapon core's version.
    @Override
    public boolean releaseSkill(ILanceCore core, LanceArgs args){
        // Do somethings.
        // Or you can even call the original weapon core's releaseSkill(LanceArgs)
        // and add more(such as more particles).
        return true;//It stand for success of this releasing.
    }

}

// Please see the previous tutorial -- "How to create a new Gemstone".
// Now add the new modifier to a gemstone and register the gemstone.
newGem.addModifier(yyyModifier);
```

## How to create a new Passive Skill and register it.

```Java
// You can see this passive skill in Class MagicPearlSkills

IPassiveSkill<WeaponPreAttackEvent> Magicize =
        SkillRegistry.registerPassiveSkill(
        // Create a new class or just use a anonymous class.
        // Make it extend a Modifier class which you want.
        // The generic parameter stands for which event you need to subscribe
        new IPassiveSkill<WeaponPreAttackEvent>() {
            
    @Override
    public Class<WeaponPreAttackEvent> getEventType() {
        // You have to return the Class Object of the event
        return WeaponPreAttackEvent.class;
    }

    @Override
    public PSkillResult onTrigger(WeaponPreAttackEvent event){
        // Just do something.
        DamageSource damageSource = event.getDamageSource();
        damageSource.setMagicDamage();
        
        // If the passive skills triggers successful or did no effect then return PSkillResult.Complete.
        // If it triggered failed then returned PSkillResult.Fail.
        // NOTE:If you want to cancel the rest of passive skills then return PSkillResult.CancelTrigger.
        return PSkillResult.Complete;
    }
    
    @Override
    public String getRegisterName() {
        // Name it and ensure it unique.
        // If you want to change it, please change it synchronously in anywhere
        return "Magicize";
    }
});

// How to register it to a Gemstone
// addPassiveSkillToAll(IPassiveSkill) will make all weapons held by a player can trigger the skills.
newGem.addPassiveSkillToAll(Magicize);

// addPassiveSkillToWeaponType(Class,IPassiveSkill) will make all weapons only in the same weapon type you gave can trigger the skills.
newGem.addPassiveSkillToWeaponType(LanceItem.class,Magicize);

//addPassiveSkillToCore(IWeaponCore,IPassiveSkill) will make only this weapon core can trigger the skills.
newGem.addPassiveSkillToCore(newLanceCore,Magicize);

// How to register it to a mastery
// To be continued...
```