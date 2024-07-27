package cn.ksmcbrigade.ci.mixin;

import cn.ksmcbrigade.ci.utils.AbstractArrowUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CrossbowItem.class)
public class CrossBowMixin {
    @Inject(locals = LocalCapture.CAPTURE_FAILSOFT,method = "getArrow",at = @At(value = "RETURN"), cancellable = true)
    private static void bow(Level p_40915_, LivingEntity p_40916_, ItemStack p_40917_, ItemStack p_40918_, CallbackInfoReturnable<AbstractArrow> cir, ArrowItem arrowitem, AbstractArrow abstractarrow, int i){
        ((AbstractArrowUtil)abstractarrow).set(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING,p_40917_)>0);
        cir.setReturnValue(abstractarrow);
    }
}
