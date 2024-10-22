package codyhuh.billaorigins.content.power.wattoian;

import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;

public class SinkBoatPower extends PowerFactory<NoConfiguration> {

    public SinkBoatPower() {
        super(NoConfiguration.CODEC);
    }

    @Override
    protected void tick(NoConfiguration configuration, Entity entity) {
        if (entity.isPassenger() && entity.getVehicle() instanceof Boat boat) {
            boat.onAboveBubbleCol(true);
        }

    }

    @Override
    public boolean canTick(ConfiguredPower<NoConfiguration, ?> configuration, Entity entity) {
        return entity.isPassenger() && entity.getVehicle() instanceof Boat;
    }
}
