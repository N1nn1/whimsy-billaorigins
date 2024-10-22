package codyhuh.billaorigins.content.power.wattoian;

import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.animal.camel.Camel;

public class MountDeathPower extends PowerFactory<NoConfiguration> {

    public MountDeathPower() {
        super(NoConfiguration.CODEC);
    }

    @Override
    protected void tick(NoConfiguration configuration, Entity entity) {
        if (entity.isPassenger() && entity.getVehicle() instanceof Saddleable) {
            if (!(entity.getVehicle() instanceof Camel)) {
                entity.getVehicle().kill();
            }
        }

    }

    @Override
    public boolean canTick(ConfiguredPower<NoConfiguration, ?> configuration, Entity entity) {
        return entity.isPassenger() && entity.getVehicle() instanceof Saddleable;
    }
}
