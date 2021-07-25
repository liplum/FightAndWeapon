package net.liplum.modifiers;

import net.liplum.api.annotations.Developing;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.BoolAttribute;
import net.liplum.items.weapons.lance.LanceCore;
import net.liplum.items.weapons.lance.LanceCoreTypes;
import net.liplum.items.weapons.lance.LanceModifier;
import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.liplum.Attributes.Generic.DropsFireproof;
@Developing
public class FlameGemModifier {
    public final static LanceModifier Light_Lance = new LanceModifier() {
        @Override
        public LanceCore getCore() {
            return LanceCoreTypes.LightLance;
        }

        @Override
        public boolean releaseSkill(WeaponCore core, WeaponSkillArgs args) {
            World world = args.getWorld();
            EntityPlayer player = args.getPlayer();

            Vec3d face = player.getLookVec();
            EntityLargeFireball fireball = new EntityLargeFireball(world, player, face.x, face.y + 1, face.z);
            EntityUtil.spawnEntityIfServer(world, fireball);
            FawItemUtil.damageWeapon(args.getWeapon(), args.getItemStack(), 10, player);
            return true;
        }

        @Override
        protected void build(ModifierBuilder builder) {
            super.build(builder);
            builder.set(
                    DropsFireproof, BoolAttribute.TrueAttrModifier
            );
        }
    };
}
