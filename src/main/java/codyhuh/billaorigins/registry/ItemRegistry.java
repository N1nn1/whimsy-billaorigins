package codyhuh.billaorigins.registry;

import codyhuh.billaorigins.BillaOrigins;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BillaOrigins.MOD_ID);

    public static final RegistryObject<Item> HARPY_EGG = ITEMS.register("harpy_egg", () -> new EggItem(new Item.Properties().stacksTo(16)));

}
