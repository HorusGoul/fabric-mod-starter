package starter.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class SwitchPlayerEntityMixin {

  @Inject(at = @At("RETURN"), method = "damage")
  private void onDamage(DamageSource source, float amount, CallbackInfoReturnable info) {
    System.out.println("The player received damage!");
  }
}