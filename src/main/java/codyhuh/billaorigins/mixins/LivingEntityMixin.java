package codyhuh.billaorigins.mixins;

import codyhuh.billaorigins.registry.ItemRegistry;
import codyhuh.billaorigins.registry.PowerRegistry;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, IForgeLivingEntity {

    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "canBreatheUnderwater", at = @At("HEAD"), cancellable = true)
    public void harpyDigSpeed(CallbackInfoReturnable<Boolean> cir) {
        if (IPowerContainer.hasPower(this, PowerRegistry.WATER_BREATHING.get())) {
            cir.setReturnValue(true);
        }

    }

    @Inject(method = "dropCustomDeathLoot", at = @At("HEAD"))
    public void fishDropCustomDeathLoot(DamageSource p_21018_, int p_21019_, boolean p_21020_, CallbackInfo ci) {
        if (IPowerContainer.hasPower(this, PowerRegistry.SWIMMING.get())) {
            this.spawnAtLocation(ItemRegistry.FLASHLIGHT_BREACHER.get());
        }
    }
}
