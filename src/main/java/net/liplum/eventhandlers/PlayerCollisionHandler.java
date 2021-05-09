package net.liplum.eventhandlers;

//@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class PlayerCollisionHandler {
    /*@SubscribeEvent
    public static void onPlayerCollied(PlayerCollisionEvent e){
        EntityPlayer p = e.player;
        World w = p.world;
        for(Entity entity : e.collided){
            if(entity instanceof EntityLiving){
                entity.attackEntityFrom(DamageSource.causePlayerDamage(p),1);
            }
        }
    }*/
/*    @SubscribeEvent
    public static void onLanceSprint(PlayerCollisionEvent e){
        EntityPlayer p = e.player;
        World w = p.world;
        ItemStack mainHeldStack = p.getHeldItemMainhand(),offHeldStack = p.getHeldItemOffhand();
        Item mainHeld = mainHeldStack.getItem(),offHeld = offHeldStack.getItem();
        LanceItem lance = JavaTool.getObjectWhichInstanceOf(LanceItem.class,mainHeld,offHeld);
        if(lance != null){
            ItemStack held = ItemTool.getItemStack(lance,mainHeldStack,offHeldStack);
        }
    }*/
}
