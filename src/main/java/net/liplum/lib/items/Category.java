package net.liplum.lib.items;

import net.liplum.Names;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private static Map<String,Category> AllCategories = new HashMap<>();
    public final String name;
    public Category(String name) {
        this.name = name;
        AllCategories.put(name,this);
    }

    public static final Category Unknown = new Category(Names.Category.Unknown);
    public static final Category Melee = new Category(Names.Category.Melee);
    public static final Category AOE = new Category(Names.Category.AOE);
    public static final Category Harvest = new Category(Names.Category.Harvest);
    public static final Category Magic = new Category(Names.Category.Magic);
    public static final Category Buffier = new Category(Names.Category.Buffier);
    public static final Category LongRange = new Category(Names.Category.LongRange);
    public static final Category Single = new Category(Names.Category.Single);
    public static final Category Tool = new Category(Names.Category.Tool);
    public static final Category Weapon = new Category(Names.Category.Weapon);
}