package net.liplum.entities;

import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.BoolAttribute;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static net.liplum.Attributes.Generic.DropsFireproof;

public class FawWeaponItemEntity extends IndestructibleItemEntity {
    public FawWeaponItemEntity(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.isImmuneToFire = true;
    }

    public FawWeaponItemEntity(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
        this.isImmuneToFire = true;
    }

    public FawWeaponItemEntity(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        boolean res = super.attackEntityFrom(source, amount);
        ItemStack itemStack = this.getItem();
        Item item = itemStack.getItem();
        if (item instanceof WeaponBaseItem) {
            WeaponBaseItem weapon = (WeaponBaseItem) item;
            if (source.isFireDamage()) {
                AttrCalculator calculator = new AttrCalculator(weapon).modifier(GemUtil.getModifierFrom(itemStack));
                if (!BoolAttribute.toBool(calculator.calcu(DropsFireproof))) {
                    //FIXME:When it floats on fluid lava, it loses durability very fast because of the duplication of In Fire damage.
                    ItemUtil.decreaseItemDurability(itemStack, 1);
                }
            }
        }
        return res;
    }
}
