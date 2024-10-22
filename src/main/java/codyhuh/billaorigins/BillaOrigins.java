package codyhuh.billaorigins;

import codyhuh.billaorigins.registry.PowerRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BillaOrigins.MOD_ID)
public class BillaOrigins {
    public static final String MOD_ID = "billaorigins";

    public BillaOrigins() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        PowerRegistry.POWERS.register(bus);
    }
}
