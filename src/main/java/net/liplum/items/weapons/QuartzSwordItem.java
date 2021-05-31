package net.liplum.items.weapons;

import com.google.common.collect.Multimap;
import net.liplum.lib.utils.FawGenerator;
import net.liplum.registeies.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.UUID;

public class QuartzSwordItem extends ItemSword {
    public QuartzSwordItem() {
        super(ToolMaterial.IRON);
    }

/*    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote) {
            StringTextComponent str = new StringTextComponent("YEAH!!!");
            playerIn.sendMessage(str, UUID.randomUUID());
        }
        return new ActionResult<>(ActionResultType.SUCCESS,playerIn.getHeldItem(handIn));
    }*/

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.addItemStackToInventory(FawGenerator.genWeaponWithGemstone(ItemRegistry.Lance_Item, "Ruby"));
        playerIn.addItemStackToInventory(FawGenerator.genWeaponWithGemstone(ItemRegistry.Lance_Item, "Endergem"));
        playerIn.addItemStackToInventory(FawGenerator.genWeaponWithGemstone(ItemRegistry.Lance_Item, "Flamegem"));
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    private static UUID uuid = UUID.randomUUID();

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> map = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            map.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuid, "Weapon modifier", 10, 0));
        }
        return map;
    }
}
