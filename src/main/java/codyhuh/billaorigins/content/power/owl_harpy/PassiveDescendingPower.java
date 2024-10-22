package codyhuh.billaorigins.content.power.owl_harpy;

import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class PassiveDescendingPower extends PowerFactory<NoConfiguration> {

    public PassiveDescendingPower() {
        super(NoConfiguration.CODEC);
    }

    @Override
    protected void tick(NoConfiguration configuration, Entity entity) {
        entity.addDeltaMovement(new Vec3(0.0D, -0.5D, 0.0D));
    }

    @Override
    public boolean canTick(ConfiguredPower<NoConfiguration, ?> configuration, Entity entity) {
        return !entity.onGround();
    }
}
