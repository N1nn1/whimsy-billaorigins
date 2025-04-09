package codyhuh.billaorigins.content.power.owl_harpy;

import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.edwinmindcraft.apoli.common.ApoliCommon;
import io.github.edwinmindcraft.calio.api.CalioAPI;
import io.github.edwinmindcraft.calio.api.registry.PlayerAbilities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleEasings;
import virtuoel.pehkui.api.ScaleTypes;

public class FlightPower extends PowerFactory<NoConfiguration> {
    public FlightPower() {
        super(NoConfiguration.CODEC);
        this.ticking(true);
    }

    public void tick(ConfiguredPower<NoConfiguration, ?> configuration, Entity entity) {
        if (entity instanceof Player player) {
            ScaleData scaleData = ScaleTypes.THIRD_PERSON.getScaleData(entity);
            scaleData.setEasing(ScaleEasings.LINEAR);

            float scale = player.getAbilities().flying ? player.isSprinting() ? 1.75F : 1F : 0.7F;
            boolean b = scaleData.getScale() == 1.75F || scaleData.getScale() == 1F || scaleData.getScale() == 0.7F;

            if (scaleData.getScale() != scale && b) scaleData.setTargetScale(scale);

            if (player.level() instanceof ServerLevel) {

                boolean isActive = configuration.isActive(player);
                boolean hasAbility = this.hasAbility(player);
                if (isActive && !hasAbility && !player.isInWaterRainOrBubble()) this.grantAbility(player);
                else if ((!isActive && hasAbility) || (player.isInWaterRainOrBubble())) this.revokeAbility(player);
            }
        }

    }

    public void onGained(ConfiguredPower<NoConfiguration, ?> configuration, Entity entity) {
        if (!entity.level().isClientSide && configuration.isActive(entity) && !this.hasAbility(entity)) {
            this.grantAbility(entity);
        }

    }

    public void onLost(ConfiguredPower<NoConfiguration, ?> configuration, Entity entity) {
        if (!entity.level().isClientSide && this.hasAbility(entity)) {
            this.revokeAbility(entity);
        }

    }

    public boolean hasAbility(Entity entity) {
        return CalioAPI.getAbilityHolder(entity).map((x) -> x.has( PlayerAbilities.ALLOW_FLYING.get(), ApoliCommon.POWER_SOURCE)).orElse(false);
    }

    public void grantAbility(Entity entity) {
        CalioAPI.getAbilityHolder(entity).ifPresent((x) -> {
            x.grant(PlayerAbilities.ALLOW_FLYING.get(), ApoliCommon.POWER_SOURCE);
        });
    }

    public void revokeAbility(Entity entity) {
        CalioAPI.getAbilityHolder(entity).ifPresent((x) -> {
            x.revoke(PlayerAbilities.ALLOW_FLYING.get(), ApoliCommon.POWER_SOURCE);
        });
    }
}
