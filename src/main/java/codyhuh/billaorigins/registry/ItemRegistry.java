package codyhuh.billaorigins.registry;

import codyhuh.billaorigins.BillaOrigins;
import codyhuh.billaorigins.content.item.FlashlightBreacherBucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BillaOrigins.MOD_ID);

    public static final RegistryObject<Item> HARPY_EGG = ITEMS.register("harpy_egg", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> HARPY_EGG_CUSTARD_TART = ITEMS.register("harpy_egg_custard_tart", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HARPY_EGGS_BENEDICT = ITEMS.register("harpy_eggs_benedict", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HARPY_OMELETTE = ITEMS.register("harpy_omelette", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HARPY_PAVLOVA = ITEMS.register("harpy_pavlova", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FLASHLIGHT_BREACHER = ITEMS.register("flashlight_breacher", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLASHLIGHT_BREACHER_BUCKET = ITEMS.register("flashlight_breacher_bucket", () -> new FlashlightBreacherBucketItem(new Item.Properties().stacksTo(1)));
}
