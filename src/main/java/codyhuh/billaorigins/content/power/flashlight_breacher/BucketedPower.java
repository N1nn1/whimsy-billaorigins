package codyhuh.billaorigins.content.power.flashlight_breacher;

import codyhuh.billaorigins.content.PlayerAccess;
import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleEasings;
import virtuoel.pehkui.api.ScaleTypes;

public class BucketedPower extends PowerFactory<NoConfiguration> {
    public BucketedPower() {
        super(NoConfiguration.CODEC);
        this.ticking(true);
    }

    public void tick(ConfiguredPower<NoConfiguration, ?> configuration, Entity entity) {
        if (entity instanceof PlayerAccess playerAccess) {
            ScaleData scaleData = ScaleTypes.HITBOX_HEIGHT.getScaleData(entity);
            scaleData.setEasing(ScaleEasings.LINEAR);
            if (scaleData.getScale() != 0.01F && playerAccess.getBucketOwnerUUID() != null) scaleData.setTargetScale(0.01F);
        }
    }
}
