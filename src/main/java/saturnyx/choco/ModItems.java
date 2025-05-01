package saturnyx.choco;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;


public class ModItems {
    // REGISTER FUNCTION
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Choco.MOD_ID, name));
        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    // Chocolate ------------------------------------------------------------------------------------------------------+
    public static final ConsumableComponent CHOCOLATE_CONSUMABLE_COMPONENT = ConsumableComponents.food().build();
    public static final FoodComponent CHOCOLATE_COMPONENT =
            new FoodComponent.Builder().alwaysEdible().nutrition(1).saturationModifier(0.6F).build();
    public static final Item CHOCOLATE = register("chocolate", Item::new,
            new Item.Settings().food(CHOCOLATE_COMPONENT, CHOCOLATE_CONSUMABLE_COMPONENT));

    // MILK CHOCOLATE -------------------------------------------------------------------------------------------------+
    public static final ConsumableComponent MILK_CHOCOLATE_CONSUMABLE_COMPONENT = ConsumableComponents.food().build();
    public static final FoodComponent MILK_CHOCOLATE_COMPONENT =
            new FoodComponent.Builder().alwaysEdible().nutrition(1).saturationModifier(0.5F).build();
    public static final Item MILK_CHOCOLATE = register("milk_chocolate", Item::new,
            new Item.Settings().food(MILK_CHOCOLATE_COMPONENT, MILK_CHOCOLATE_CONSUMABLE_COMPONENT));

    // WHITE CHOCOLATE ------------------------------------------------------------------------------------------------+
    public static final ConsumableComponent WHITE_CHOCOLATE_CONSUMABLE_COMPONENT = ConsumableComponents.food().build();
    public static final FoodComponent WHITE_CHOCOLATE_COMPONENT =
            new FoodComponent.Builder().alwaysEdible().nutrition(1).saturationModifier(0.4F).build();
    public static final Item WHITE_CHOCOLATE = register("white_chocolate", Item::new,
            new Item.Settings().food(WHITE_CHOCOLATE_COMPONENT, WHITE_CHOCOLATE_CONSUMABLE_COMPONENT));

    // GOLDEN CHOCOLATE -----------------------------------------------------------------------------------------------+
    public static final ConsumableComponent GOLDEN_CHOCOLATE_CONSUMABLE_COMPONENT = ConsumableComponents.food()
            .consumeEffect(new net.minecraft.item.consume.ApplyEffectsConsumeEffect(
                    new net.minecraft.entity.effect.StatusEffectInstance(
                            net.minecraft.entity.effect.StatusEffects.ABSORPTION, 60 * 20, 1), 1.0f)).consumeEffect(
                    new net.minecraft.item.consume.ApplyEffectsConsumeEffect(
                            new net.minecraft.entity.effect.StatusEffectInstance(
                                    net.minecraft.entity.effect.StatusEffects.REGENERATION, 5 * 20, 0), 1.0f)).build();
    public static final FoodComponent GOLDEN_CHOCOLATE_COMPONENT =
            new FoodComponent.Builder().alwaysEdible().nutrition(1).saturationModifier(0.3F).build();
    public static final Item GOLDEN_CHOCOLATE = register("golden_chocolate", Item::new,
            new Item.Settings().food(GOLDEN_CHOCOLATE_COMPONENT, GOLDEN_CHOCOLATE_CONSUMABLE_COMPONENT));

    // RUBY CHOCOLATE -------------------------------------------------------------------------------------------------+
    public static final ConsumableComponent RUBY_CHOCOLATE_CONSUMABLE_COMPONENT = ConsumableComponents.food()
            .consumeEffect(new net.minecraft.item.consume.ApplyEffectsConsumeEffect(
                    new net.minecraft.entity.effect.StatusEffectInstance(
                            net.minecraft.entity.effect.StatusEffects.ABSORPTION, 30 * 20, 1), 1.0f)).consumeEffect(
                    new net.minecraft.item.consume.ApplyEffectsConsumeEffect(
                            new net.minecraft.entity.effect.StatusEffectInstance(
                                    net.minecraft.entity.effect.StatusEffects.REGENERATION, 4 * 20, 0), 1.0f)).build();
    public static final FoodComponent RUBY_CHOCOLATE_COMPONENT =
            new FoodComponent.Builder().alwaysEdible().nutrition(1).saturationModifier(0.3F).build();
    public static final Item RUBY_CHOCOLATE = register("ruby_chocolate", Item::new,
            new Item.Settings().food(RUBY_CHOCOLATE_COMPONENT, RUBY_CHOCOLATE_CONSUMABLE_COMPONENT));

    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our chocolate to the ingredients group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.MILK_CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.WHITE_CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.GOLDEN_CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.RUBY_CHOCOLATE));

        // Add the chocolate to the composting registry with a 30% chance of increasing the composter's level.
        CompostingChanceRegistry.INSTANCE.add(ModItems.CHOCOLATE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.MILK_CHOCOLATE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.WHITE_CHOCOLATE, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.RUBY_CHOCOLATE, 0.2f);
        // Add chocolate to the registry of fuels, with a burn time of 5 seconds.
        // 20 ticks = 1 second (Minecraft deals with logical based-time using ticks)
        FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(ModItems.CHOCOLATE, 5 * 20));
        FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(ModItems.MILK_CHOCOLATE, 5 * 20));
        FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(ModItems.WHITE_CHOCOLATE, 6 * 20));
        FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(ModItems.RUBY_CHOCOLATE, 10 * 20));
    }
}
