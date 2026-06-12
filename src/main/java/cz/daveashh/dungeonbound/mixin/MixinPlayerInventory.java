package cz.daveashh.dungeonbound.mixin;

import cz.daveashh.dungeonbound.inventory.DungeonInventoryRules;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class MixinPlayerInventory {
    @Shadow
    @Final
    public Player player;

    @Inject(method = "add(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void dungeonbound$addItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Inventory inventory = (Inventory) (Object) this;

        if (stack.isEmpty()) {
            return;
        }

        if (!DungeonInventoryRules.isDungeonItem(stack)) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(DungeonInventoryRules.tryPickup(inventory, stack, this.player));
    }

    @Inject(method = "setItem(ILnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private void dungeonbound$setItem(int slot, ItemStack stack, CallbackInfo ci) {
        if (!DungeonInventoryRules.canSetItem(slot, stack)) {
            ci.cancel();
        }
    }
}
