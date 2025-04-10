package codyhuh.billaorigins.content.power.flashlight_breacher;

import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleEasings;
import virtuoel.pehkui.api.ScaleTypes;

public class SwimmingPower extends PowerFactory<NoConfiguration> {
    public SwimmingPower() {
        super(NoConfiguration.CODEC);
        this.ticking(true);
    }

    public void tick(ConfiguredPower<NoConfiguration, ?> configuration, Entity entity) {
        if (entity instanceof Player player) {
            ScaleData scaleData = ScaleTypes.THIRD_PERSON.getScaleData(entity);
            scaleData.setEasing(ScaleEasings.LINEAR);

            if (player.isUnderWater()) {
                float scale = player.isSwimming() ? 1.75F : 1F;
                boolean b = scaleData.getScale() == 1.75F || scaleData.getScale() == 1F;

                if (scaleData.getScale() != scale && b) scaleData.setTargetScale(scale);
            } else {
                if (scaleData.getScale() != 1) scaleData.setTargetScale(1);
            }
        }

    }
}
