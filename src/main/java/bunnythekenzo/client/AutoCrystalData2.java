package bunnythekenzo.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

public record AutoCrystalData2(
    ClientPlayerEntity _d, ClientWorld clientWorld, int count, int count2) {}
