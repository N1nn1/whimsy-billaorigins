package codyhuh.billaorigins.content.sound;

import codyhuh.billaorigins.registry.SoundRegistry;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HarpyFlightSound extends AbstractTickableSoundInstance {
    private final LocalPlayer player;
    private int time;
    private boolean playedTakeOff;
    private boolean playedFlap;

    public HarpyFlightSound(LocalPlayer player) {
        super(SoundRegistry.HARPY_FLY_LOOP.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.1F;
        this.playedTakeOff = false;
        this.playedFlap = false;
    }

    public void tick() {
        ++this.time;

        if (time == 1 && !playedTakeOff) {
            player.playSound(SoundRegistry.HARPY_FLY_TAKEOFF.get());
            playedTakeOff = true;
        }

        if (!this.player.isRemoved() && (this.time <= 20 || this.player.getAbilities().flying)) {
            this.x = (float)this.player.getX();
            this.y = (float)this.player.getY();
            this.z = (float)this.player.getZ();
            float f = (float)this.player.getDeltaMovement().lengthSqr();
            if ((double)f >= 1.0E-7D) {
                this.volume = Mth.clamp(f / 4.0F, 0.0F, 1.0F);
            } else {
                this.volume = 0.0F;
            }


            if (this.time <= 20) {
                this.volume = 0.0F;
            } else if (this.time < 40) {
                this.volume *= (float)(this.time - 20) / 20.0F;
            }

            if (this.volume > 0.8F) {
                this.pitch = 1.0F + (this.volume - 0.8F);
            } else {
                this.pitch = 1.0F;
            }

            if (this.time % (this.random.nextInt(10) + (player.isSprinting() ? 10 : 20)) == 0) {
                if (playedFlap) playedFlap = false;
                else {
                    player.playSound(SoundRegistry.HARPY_FLY.get());
                    playedFlap = true;
                }
            }

        } else {
            this.stopSound();
        }
    }

    public final void stopSound() {
        this.stop();
    }
}

