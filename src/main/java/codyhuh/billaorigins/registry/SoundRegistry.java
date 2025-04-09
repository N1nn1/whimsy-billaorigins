package codyhuh.billaorigins.registry;

import codyhuh.billaorigins.BillaOrigins;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = BillaOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BillaOrigins.MOD_ID);

    public static final RegistryObject<SoundEvent> HARPY_FLY = register("entity.harpy.fly");
    public static final RegistryObject<SoundEvent> HARPY_FLY_LOOP = register("entity.harpy.fly.loop");
    public static final RegistryObject<SoundEvent> HARPY_FLY_TAKEOFF = register("entity.harpy.fly.takeoff");

    private static RegistryObject<SoundEvent> register(String name) {
        ResourceLocation id = new ResourceLocation(BillaOrigins.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
}
