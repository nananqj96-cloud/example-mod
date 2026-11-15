package bunnythekenzo.client;

import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public record AutoCrystalData(
    HitResult hitResult, EndCrystalEntity endCrystalEntity, BlockPos blockPos, boolean enabled) {}
