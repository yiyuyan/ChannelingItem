package cn.ksmcbrigade.ci.mixin;

import cn.ksmcbrigade.ci.ChannelingItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.Iterator;

@Mixin(ArmorItem.class)
public abstract class BootsMixin extends Item {
    @Shadow public abstract ArmorItem.Type getType();

    public BootsMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Unique
    public void inventoryTick(@NotNull ItemStack p_41404_, @NotNull Level p_41405_, @NotNull Entity p_41406_, int p_41407_, boolean p_41408_) {
        if(this.getType().equals(ArmorItem.Type.BOOTS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING,p_41404_)>0 && (p_41406_ instanceof LivingEntity livingEntity) && ci$toList(livingEntity.getArmorSlots().iterator()).contains(p_41404_)){
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,p_41405_);
            lightningBolt.setPos(p_41406_.getPosition(0));
            if(!ChannelingItem.fire) lightningBolt.setVisualOnly(true);
            p_41405_.addFreshEntity(lightningBolt);
        }
    }

    @Unique
    public ArrayList<ItemStack> ci$toList(Iterator<ItemStack> stacks){
        ArrayList<ItemStack> list = new ArrayList<>();
        while (stacks.hasNext()){
            list.add(stacks.next());
        }
        return list;
    }
}
