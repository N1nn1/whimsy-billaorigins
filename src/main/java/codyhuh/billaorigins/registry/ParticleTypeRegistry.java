package codyhuh.billaorigins.registry;

import codyhuh.billaorigins.BillaOrigins;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BillaOrigins.MOD_ID);

    public static final RegistryObject<SimpleParticleType> OWL_HARPY_FEATHER = PARTICLE_TYPES.register("owl_harpy_feather", () -> new SimpleParticleType(false));
}
