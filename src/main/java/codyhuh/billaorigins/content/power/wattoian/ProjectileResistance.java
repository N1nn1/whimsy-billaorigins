package codyhuh.billaorigins.content.power.wattoian;

import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.animal.camel.Camel;

public class ProjectileResistance extends PowerFactory<NoConfiguration> {

    public ProjectileResistance() {
        super(NoConfiguration.CODEC);
    }

    @Override
    protected void tick(NoConfiguration configuration, Entity entity) {
    }
}
