package codyhuh.billaorigins.content;

import codyhuh.billaorigins.registry.ItemRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

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


    static boolean isBucketWithPlayer(ItemStack stack) {
        return stack.is(ItemRegistry.FLASHLIGHT_BREACHER_BUCKET.get()) && stack.hasTag() && stack.getTag().hasUUID("BucketedPlayer");
    }

    static boolean isBucketWithPlayer(ItemStack stack, Player player) {
        return stack.is(ItemRegistry.FLASHLIGHT_BREACHER_BUCKET.get()) && stack.hasTag() && stack.getTag().hasUUID("BucketedPlayer") && stack.getTag().getUUID("BucketedPlayer").equals(player.getUUID());
    }

    static void releaseBucketedPlayer(Player bucketed, Player holder, Vec3 position) {
        bucketed.setPos(position);

        holder.playSound(SoundEvents.BUCKET_EMPTY_FISH);

        for (int i = 0; i < holder.getInventory().items.size(); i++) {
            ItemStack item = holder.getInventory().items.get(i);
            if (item.is(ItemRegistry.FLASHLIGHT_BREACHER_BUCKET.get()) && item.hasTag()
                    && item.getTag().getUUID("BucketedPlayer").equals(bucketed.getUUID())) {

                holder.getInventory().setItem(i, new ItemStack(Items.WATER_BUCKET));
                break;
            }
        }

        bucketed.setInvisible(false);
        bucketed.noPhysics = false;
        bucketed.setNoGravity(false);
        bucketed.setInvulnerable(false);
        if (bucketed instanceof PlayerAccess access) access.setBucketOwnerUUID(null);
    }
}
