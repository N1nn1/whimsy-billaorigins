package codyhuh.billaorigins.content.item;

import codyhuh.billaorigins.content.PlayerAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

import static codyhuh.billaorigins.content.PlayerAccess.isBucketWithPlayer;

public class FlashlightBreacherBucketItem extends BucketItem {
    public FlashlightBreacherBucketItem(Properties properties) {
        super(Fluids.WATER, properties);
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
        if (isBucketWithPlayer(stack)) {
            PlayerAccess.releaseBucketedPlayer(level.getPlayerByUUID(stack.getTag().getUUID("BucketedPlayer")), player, pos.getCenter());
        }
    }

    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }
}
