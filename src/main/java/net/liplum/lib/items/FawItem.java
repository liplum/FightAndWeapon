package net.liplum.lib.items;

import net.liplum.entities.IndestructibleEntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class FawItem extends Item {
    public final Set<Category> categories = new HashSet<>();

    public FawItem(Category... categories) {
        //Player can only hold ONE Weapon in an item stack.
        setMaxStackSize(1);
        //Player can't repair the weapon in common way(an anvil)
        setNoRepair();
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

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nonnull
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        EntityItem entity = new IndestructibleEntityItem(world, location.posX, location.posY, location.posZ, itemstack);
        if (location instanceof EntityItem) {
            //TODO:Test
            // workaround for private access on that field >_>
            NBTTagCompound tag = new NBTTagCompound();
            location.writeToNBT(tag);
            entity.setPickupDelay(tag.getShort("PickupDelay"));
        }
        entity.motionX = location.motionX;
        entity.motionY = location.motionY;
        entity.motionZ = location.motionZ;
        return entity;
    }
}
