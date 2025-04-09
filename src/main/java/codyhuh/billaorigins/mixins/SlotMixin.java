package codyhuh.billaorigins.mixins;

import codyhuh.billaorigins.content.PlayerAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Shadow public abstract ItemStack getItem();

    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    public void B$mayPickup(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (PlayerAccess.isBucketWithPlayer(this.getItem()) && !player.isCreative()) cir.setReturnValue(false);
    }
}
