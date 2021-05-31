package net.liplum.modifiers;

import net.liplum.items.weapons.lance.LanceCoreTypes;
import net.liplum.lib.cores.lance.ILanceCore;
import net.liplum.lib.cores.lance.LanceArgs;
import net.liplum.lib.modifiers.LanceModifier;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlameGemModifier {
    public final static LanceModifier Normal_Lance = new LanceModifier() {
        @Override
        public ILanceCore getCoreType() {
            return LanceCoreTypes.Normal;
        }

        @Override
        public boolean releaseSkill(ILanceCore core, LanceArgs args) {
            World world = args.getWorld();
            EntityPlayer player = args.getPlayer();

            Vec3d face = player.getLookVec();
            EntityLargeFireball fireball = new EntityLargeFireball(world,player,face.x,face.y,face.z);
            EntityUtil.spawnEntityIfServer(world,fireball);
            return true;
        }
    };
}
