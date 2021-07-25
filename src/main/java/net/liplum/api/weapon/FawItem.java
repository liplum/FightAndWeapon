package net.liplum.api.weapon;

import net.liplum.api.annotations.Developing;
import net.minecraft.item.Item;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Developing
public abstract class FawItem extends Item {
    public final Set<Category> categories = new HashSet<>();
    @Developing
    public FawItem(Category... categories) {
        Collections.addAll(this.categories, categories);
    }

    @Developing
    public boolean isA(Category category) {
        return categories.contains(category);
    }

    @Developing
    public void addCategory(Category... categories) {
        Collections.addAll(this.categories, categories);
    }

    @Developing
    public Category[] getCategories() {
        return categories.toArray(new Category[0]);
    }
}
