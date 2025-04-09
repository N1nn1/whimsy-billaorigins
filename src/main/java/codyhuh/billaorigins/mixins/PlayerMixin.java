package codyhuh.billaorigins.mixins;

import codyhuh.billaorigins.content.PlayerAccess;
import codyhuh.billaorigins.content.sound.HarpyFlightSound;
import codyhuh.billaorigins.registry.ParticleTypeRegistry;
import codyhuh.billaorigins.registry.PowerRegistry;
import codyhuh.billaorigins.registry.SoundRegistry;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.BeeFlyingSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerAccess {
    @Shadow @Final private Inventory inventory;
    @Shadow @Final private Abilities abilities;
    @Unique public float zRot0;
    @Unique public float zRot;
    @Unique private float prevYRot;
    @Unique private float cameraFlightProgress = 0f;
    @Unique private float lastCameraFlightProgress = 0f;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    public void harpyAiStep(CallbackInfo ci) {
        lastCameraFlightProgress = cameraFlightProgress;

        if (this.abilities.flying && IPowerContainer.hasPower(this, PowerRegistry.FLIGHT_POWER.get())) {

            if (this.level() instanceof ServerLevel serverLevel) {

                double speed = this.getDeltaMovement().length();
                float yawDelta = Mth.degreesDifference(this.getYRot(), prevYRot);

                if (random.nextFloat() < 0.1 && this.tickCount % (this.random.nextInt(10) + (this.isSprinting() ? 10 : 20)) == 0) {
                    Vec3 back = this.position().add(0, 0.25, 0).subtract(this.getLookAngle().scale(0.3));

                    serverLevel.sendParticles(
                            ParticleTypeRegistry.OWL_HARPY_FEATHER.get(),
                            back.x, back.y, back.z,
                            1,
                            0.5, 0.25, 0.5,
                            0.0
                    );
                }

                if (Math.abs(yawDelta) > 8 || speed > 0.6) {
                    Vec3 look = this.getLookAngle().normalize();
                    Vec3 right = look.cross(new Vec3(0, 1, 0)).normalize();
                    Vec3 base = this.position().add(0, 0.25, 0);

                    int amount = 1 + random.nextInt(2);
                    for (int i = 0; i < amount; i++) {
                        double side = random.nextGaussian() * 0.4;
                        Vec3 shoulder = base.add(right.scale(side));

                        double dx = side * 0.5 + random.nextGaussian() * 0.04;
                        double dy = -0.25 + random.nextGaussian() * 0.03;
                        double dz = look.z * -0.5 + random.nextGaussian() * 0.03;

                        serverLevel.sendParticles(
                                ParticleTypeRegistry.OWL_HARPY_FEATHER.get(),
                                shoulder.x, shoulder.y, shoulder.z,
                                1,
                                dx, dy, dz,
                                0
                        );
                    }
                }
            }


            cameraFlightProgress = Mth.clamp(cameraFlightProgress + 0.1f, 0f, 1f);

            zRot0 = zRot;
            float currYaw = this.getYRot();
            float yawDelta = Mth.degreesDifference(currYaw, prevYRot);
            prevYRot = currYaw;

            float turnRoll = 0f;
            if (Math.abs(yawDelta) > 1.5f) {
                turnRoll = -Mth.clamp(yawDelta * 1.2f, -10f, 10f);
            }

            float strafeRoll = -this.xxa * 12f;
            float targetRoll = Mth.clamp(turnRoll + strafeRoll, -25f, 25f);
            zRot = Mth.lerp(0.15f, zRot, targetRoll);

        } else {
            cameraFlightProgress = Mth.clamp(cameraFlightProgress - 0.1f, 0f, 1f);
            zRot = Mth.lerp(0.1f, zRot, 0f);
            zRot0 = Mth.lerp(0.1f, zRot0, 0f);
        }
    }

    @Inject(method = "getDigSpeed", at = @At("HEAD"), cancellable = true)
    public void harpyDigSpeed(BlockState blockState, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (IPowerContainer.hasPower(this, PowerRegistry.FLIGHT_POWER.get())) {
            float f = this.inventory.getDestroySpeed(blockState);
            if (f > 1.0F) {
                int i = EnchantmentHelper.getBlockEfficiency(this);
                ItemStack itemstack = this.getMainHandItem();
                if (i > 0 && !itemstack.isEmpty()) {
                    f += (float)(i * i + 1);
                }
            }

            if (MobEffectUtil.hasDigSpeed(this)) {
                f *= 1.0F + (float)(MobEffectUtil.getDigSpeedAmplification(this) + 1) * 0.2F;
            }

            if (this.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                float f1 = switch (this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) {
                    case 0 -> 0.3F;
                    case 1 -> 0.09F;
                    case 2 -> 0.0027F;
                    default -> 8.1E-4F;
                };

                f *= f1;
            }

            if (this.isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
                f /= 5.0F;
            }
            cir.setReturnValue(f);
        }

    }

    @Override
    public float getZRot() {
        return zRot;
    }
    @Override
    public float getZRot0() {
        return zRot0;
    }
    @Override public float getCameraFlightProgress() {
        return cameraFlightProgress;
    }
    @Override public float getLastCameraFlightProgress() {
        return lastCameraFlightProgress;
    }
}
