package cz.daveashh.dungeonbound.inventory;

import cz.daveashh.dungeonbound.item.base.BaseArmorItem;
import cz.daveashh.dungeonbound.item.base.BaseArtifactItem;
import cz.daveashh.dungeonbound.item.base.BaseMeleeItem;
import cz.daveashh.dungeonbound.item.base.BaseRangedItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class DungeonInventoryRules {
    public static final int MELEE_SLOT = 0;
    public static final int RANGED_SLOT = 1;
    public static final int ARMOR_SLOT = 2;
    public static final int ARTIFACT_SLOT_START = 3;
    public static final int ARTIFACT_SLOT_END = 5;
    public static final int LAST_ALLOWED_SLOT = ARTIFACT_SLOT_END;

    private DungeonInventoryRules() {
    }

    public static boolean isManagedSlot(int slot) {
        return slot >= MELEE_SLOT && slot <= LAST_ALLOWED_SLOT;
    }

    public static boolean isDungeonItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return true;
        }

        return stack.getItem() instanceof BaseMeleeItem
                || stack.getItem() instanceof BaseRangedItem
                || stack.getItem() instanceof BaseArmorItem
                || stack.getItem() instanceof BaseArtifactItem;
    }

    public static boolean matchesSlot(ItemStack stack, int slot) {
        if (stack.isEmpty()) {
            return true;
        }

        return switch (slot) {
            case MELEE_SLOT -> stack.getItem() instanceof BaseMeleeItem;
            case RANGED_SLOT -> stack.getItem() instanceof BaseRangedItem;
            case ARMOR_SLOT -> stack.getItem() instanceof BaseArmorItem;
            case ARTIFACT_SLOT_START, ARTIFACT_SLOT_START + 1, ARTIFACT_SLOT_END ->
                    stack.getItem() instanceof BaseArtifactItem;
            default -> false;
        };
    }

    public static boolean canSetItem(int slot, ItemStack stack) {
        if (!isManagedSlot(slot)) {
            return stack.isEmpty();
        }

        return matchesSlot(stack, slot);
    }

    public static int getPickupSlot(Inventory inventory, ItemStack stack) {
        if (stack.isEmpty() || !isDungeonItem(stack)) {
            return -1;
        }

        if (stack.getItem() instanceof BaseMeleeItem) {
            return tryPickupSlot(inventory, MELEE_SLOT, stack);
        }

        if (stack.getItem() instanceof BaseRangedItem) {
            return tryPickupSlot(inventory, RANGED_SLOT, stack);
        }

        if (stack.getItem() instanceof BaseArmorItem) {
            return tryPickupSlot(inventory, ARMOR_SLOT, stack);
        }

        if (stack.getItem() instanceof BaseArtifactItem) {
            for (int slot = ARTIFACT_SLOT_START; slot <= ARTIFACT_SLOT_END; slot++) {
                int pickupSlot = tryPickupSlot(inventory, slot, stack);
                if (pickupSlot >= 0) {
                    return pickupSlot;
                }
            }
        }

        return -1;
    }

    public static boolean tryPickup(Inventory inventory, ItemStack stack, Player player) {
        int slot = getPickupSlot(inventory, stack);
        if (slot < 0) {
            return false;
        }

        ItemStack current = inventory.getItem(slot);
        if (current.isEmpty()) {
            inventory.setItem(slot, stack.copy());
            stack.setCount(0);
            player.containerMenu.broadcastChanges();
            return true;
        }

        if (ItemStack.isSameItemSameComponents(current, stack)) {
            int transferable = Math.min(stack.getCount(), current.getMaxStackSize() - current.getCount());
            if (transferable <= 0) {
                return false;
            }

            current.grow(transferable);
            stack.shrink(transferable);
            player.containerMenu.broadcastChanges();
            return stack.isEmpty();
        }

        return false;
    }

    private static int tryPickupSlot(Inventory inventory, int slot, ItemStack stack) {
        if (!matchesSlot(stack, slot)) {
            return -1;
        }

        ItemStack current = inventory.getItem(slot);
        if (current.isEmpty()) {
            return slot;
        }

        if (ItemStack.isSameItemSameComponents(current, stack) && current.getCount() < current.getMaxStackSize()) {
            return slot;
        }

        return -1;
    }
}
