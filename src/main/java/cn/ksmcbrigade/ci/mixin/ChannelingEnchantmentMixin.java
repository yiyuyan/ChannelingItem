package cn.ksmcbrigade.ci.mixin;

import cn.ksmcbrigade.ci.ChannelingItem;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TridentChannelingEnchantment.class)
public abstract class ChannelingEnchantmentMixin extends Enchantment {
    protected ChannelingEnchantmentMixin(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Unique
    public boolean canEnchant(@NotNull ItemStack stack){
        return true;
    }

    @Unique
    public void doPostAttack(@NotNull LivingEntity p_44638_, @NotNull Entity p_44639_, int p_44640_){
        if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING,p_44638_.getItemInHand(p_44638_.getUsedItemHand()))>0 && !(p_44638_.getItemInHand(p_44638_.getUsedItemHand()).getItem() instanceof ArmorItem)){
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,p_44639_.level());
            lightningBolt.setPos(p_44639_.getPosition(0));
            if(!ChannelingItem.fire) lightningBolt.setVisualOnly(true);
            p_44639_.level().addFreshEntity(lightningBolt);
        }
    }
}
