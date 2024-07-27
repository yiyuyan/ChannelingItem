package cn.ksmcbrigade.ci.mixin;

import cn.ksmcbrigade.ci.utils.AbstractArrowUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public class BowMixin {
    @Inject(locals = LocalCapture.CAPTURE_FAILSOFT,method = "releaseUsing",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;shootFromRotation(Lnet/minecraft/world/entity/Entity;FFFFF)V",shift = At.Shift.AFTER))
    public void bow(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_, CallbackInfo ci, Player player, boolean flag, ItemStack itemstack, int i, float f, boolean flag1, ArrowItem arrowitem, AbstractArrow abstractarrow){
        ((AbstractArrowUtil)abstractarrow).set(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING,p_40667_)>0);
    }
}
