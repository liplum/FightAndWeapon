package net.liplum.items.weapons;

import com.google.common.collect.Multimap;
import net.liplum.Names;
import net.liplum.lib.utils.FawGenerator;
import net.liplum.registries.ItemRegistry;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class QuartzSwordItem extends ItemSword {
    private static final UUID uuid = UUID.randomUUID();

/*    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote) {
            StringTextComponent str = new StringTextComponent("YEAH!!!");
            playerIn.sendMessage(str, UUID.randomUUID());
        }
        return new ActionResult<>(ActionResultType.SUCCESS,playerIn.getHeldItem(handIn));
    }*/

    public QuartzSwordItem() {
        super(ToolMaterial.IRON);
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
        playerIn.addItemStackToInventory(FawGenerator.genWeaponWithGemstone(ItemRegistry.Light_Lance_Item, Names.Gemstone.RubyN));
        playerIn.addItemStackToInventory(FawGenerator.genWeaponWithGemstone(ItemRegistry.Light_Lance_Item, Names.Gemstone.EndergemN));
        playerIn.addItemStackToInventory(FawGenerator.genWeaponWithGemstone(ItemRegistry.Light_Lance_Item, Names.Gemstone.FlamegemN));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @NotNull
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(@NotNull EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> map = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            map.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuid, "Weapon modifier", 10, 0));
        }
        return map;
    }
}
