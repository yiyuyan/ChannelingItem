package cn.ksmcbrigade.ci.mixin;

import cn.ksmcbrigade.ci.ChannelingItem;
import cn.ksmcbrigade.ci.utils.AbstractArrowUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin implements AbstractArrowUtil {

    @Unique
    public boolean ci$hasChanneling = false;

    @Override
    public void set(boolean has) {
        this.ci$hasChanneling = has;
    }

    @Override
    public boolean has() {
        return this.ci$hasChanneling;
    }

    @Inject(method = "onHitEntity",at = @At("HEAD"))
    public void onHit(EntityHitResult p_36757_, CallbackInfo ci){
        if(this.has()){
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,p_36757_.getEntity().level());
            lightningBolt.setPos(p_36757_.getEntity().getPosition(0));
            if(!ChannelingItem.fire) lightningBolt.setVisualOnly(true);
            p_36757_.getEntity().level().addFreshEntity(lightningBolt);
        }
    }
}
