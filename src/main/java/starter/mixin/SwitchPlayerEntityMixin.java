package starter.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(PlayerEntity.class)
public class SwitchPlayerEntityMixin {

  @Inject(at = @At("RETURN"), method = "damage")
  private void onDamage(DamageSource source, float amount, CallbackInfoReturnable info) {
    PlayerEntity self = (PlayerEntity) (Object) this;

    // Get all the players in the current minecraft world
    List<PlayerEntity> players = (List<PlayerEntity>) self.world.getPlayers();

    // The player we'll switch positions with.
    PlayerEntity otherPlayer;

    // Stop the execution if the player is playing alone.
    if (players.size() <= 1) {
      return;
    }

    // Get a random player from the players list.
    // Repeat this process until we have a player that is
    // not the player who got hurt.
    do {
      int index = (int) Math.floor(Math.random() * players.size());
      otherPlayer = players.get(index);
    } while (otherPlayer == self);

    // Get the block position of both players
    BlockPos selfPos = self.getBlockPos();
    BlockPos otherPlayerPos = otherPlayer.getBlockPos();

    // Teleport damaged player to the other player's coordinates
    // We set the Y to 300 in order to avoid a collision with the other player.
    //
    // We add 0.5 to both X and Z because that's the center point of a block
    // and the players could suffocate under certain circumstances if we didn't
    self.teleport(otherPlayerPos.getX() + 0.5, 300, otherPlayerPos.getZ() + 0.5);

    // Teleport the other player to the position of the damaged player.
    otherPlayer.teleport(selfPos.getX() + 0.5, selfPos.getY(), selfPos.getZ() + 0.5);

    // Finally change the Y to the real value and complete the teleport of both
    // players.
    self.teleport(otherPlayerPos.getX() + 0.5, otherPlayerPos.getY(), otherPlayerPos.getZ() + 0.5);
  }
}