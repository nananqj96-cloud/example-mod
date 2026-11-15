package bunnythekenzo.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

public record AnchorMacroData(
    ClientPlayerEntity clientPlayerEntity, ClientWorld clientWorld, int count, int count2) {}
