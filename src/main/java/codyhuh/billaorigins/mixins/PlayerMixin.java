package codyhuh.billaorigins.mixins;

import codyhuh.billaorigins.content.PlayerAccess;
import codyhuh.billaorigins.registry.ItemRegistry;
import codyhuh.billaorigins.registry.ParticleTypeRegistry;
import codyhuh.billaorigins.registry.PowerRegistry;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerAccess {
    @Shadow @Final private Inventory inventory;
    @Shadow @Final private Abilities abilities;
    @Shadow public abstract boolean isSwimming();
    @Shadow public abstract void displayClientMessage(Component p_36216_, boolean p_36217_);
    @Shadow public abstract Component getDisplayName();
    @Unique public float zRot0;
    @Unique public float zRot;
    @Unique private float prevYRot;
    @Unique private float cameraFlightProgress = 0f;
    @Unique private float lastCameraFlightProgress = 0f;
    @Unique private static final EntityDataAccessor<Optional<UUID>> DATA_BUCKET_OWNERUUID_ID = SynchedEntityData.defineId(PlayerMixin.class, EntityDataSerializers.OPTIONAL_UUID);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (IPowerContainer.hasPower(this, PowerRegistry.BUCKETABLE.get()) && getBucketOwner() == null && player.getMainHandItem().is(Items.WATER_BUCKET)) {

            this.setBucketOwnerUUID(player.getUUID());

            ItemStack bucket = ItemRegistry.FLASHLIGHT_BREACHER_BUCKET.get().getDefaultInstance();
            bucket.getOrCreateTag().putUUID("BucketedPlayer", this.getUUID());
            bucket.setHoverName(this.getDisplayName());
            this.noPhysics = true;
            this.setNoGravity(true);
            this.setInvulnerable(true);

            ScaleData scaleData = ScaleTypes.HITBOX_HEIGHT.getScaleData(this);
            scaleData.setTargetScale(0.01F);
            ScaleData scaleData2 = ScaleTypes.HITBOX_WIDTH.getScaleData(this);
            scaleData2.setTargetScale(0.01F);
            ScaleData scaleData3 = ScaleTypes.EYE_HEIGHT.getScaleData(this);
            scaleData3.setTargetScale(0.01F);

            player.setItemInHand(hand, bucket);

            player.playSound(SoundEvents.BUCKET_FILL_FISH);

            this.displayClientMessage(Component.literal("Press Shift To exit out of the Bucket"), true);
            return InteractionResult.SUCCESS;
        }

        return super.interact(player, hand);
    }

    @Override
    public boolean isFree(double p_20230_, double p_20231_, double p_20232_) {
        if (this.getBucketOwner() != null) return true;
        return super.isFree(p_20230_, p_20231_, p_20232_);
    }

    @Override
    public boolean isPushable() {
        return this.getBucketOwner() == null && super.isPushable();
    }

    @Override
    protected void pushEntities() {
        if (this.getBucketOwner() == null) {
            super.pushEntities();
        }
    }

    @Override
    public boolean startRiding(Entity p_20330_) {
        if (this.getBucketOwner() != null) return false;
        return super.startRiding(p_20330_);
    }

    @Override
    public boolean canBeSeenByAnyone() {
        if (this.getBucketOwner() != null) return false;
        return super.canBeSeenByAnyone();
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    public void BO$aiStep(CallbackInfo ci) {
        Player owner = this.getBucketOwner();

        if (owner != null) {
            EntityDimensions dimensions = owner.getDimensions(owner.getPose());
            Vec3 pos = owner.position().add(0, dimensions.height/2,0).add(owner.getLookAngle().multiply(dimensions.width/4,0,dimensions.width/4));
            Vec3 pos2 = owner.position().add(owner.getLookAngle().multiply(2,0,2)).add(0, 1.8,0);

            this.setPos(pos);
            this.setDeltaMovement(Vec3.ZERO);
            this.setAirSupply(this.getMaxAirSupply());

            this.noPhysics = true;
            this.setNoGravity(true);
            this.setInvulnerable(true);

            if (!anyHandHoldsNucket(owner) || this.isShiftKeyDown()) {
                PlayerAccess.releaseBucketedPlayer((Player)(Object)this, owner, pos2);
            }
        }
    }

    @Unique
    private boolean anyHandHoldsNucket(Player p) {
        return (p.getMainHandItem().is(ItemRegistry.FLASHLIGHT_BREACHER_BUCKET.get()) && p.getMainHandItem().hasTag() && p.getMainHandItem().getTag().hasUUID("BucketedPlayer") && p.getMainHandItem().getTag().getUUID("BucketedPlayer").equals(this.getUUID())) || (p.getOffhandItem().is(ItemRegistry.FLASHLIGHT_BREACHER_BUCKET.get()) && p.getOffhandItem().hasTag() && p.getOffhandItem().getTag().hasUUID("BucketedPlayer") && p.getOffhandItem().getTag().getUUID("BucketedPlayer").equals(this.getUUID()));
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    public void B$aiStep(CallbackInfo ci) {
        lastCameraFlightProgress = cameraFlightProgress;

        if ((this.abilities.flying && IPowerContainer.hasPower(this, PowerRegistry.FLIGHT_POWER.get()))) {

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
        }

        if ((this.abilities.flying && IPowerContainer.hasPower(this, PowerRegistry.FLIGHT_POWER.get()))
                || (this.isSwimming() && IPowerContainer.hasPower(this, PowerRegistry.SWIMMING.get()))) {

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

    @Inject(at = @At("RETURN"), method = "getDimensions", cancellable = true)
    private void S$getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if ((this.isUnderWater() || (!this.isUnderWater() && this.isCrouching())) && IPowerContainer.hasPower(this, PowerRegistry.SWIMMING.get())) {
            cir.setReturnValue(EntityDimensions.scalable(0.8F, 0.8F));
        }
    }

    @Inject(at = @At("RETURN"), method = "getStandingEyeHeight", cancellable = true)
    private void S$getStandingEyeHeight(Pose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
        if ((this.isUnderWater() || (!this.isUnderWater() && this.isCrouching())) && IPowerContainer.hasPower(this, PowerRegistry.SWIMMING.get())) {
            cir.setReturnValue(0.35F);
        }
    }

    @Inject(at = @At("TAIL"), method = "defineSynchedData")
    private void S$defineSynchedData(CallbackInfo ci) {
        this.entityData.define(DATA_BUCKET_OWNERUUID_ID, Optional.empty());
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
    private void S$addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        if (this.getBucketOwnerUUID() != null) {
            compoundTag.putUUID("BucketOwner", this.getBucketOwnerUUID());
        }
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData")
    private void S$readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        if (compoundTag.hasUUID("BucketOwner")) {
            this.setBucketOwnerUUID(compoundTag.getUUID("BucketOwner"));
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
    @Override
    @Nullable
    public UUID getBucketOwnerUUID() {
        return this.entityData.get(DATA_BUCKET_OWNERUUID_ID).orElse(null);
    }
    @Override
    public void setBucketOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_BUCKET_OWNERUUID_ID, Optional.ofNullable(uuid));
    }
    @Override
    public Player getBucketOwner() {
        UUID $$0 = this.getBucketOwnerUUID();
        return $$0 == null ? null : this.level().getPlayerByUUID($$0);
    }
}

