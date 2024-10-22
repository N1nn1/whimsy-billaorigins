package codyhuh.billaorigins.content.power.wattoian;

import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.minecraft.world.entity.Entity;

public class FreezeResistancePower extends PowerFactory<NoConfiguration> {

    public FreezeResistancePower() {
        super(NoConfiguration.CODEC);
    }

    @Override
    protected void tick(NoConfiguration configuration, Entity entity) {
        if (entity.getTicksFrozen() > 0) {
            entity.setTicksFrozen(0);
        }

    }

    @Override
    public boolean canTick(ConfiguredPower<NoConfiguration, ?> configuration, Entity entity) {
        return entity.isFreezing();
    }
}
