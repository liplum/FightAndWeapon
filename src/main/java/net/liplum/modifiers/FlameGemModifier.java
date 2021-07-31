package net.liplum.modifiers;

import net.liplum.api.annotations.Developing;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.BoolAttribute;
import net.liplum.items.weapons.lance.LanceCore;
import net.liplum.items.weapons.lance.LanceCores;
import net.liplum.items.weapons.lance.LanceModifier;
import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.liplum.Attributes.Generic.DropsFireproof;

@Developing
public class FlameGemModifier {
    public final static LanceModifier Light_Lance = new LanceModifier() {
        @Override
        public LanceCore getCore() {
            return LanceCores.LightLance;
        }

        @Override
        public boolean releaseSkill(WeaponCore core, WeaponSkillArgs args) {
            World world = args.world();
            EntityLivingBase player = args.entity();

            Vec3d face = player.getLookVec();
            EntityLargeFireball fireball = new EntityLargeFireball(world, player, face.x, face.y + 1, face.z);
            EntityUtil.spawnEntityIfServer(world, fireball);
            FawItemUtil.damageWeapon(args.weapon(), args.itemStack(), 10, player);
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
