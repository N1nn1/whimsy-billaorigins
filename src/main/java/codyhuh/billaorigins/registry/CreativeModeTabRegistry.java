package codyhuh.billaorigins.registry;

import codyhuh.billaorigins.BillaOrigins;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = BillaOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BillaOrigins.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BILLAORIGINS = CREATIVE_MODE_TABS.register("billaorigins", () -> CreativeModeTab.builder()
            .icon(ItemRegistry.HARPY_EGG.get()::getDefaultInstance)
            .title(Component.translatable("itemGroup.billaorigins"))
            .displayItems((itemDisplayParameters, output) -> {
                ItemRegistry.ITEMS.getEntries().forEach(itemRegistryObject ->  {
                });
            })
            .build());

}
