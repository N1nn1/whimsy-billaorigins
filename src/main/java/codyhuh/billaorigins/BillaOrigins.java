package codyhuh.billaorigins;

import codyhuh.billaorigins.content.CommonEvents;
import codyhuh.billaorigins.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BillaOrigins.MOD_ID)
public class BillaOrigins {
    public static final String MOD_ID = "billaorigins";

    public BillaOrigins() {
        MinecraftForge.EVENT_BUS.register(CommonEvents.class);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(bus);
        ParticleTypeRegistry.PARTICLE_TYPES.register(bus);
        SoundRegistry.SOUND_EVENTS.register(bus);
        PowerRegistry.POWERS.register(bus);
        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(bus);
    }
}
