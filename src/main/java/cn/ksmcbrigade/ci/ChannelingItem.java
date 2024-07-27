package cn.ksmcbrigade.ci;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Mod(ChannelingItem.MODID)
@Mod.EventBusSubscriber(modid = ChannelingItem.MODID)
public class ChannelingItem {

    public static final String MODID = "ci";

    public static int exp = 6;
    public static boolean fire = true;

    public ChannelingItem() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        File file = new File("config/ci-config.json");
        if(!file.exists()){
            JsonObject json = new JsonObject();
            json.addProperty("exp",exp);
            json.addProperty("fire",fire);
            Files.write(file.toPath(),json.toString().getBytes());
        }
        exp = JsonParser.parseString(Files.readString(file.toPath())).getAsJsonObject().get("exp").getAsInt();
        fire = JsonParser.parseString(Files.readString(file.toPath())).getAsJsonObject().get("fire").getAsBoolean();
    }

    @SubscribeEvent
    public static void RegisterCommand(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("ci-inject").executes(context -> {
            Player entity = (Player)context.getSource().getEntity();
            if (entity != null) {
                if(!entity.getMainHandItem().isEmpty()){
                    if(entity.experienceLevel>=exp){
                        entity.getMainHandItem().enchant(Enchantments.CHANNELING,1);
                        entity.giveExperienceLevels(-exp);
                    }
                    else{
                        entity.sendSystemMessage(Component.nullToEmpty(I18n.get("commands.ci.cannot_xp").replace("{x}",String.valueOf(exp))));
                    }
                }
                else if(!entity.getOffhandItem().isEmpty()){
                    if(entity.experienceLevel>=exp){
                        entity.getOffhandItem().enchant(Enchantments.CHANNELING,1);
                        entity.giveExperienceLevels(-exp);
                    }
                    else{
                        entity.sendSystemMessage(Component.nullToEmpty(I18n.get("commands.ci.cannot_xp").replace("{x}",String.valueOf(exp))));
                    }
                }
                else{
                    entity.sendSystemMessage(Component.translatable("commands.ci.empty"));
                }
            }
            return 0;
        }));
    }
}
