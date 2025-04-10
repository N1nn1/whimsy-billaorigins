package codyhuh.billaorigins.content;

import codyhuh.billaorigins.registry.ItemRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.UUID;

public interface PlayerAccess {
    float getZRot();
    float getZRot0();
    float getCameraFlightProgress();
    float getLastCameraFlightProgress();

    @Nullable
    UUID getBucketOwnerUUID();
    void setBucketOwnerUUID(@Nullable UUID uuid);
    Player getBucketOwner();

    static void releaseBucketedPlayer(Player bucketed, Player holder, Vec3 position) {
        if (bucketed != null) {
            bucketed.setInvisible(false);
            bucketed.noPhysics = false;
            bucketed.setNoGravity(false);
            bucketed.setPose(Pose.STANDING);
            if (bucketed instanceof PlayerAccess access) access.setBucketOwnerUUID(null);
            bucketed.setPos(position);

            ScaleData scaleData = ScaleTypes.HITBOX_HEIGHT.getScaleData(bucketed);
            scaleData.setTargetScale(1.3F);
            ScaleData scaleData2 = ScaleTypes.HITBOX_WIDTH.getScaleData(bucketed);
            scaleData2.setTargetScale(1F);
            ScaleData scaleData3 = ScaleTypes.EYE_HEIGHT.getScaleData(bucketed);
            scaleData3.setTargetScale(1.3F);

            holder.playSound(SoundEvents.BUCKET_EMPTY_FISH);

            for (int i = 0; i < holder.getInventory().items.size(); i++) {
                ItemStack item = holder.getInventory().items.get(i);
                if (item.is(ItemRegistry.FLASHLIGHT_BREACHER_BUCKET.get()) && item.hasTag() && item.getTag().getUUID("BucketedPlayer").equals(bucketed.getUUID())) {
                    holder.getInventory().setItem(i, new ItemStack(Items.WATER_BUCKET));
                    break;
                }
            }
        }
    }
}