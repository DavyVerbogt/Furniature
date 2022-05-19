package com.colt.furniature.registries;

import com.colt.furniature.Furniature;
import com.colt.furniature.entity.Sitting;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FurniatureEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITIES, Furniature.MOD_ID);

    public static final RegistryObject<EntityType<Sitting>> SEAT = register("seat", EntityType.Builder.<Sitting>of((type, world) -> new Sitting(world), MobCategory.MISC).sized(0.0F, 0.0F).setCustomClientFactory((spawnEntity, world) -> new Sitting(world)));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY.register(name, () -> builder.build(name));
    }
}
