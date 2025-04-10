package codyhuh.billaorigins.client;

import codyhuh.billaorigins.BillaOrigins;
import codyhuh.billaorigins.client.particles.FeatherParticle;
import codyhuh.billaorigins.content.PlayerAccess;
import codyhuh.billaorigins.content.sound.HarpyFlightSound;
import codyhuh.billaorigins.registry.ParticleTypeRegistry;
import codyhuh.billaorigins.registry.PowerRegistry;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = BillaOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleTypeRegistry.OWL_HARPY_FEATHER.get(), FeatherParticle.Factory::new);
    }

    @Mod.EventBusSubscriber(modid = BillaOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEvents {
        private static boolean wasFlying = false;
        private static HarpyFlightSound currentFlightSound = null;

        @SubscribeEvent
        public static void livingEntityRenderer(RenderLivingEvent<LivingEntity, EntityModel<LivingEntity>> event) {
            if (event.getEntity() instanceof PlayerAccess access && access.getBucketOwner() != null) {
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;

            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;

            if (player == null) return;

            boolean isFlyingNow = player.getAbilities().flying && IPowerContainer.hasPower(player, PowerRegistry.FLIGHT_POWER.get());

            if (isFlyingNow && !wasFlying) {
                currentFlightSound = new HarpyFlightSound(player);
                mc.getSoundManager().play(currentFlightSound);
            }

            if (!isFlyingNow && currentFlightSound != null) {
                currentFlightSound.stopSound();
                currentFlightSound = null;
            }

            wasFlying = isFlyingNow;
        }

        @SubscribeEvent
        public static void cameraOverlay(ViewportEvent.ComputeCameraAngles event) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;

            if (player instanceof PlayerAccess access) {
                float pt = (float) event.getPartialTick();

                float progress = Mth.lerp(pt, access.getLastCameraFlightProgress(), access.getCameraFlightProgress());
                if (progress > 0f) {
                    float rollFactor = mc.options.getCameraType().isFirstPerson() ? 0.5F : 1.25F;
                    if (player.isUnderWater()) rollFactor *= 0.5F;
                    float baseRoll = Mth.lerp(pt, access.getZRot0(), access.getZRot()) * progress;
                    float smoothedRoll = event.getRoll() + baseRoll * rollFactor * progress;
                    event.setRoll(smoothedRoll);
                }
            }
        }
    }
}
