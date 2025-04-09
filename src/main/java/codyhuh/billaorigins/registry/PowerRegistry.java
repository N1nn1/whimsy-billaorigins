package codyhuh.billaorigins.registry;

import codyhuh.billaorigins.BillaOrigins;
import codyhuh.billaorigins.content.power.flashlight_breacher.BucketedPower;
import codyhuh.billaorigins.content.power.flashlight_breacher.SwimmingPower;
import codyhuh.billaorigins.content.power.owl_harpy.FlightPower;
import codyhuh.billaorigins.content.power.wattoian.FreezeResistancePower;
import codyhuh.billaorigins.content.power.wattoian.MountDeathPower;
import codyhuh.billaorigins.content.power.wattoian.SinkBoatPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.edwinmindcraft.apoli.common.power.DummyPower;
import io.github.edwinmindcraft.origins.common.registry.OriginRegisters;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PowerRegistry {
    public static final DeferredRegister<PowerFactory<?>> POWERS = DeferredRegister.create(OriginRegisters.POWER_FACTORIES.getRegistryName(), BillaOrigins.MOD_ID);

    public static final RegistryObject<FlightPower> FLIGHT_POWER = POWERS.register("flight", FlightPower::new);
    public static final RegistryObject<SwimmingPower> SWIMMING = POWERS.register("swimming", SwimmingPower::new);
    public static final RegistryObject<SinkBoatPower> SINK_BOAT_POWER = POWERS.register("sink_boat", SinkBoatPower::new);
    public static final RegistryObject<MountDeathPower> KILL_MOUNT_POWER = POWERS.register("kill_mount", MountDeathPower::new);
    public static final RegistryObject<FreezeResistancePower> FREEZE_RESISTANCE = POWERS.register("freeze_resistance", FreezeResistancePower::new);
    public static final RegistryObject<DummyPower> WATER_BREATHING = POWERS.register("water_breathing", DummyPower::new);
    public static final RegistryObject<BucketedPower> BUCKETABLE = POWERS.register("bucketable", BucketedPower::new);
}
