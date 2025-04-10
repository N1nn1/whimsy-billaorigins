package codyhuh.billaorigins.content;

import codyhuh.billaorigins.BillaOrigins;
import codyhuh.billaorigins.content.item.FlashlightBreacherBucketItem;
import codyhuh.billaorigins.registry.ItemRegistry;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BillaOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @Mod.EventBusSubscriber(modid = BillaOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onItemToss(ItemTossEvent event) {
            if (event.getEntity().getItem().getItem() instanceof FlashlightBreacherBucketItem) {
                event.getEntity().setItem(Items.WATER_BUCKET.getDefaultInstance());
            }
        }
        @SubscribeEvent
        public static void OnDeath(LivingDropsEvent event) {
            for (ItemEntity itemEntity : event.getDrops()) {
                if (itemEntity.getItem().getItem() instanceof FlashlightBreacherBucketItem) {
                    itemEntity.setItem(Items.WATER_BUCKET.getDefaultInstance());
                }
            }
        }
        @SubscribeEvent
        public static void playerLogOff(PlayerEvent.PlayerLoggedOutEvent event) {
            if (event.getEntity() instanceof PlayerAccess access && access.getBucketOwner() != null) {
                Vec3 pos = access.getBucketOwner().position().add(access.getBucketOwner().getLookAngle().multiply(2,0,2)).add(0, 1.8,0);
                PlayerAccess.releaseBucketedPlayer(event.getEntity(), access.getBucketOwner(), pos);
            } else {
                for (ItemStack stack : event.getEntity().getInventory().items) {
                    if (stack.is(ItemRegistry.FLASHLIGHT_BREACHER_BUCKET.get()) && stack.hasTag() && stack.getTag().hasUUID("BucketedPlayer")) {
                        Player bucketed = event.getEntity().level().getPlayerByUUID(stack.getTag().getUUID("BucketedPlayer"));
                        Vec3 pos = bucketed.position().add(bucketed.getLookAngle().multiply(2,0,2)).add(0, 1.8,0);
                        PlayerAccess.releaseBucketedPlayer(bucketed, event.getEntity(), pos);
                    }
                }
            }
        }

    }
}