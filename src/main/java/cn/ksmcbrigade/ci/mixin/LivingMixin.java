package cn.ksmcbrigade.ci.mixin;

import cn.ksmcbrigade.ci.ChannelingItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(LivingEntity.class)
public abstract class LivingMixin {

    @Shadow public abstract Iterable<ItemStack> getArmorSlots();

    @Inject(method = "checkTotemDeathProtection",at = @At(value = "INVOKE",target = "Lnet/minecraft/advancements/critereon/UsedTotemTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;)V",shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILSOFT)
    public void usedTotem(DamageSource p_21263_, CallbackInfoReturnable<Boolean> cir,ItemStack itemstack){
        try {
            if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING,itemstack)>0 && p_21263_.getEntity()!=null){
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,p_21263_.getEntity().level());
                lightningBolt.setPos(p_21263_.getEntity().getPosition(0));
                if(!ChannelingItem.fire) lightningBolt.setVisualOnly(true);
                p_21263_.getEntity().level().addFreshEntity(lightningBolt);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Inject(method = "hurt",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/WalkAnimationState;setSpeed(F)V",shift = At.Shift.AFTER))
    public void hurt(DamageSource p_21016_, float p_21017_, CallbackInfoReturnable<Boolean> cir){
        if(p_21016_.getEntity()!=null && ci$hasChanneling(this.getArmorSlots().iterator())){
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,p_21016_.getEntity().level());
            lightningBolt.setPos(p_21016_.getEntity().getPosition(0));
            if(!ChannelingItem.fire) lightningBolt.setVisualOnly(true);
            p_21016_.getEntity().level().addFreshEntity(lightningBolt);
        }
    }

    @Unique
    public boolean ci$hasChanneling(Iterator<ItemStack> stacks){
        while (stacks.hasNext()){
            ItemStack item = stacks.next();
            if((item.getItem() instanceof ArmorItem armorItem) && !armorItem.getType().equals(ArmorItem.Type.BOOTS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING,item)>0){
                return true;
            }
        }
        return false;
    }
}
