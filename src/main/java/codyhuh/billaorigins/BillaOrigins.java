package codyhuh.billaorigins;

import codyhuh.billaorigins.registry.CreativeModeTabRegistry;
import codyhuh.billaorigins.registry.ItemRegistry;
import codyhuh.billaorigins.registry.PowerRegistry;
import codyhuh.billaorigins.registry.SoundRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BillaOrigins.MOD_ID)
public class BillaOrigins {
    public static final String MOD_ID = "billaorigins";

    public BillaOrigins() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(bus);
        SoundRegistry.SOUND_EVENTS.register(bus);
        PowerRegistry.POWERS.register(bus);
        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(bus);
    }
}
