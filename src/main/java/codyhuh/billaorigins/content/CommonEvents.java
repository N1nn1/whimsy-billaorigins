package codyhuh.billaorigins.content;

import codyhuh.billaorigins.BillaOrigins;
import codyhuh.billaorigins.client.particles.FeatherParticle;
import codyhuh.billaorigins.content.sound.HarpyFlightSound;
import codyhuh.billaorigins.registry.ItemRegistry;
import codyhuh.billaorigins.registry.ParticleTypeRegistry;
import codyhuh.billaorigins.registry.PowerRegistry;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static codyhuh.billaorigins.content.PlayerAccess.isBucketWithPlayer;
import static codyhuh.billaorigins.content.PlayerAccess.releaseBucketedPlayer;


@Mod.EventBusSubscriber(modid = BillaOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @Mod.EventBusSubscriber(modid = BillaOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onItemToss(ItemTossEvent event) {
            if (isBucketWithPlayer(event.getEntity().getItem())) {
                event.getEntity().setItem(Items.WATER_BUCKET.getDefaultInstance());
            }
        }
    }
}
