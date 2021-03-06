# [FAW] Fight And Weapon

Major Author : Liplum , Frost_Ansel

Programing : Liplum

Designer : Frost_Ansel

## How to create a new Weapon Core.

```Java
// Create a new class or just use a anonymous class.
// Make it extend a CONCRETE WeaponCore class which you want.
LanceCore newLanceCore = new LanceCore() {
    @Override
    public boolean releaseSkill(WeaponSkillArgs args) {
        // Code something to show the player what this weapon can do with the Weapon Skill.
        // If the skill was canceled for some reasons, please return false.
        return true;
    }

    @Override
    // Now configure this Weapon Core
    protected void build(WeaponCoreBuilder builder) {
        // Make sure you call its super class's one.
        super.build(builder);
        // Set the all Attribute you want.
        // It will decide one or some certain final effect(s).
        builder.set(
        // You can find all the attributes in the net.liplum.Attributes. 
            SprintStrength, SprintStrength.newBasicAttrValue(2F)
        ).set(
            CoolDown, SprintStrength.newBasicAttrValue(6 * 20)
        ).set(
            Strength, SprintStrength.newBasicAttrValue(5F)
        ).set(
            AttackReach, AttackReach.newBasicAttrValue(8F)
        );
    }
};

// Finally, register a item into Minecraft Forge with the Weapon Core as a parameter.
Item newLance = new LanceItem(newLanceCore)

// Now you can see the new weapon in the game.
```

## How to create a new Gemstone and its Modifier.

```Java
// New a gemstone object. Please only use lower case letters and underline.
// net.liplum.api.Gemstone
IGemstone newGem = new Gemstone("new_gem");

public final static LanceModifier newModifier = new LanceModifier() {
    @Override
    public LanceCore getCore() {
        return newLanceCore;
    }

    @Override
    protected void build(Modifier.ModifierBuilder builder) {
        // Make sure you call its super class's one.
        super.build(builder);
        builder.set(
        // Set the all Attribute Modifier you want.
            SprintStrength, SprintStrength.newAttrModifier(2, 0)
        );
    }

    @Override
    public boolean releaseSkill(WeaponCore core, WeaponSkillArgs args) {
        // Code something to show the player How this gemstone changes the Weapon Skill.
        // The default behavior is calling the Weapon Core on (aka there is no change.)
        // If the skill was canceled for some reasons, please return false.
        return true;
    }
};

// And add one or some modifier(s) to the gemstone.
newGem.addModifier(newModifier);

// Finally, register it. But you can still add more modifiers or other things after registering.
// Oh, fortunately, when you new a Gemstone Object, it will register itself automatically.
```

## How to create a new Attribute and Access it.

```Java
// The Attribute -- Normal One
IAttribute testAtrribute = new Attribute()
        // Set the unique Register Name
        .setRegisterName(Names.Attribute.Generic.Strength)
        // How to get the I18n key which is used in tooltip showing
        .setHowToGetI18nKey(I18ns.Attribute::Generic)
        // Basic Attribute will be initialized in all Weapon Core automatically
        .setBasic()
        // There are only two choices--Int or Float
        .setDataType(DataType.Float)
        // Whether this attribute is shown in tooltip
        // while the player open inventory and their mouse is hovering on the ItemStack
        // the default is true 
        .setShownInTooltip(false)
        // It will be applied in tooltip showing
        // The default is null which means there will use original value
        .setFormat("%.1f")
        // There are some different algorithms to calculate the final value
        .setComputeType(ComputeType.Full)
        // The sequence in tooltip showing
        // Smaller number means front
        // Larger number means later
        .setDisplayPriority(-100)
        // The default value when initialized automatically until you set it manually
        .setDefaultValue(0)
        // The minimum value attribute can return (>=0)
        // It also changes the default value to equal it if is more than it.
        .setMinimum(0);
        // If necessary, it can be mapped to another value
        // The default is return the same value
        .setTooltipShownMapping(n -> n.floatValue() / 20)//It's equivalent to Vanilla::PerSecond
        // Whether player have to pressed the shift key continuously to show this attribute in tooltip
        .setNeedMoreDetailsToShown()
        // Whether use a special value when the weapon is broken
        // The default is false
        .setUseSpecialValueWhenWeaponBroken()
        // The value when a broken weapon want to access this Attribute
        // The default is return the default value
        .setSpecialValueWhenWeaponBroken(n -> 0);
        
// Now you can new a AttrCalculator object
AttrCalculator calculator = new AttrCalculator()
    // The Weapon Core object -- It mustn't be null otherwise it throws IllegalArgumentException 
    .setWeaponCore(newLanceCore)
    // The Modifier -- Can be null which means this weapon hasn't been inlaid yet
    .setModifier(newGem.getModifierOf(newLanceCore))
    // The Player Entity -- Can be null which means the Attribute Access doesn't come from a player
    // and maybe it comes from a zombie or it is even a drop.    
    .setPlayer(player)
    // The weapon ItemStack -- Can be null because sometimes you cannot get any detail about the ItemStack's NBT
    // or you don't need to know that.
    // NOTE:If null, the Attribute Access cannot be changed to a special value due to no Weapon's Durability Info    
    .setItemStack(itemStack)
    // Both it and Attribute's one can decide whether tooltip shows the special value with a broken weapon in the same time
    // The default is true
    .setUseSpecialValueWhenWeaponBroken(true)
    // If you set call it, Attribute Access cannot be modified in this time
    // The default is true.
    .setNotPostEvent();

//How to use the calculator
FinalAttrValue finalValue = calculator.calcu(testAtrribute);
float res = finalValue.getFloat()

// The Boolean Attribute
// As default, it cannot be shown in tooltip
IAttribute DropsFireproof = new BoolAttribute()
    .setRegisterName(Names.Attribute.Generic.DropsFireproof)
    .setBasic()
    // There are only FOUR options excluding Only_Rate 
    .setComputeType(ComputeType.Only_Gemstone)
    .setOnlyGemstoneCompute((base, modifier) -> base || modifier)
    .setDefaultValue(false);
```

## How to create a new Passive Skill and register it.

```Java
IPassiveSkill<WeaponAttackEvent.Attacking> Magicize =
        // The first parameter is its unique Register Name
        // The second one is the event it has to subscribe
        new PassiveSkill<WeaponAttackEvent.Attacking>(Names.PassiveSkill.Magicize, WeaponAttackEvent.Attacking.class) {
    @Nonnull
    @Override
    public PSkillResult onTrigger(@Nonnull WeaponAttackEvent.Attacking event) {
        // Code something to show the player what the passive skill can do.
        // If the Passive Skill triggers successfully, please return Complete.
        // If it triggered failed, please return Fail.
        // If it need cancel the event--such as canceling the EntityFallEvent, please return Cancel Trigger
        // to end the rest of other Passive Skills ahead of time.
        return PSkillResult.Complete;
    }
};

// How to register it to a Gemstone
// addPassiveSkillToAll(IPassiveSkill) will make all weapons held by a player can trigger the skills.
newGem.addPassiveSkillToAll(Magicize);

// addPassiveSkillToWeaponType(WeaponType,IPassiveSkill) will make all weapons only in the same weapon type you gave can trigger the skills.
newGem.addPassiveSkillToWeaponType(CertainWeaponType,Magicize);

//addPassiveSkillToCore(IWeaponCore,IPassiveSkill) will make only this weapon core can trigger the skills.
newGem.addPassiveSkillToCore(newLanceCore,Magicize);

// How to register it to a mastery
// To be continued...
```