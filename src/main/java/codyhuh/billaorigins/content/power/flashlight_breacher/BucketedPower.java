package codyhuh.billaorigins.content.power.flashlight_breacher;

import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;

public class BucketedPower extends PowerFactory<NoConfiguration> {
    public BucketedPower() {
        super(NoConfiguration.CODEC);
        this.ticking(true);
    }
}
