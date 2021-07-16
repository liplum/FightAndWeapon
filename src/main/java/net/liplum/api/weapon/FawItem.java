package net.liplum.api.weapon;

import net.minecraft.item.Item;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class FawItem extends Item {
    public final Set<Category> categories = new HashSet<>();

    public FawItem(Category... categories) {
        Collections.addAll(this.categories, categories);
    }

    public boolean isA(Category category) {
        return categories.contains(category);
    }

    public void addCategory(Category... categories) {
        Collections.addAll(this.categories, categories);
    }

    public Category[] getCategories() {
        return categories.toArray(new Category[0]);
    }
}
