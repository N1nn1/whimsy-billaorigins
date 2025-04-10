package codyhuh.billaorigins.mixins.client;

import codyhuh.billaorigins.content.PlayerAccess;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRendererDispatcherMixin implements ResourceManagerReloadListener {

    @Inject(at = @At("HEAD"), method = "renderShadow", cancellable = true)
    private static void S$renderShadow(PoseStack p_114458_, MultiBufferSource p_114459_, Entity p_114460_, float p_114461_, float p_114462_, LevelReader p_114463_, float p_114464_, CallbackInfo ci) {
        if (p_114460_ instanceof PlayerAccess access && access.getBucketOwner() != null) ci.cancel();
    }

}
