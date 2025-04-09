package codyhuh.billaorigins.mixins.client;

import codyhuh.billaorigins.content.sound.HarpyFlightSound;
import codyhuh.billaorigins.registry.PowerRegistry;
import com.mojang.authlib.GameProfile;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    @Shadow @Final protected Minecraft minecraft;
    @Unique private boolean harpyFlightSoundPlaying = false;

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    public void harpyAiStep(CallbackInfo ci) {
        LocalPlayer that = (LocalPlayer) (Object) this;

        if (this.getAbilities().flying && this.level().isClientSide && IPowerContainer.hasPower(this, PowerRegistry.FLIGHT_POWER.get())) {
            if (!harpyFlightSoundPlaying) {
                minecraft.getSoundManager().play(new HarpyFlightSound(that));
                harpyFlightSoundPlaying = true;
            }
        } else {
            harpyFlightSoundPlaying = false;
        }
    }
}
