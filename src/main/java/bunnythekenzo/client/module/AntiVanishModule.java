package bunnythekenzo.client.module;

import com.mojang.brigadier.suggestion.Suggestion;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Action;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Entry;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.AntiVanishData;
import bunnythekenzo.client.AntiVanishData2;
import bunnythekenzo.client.AntiVanishData3;
import bunnythekenzo.client.AntiVanishData4;
import bunnythekenzo.client.AntiVanishData5;
import bunnythekenzo.client.AntiVanishData6;
import bunnythekenzo.client.AntiVanishData7;
import bunnythekenzo.client.AntiVanishData8;
import bunnythekenzo.client.AntiVanishState;
import bunnythekenzo.client.AntiVanishState2;
import bunnythekenzo.client.AntiVanishSupport;
import bunnythekenzo.client.AntiVanishSupport2;
import bunnythekenzo.client.ChatMessageBuilder;
import bunnythekenzo.client.ClientChat;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.NullSafeText;
import bunnythekenzo.client.SearchTextNormalizer;
import bunnythekenzo.client.StaffWatchNotification;
import bunnythekenzo.client.WorldChangeTracker;
import bunnythekenzo.client.WorldSignalClassifier;

public final class AntiVanishModule extends Module {
  public static final MinecraftClient minecraftClient = MinecraftClient.getInstance();

  public final ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
  public final Map map = new HashMap();
  public final Map map2 = new LinkedHashMap();
  public final Map map3 = new HashMap();
  public final Map map4 = new HashMap();
  public final Deque<Long> deque = new ArrayDeque();
  public final Map map5 = new ConcurrentHashMap();
  public final Map<UUID, Long> map6 = new HashMap();
  public final Map<Long, Long> map7 = new HashMap();
  public final Map map8 = new HashMap();
  public final Deque deque2 = new ArrayDeque();
  public final Deque deque3 = new ArrayDeque();
  public final Deque deque4 = new ArrayDeque();
  public final Map map9 = new LinkedHashMap();
  public final Deque deque5 = new ArrayDeque();
  public boolean enabled;
  public final Set set = ConcurrentHashMap.newKeySet();
  public volatile List list;
  public int count5 = 30000;

  public final Map map10 = new HashMap();
  public final WorldChangeTracker worldChangeTracker = new WorldChangeTracker();
  public int count6;
  public volatile long timestamp10;
  public long timestamp11;
  public long timestamp12;
  public long timestamp13;
  public String string2 = "";
  public final Set set2 = new HashSet();
  public UUID uUID;
  public String string3;
  public static ClientInitializer[] clientInitializerArray;

  public AntiVanishModule() {
    super("AntiVanish", "Detects vanished players.", ModuleCategory.MISC);
  }

  @Override
  public void onEnable() {
    FakeLootDropModule.ensureInitialized();

    this.update15();
    if (minecraftClient.player != null && minecraftClient.world != null) {
      this.update5();
    }
  }

  @Override
  public void onDisable() {
    this.update15();
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.player != null
        && minecraftClient.world != null
        && minecraftClient.getNetworkHandler() != null) {
      if (this.worldChangeTracker.test(minecraftClient)) {
        this.update15();
      }

      this.update14();
      this.count6++;
      if (minecraftClient.player.currentScreenHandler
          != minecraftClient.player.playerScreenHandler) {
        this.timestamp11 = System.currentTimeMillis();
      }

      this.update4();
      this.update8();
      this.update10();
      if (this.count6 % 100 == 0) {
        this.update9();
      }

      if (this.count6 % 10 == 0) {
        this.update5();
        this.update11();
      }

      if (this.count6 % 5 == 0) {
        this.update12();
      }

      this.pruneState();
    } else {
      this.update15();
    }
  }

  public void apply(Packet packet) {
    this.apply2(packet);
  }

  public void apply2(Packet packet) {
    FakeLootDropModule.ensureInitialized();
    if (packet != null) {
      if (packet instanceof PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket) {
        this.timestamp10 = System.currentTimeMillis();
        if (playerInteractBlockC2SPacket.getBlockHitResult() != null
            && playerInteractBlockC2SPacket.getBlockHitResult().getBlockPos() != null) {
          long longValue = System.currentTimeMillis() + 6000L;
          BlockPos blockPos = playerInteractBlockC2SPacket.getBlockHitResult().getBlockPos();
          BlockPos blockPos2 =
              blockPos.offset(playerInteractBlockC2SPacket.getBlockHitResult().getSide());
          this.map5.put(blockPos.asLong(), longValue);
          this.map5.put(blockPos2.asLong(), longValue);
          String text = "";
          if (minecraftClient.player != null && playerInteractBlockC2SPacket.getHand() != null) {
            Identifier id =
                Registries.ITEM.getId(
                    minecraftClient
                        .player
                        .getStackInHand(playerInteractBlockC2SPacket.getHand())
                        .getItem());
            text = WorldSignalClassifier.normalizeIdentifier(id == null ? "" : id.toString());
          }

          apply(blockPos, text, this.map5, longValue);
          apply(blockPos2, text, this.map5, longValue);
        }
      } else {
        if (packet instanceof PlayerActionC2SPacket
            || packet instanceof PlayerInteractItemC2SPacket
            || packet instanceof PlayerInteractEntityC2SPacket
            || packet instanceof HandSwingC2SPacket) {
          this.timestamp10 = System.currentTimeMillis();
        }
      }
    }
  }

  public static void apply(BlockPos blockPos, String text, Map map12, long longValue) {
    FakeLootDropModule.ensureInitialized();
    if (blockPos != null && map12 != null) {
      map12.put(blockPos.asLong(), longValue);
      String text2 = text == null ? "" : text;
      boolean flag =
          text2.endsWith("_door") && !text2.endsWith("trapdoor")
              || text2.contains("sunflower")
              || text2.contains("lilac")
              || text2.contains("rose_bush")
              || text2.contains("peony")
              || text2.contains("tall_grass")
              || text2.contains("large_fern")
              || text2.contains("pitcher_plant");
      if (flag) {
        map12.put(blockPos.up().asLong(), longValue);
        map12.put(blockPos.down().asLong(), longValue);
      }

      if (text2.endsWith("_bed")) {
        map12.put(blockPos.add(1, 0, 0).asLong(), longValue);
        map12.put(blockPos.add(-1, 0, 0).asLong(), longValue);
        map12.put(blockPos.add(0, 0, 1).asLong(), longValue);
        map12.put(blockPos.add(0, 0, -1).asLong(), longValue);
      }
    }
  }

  public boolean test(Packet packet) {
    return this.test2(packet);
  }

  public boolean test2(Packet packet) {
    FakeLootDropModule.ensureInitialized();
    if (packet instanceof PlayerRemoveS2CPacket playerRemoveS2CPacket) {
      if (playerRemoveS2CPacket.profileIds().size() < 4) {
        for (UUID uuid : playerRemoveS2CPacket.profileIds()) {
          this.concurrentLinkedQueue.offer(AntiVanishData7.getAntiVanishData7(uuid));
        }
      }
    } else if (packet instanceof PlayerListS2CPacket playerListS2CPacket
        && playerListS2CPacket.getActions().contains(Action.UPDATE_LISTED)) {
      int index = 0;

      for (Entry entry : playerListS2CPacket.getEntries()) {
        if (entry != null && !entry.listed()) {
          index++;
        }
      }

      if (index > 0 && index < 4) {
        for (Entry entry2 : playerListS2CPacket.getEntries()) {
          if (entry2 != null && !entry2.listed()) {
            this.concurrentLinkedQueue.offer(
                AntiVanishData7.getAntiVanishData72(entry2.profileId()));
          }
        }
      }
    } else if (packet instanceof PlayerListS2CPacket playerListS2CPacket2
        && playerListS2CPacket2.getActions().contains(Action.UPDATE_GAME_MODE)) {
      this.apply(playerListS2CPacket2);
    } else if (packet instanceof GameMessageS2CPacket gameMessageS2CPacket) {
      String text =
          gameMessageS2CPacket.content() == null ? "" : gameMessageS2CPacket.content().getString();
      if (!text.isBlank()) {
        this.concurrentLinkedQueue.offer(AntiVanishData7.getAntiVanishData72(text));
      }

      String text2 = getString(gameMessageS2CPacket.content());
      if (!text2.isBlank()) {
        this.concurrentLinkedQueue.offer(AntiVanishData7.getAntiVanishData7(text2));
      }
    } else {
      if (packet instanceof CommandSuggestionsS2CPacket commandSuggestionsS2CPacket
          && this.set.remove(commandSuggestionsS2CPacket.id())) {
        ArrayList list2 = new ArrayList();

        for (Suggestion suggestion : commandSuggestionsS2CPacket.getSuggestions().getList()) {
          String text3 = suggestion.getText();
          if (text3 != null && !text3.isBlank()) {
            list2.add(text3.trim());
          }
        }

        this.list = list2;
        return true;
      }

      if (packet instanceof EntityTrackerUpdateS2CPacket entityTrackerUpdateS2CPacket) {
        this.concurrentLinkedQueue.offer(
            AntiVanishData7.getAntiVanishData7(entityTrackerUpdateS2CPacket.id()));
      } else if (packet instanceof ExplosionS2CPacket explosionS2CPacket) {
        this.concurrentLinkedQueue.offer(
            AntiVanishData7.getAntiVanishData7(
                explosionS2CPacket.center(), explosionS2CPacket.radius()));
      } else if (packet instanceof PlaySoundFromEntityS2CPacket playSoundFromEntityS2CPacket) {
        this.concurrentLinkedQueue.offer(
            AntiVanishData7.getAntiVanishData7(
                playSoundFromEntityS2CPacket.getEntityId(),
                getString(playSoundFromEntityS2CPacket.getSound().value().id())));
      } else if (packet instanceof ParticleS2CPacket particleS2CPacket) {
        Identifier id = Registries.PARTICLE_TYPE.getId(particleS2CPacket.getParameters().getType());
        this.concurrentLinkedQueue.offer(
            AntiVanishData7.getAntiVanishData7(
                AntiVanishState2.PARTICLE,
                particleS2CPacket.getX(),
                particleS2CPacket.getY(),
                particleS2CPacket.getZ(),
                id == null ? "" : id.toString()));
      } else if (packet instanceof PlaySoundS2CPacket playSoundS2CPacket) {
        this.apply(playSoundS2CPacket);
      }
    }

    return false;
  }

  public void apply(PlaySoundS2CPacket playSoundS2CPacket) {
    FakeLootDropModule.ensureInitialized();
    if (playSoundS2CPacket != null
        && playSoundS2CPacket.getSound() != null
        && playSoundS2CPacket.getSound().value() != null) {
      String text = getString(playSoundS2CPacket.getSound().value().id());
      this.concurrentLinkedQueue.offer(
          AntiVanishData7.getAntiVanishData7(
              AntiVanishState2.POSITIONAL_SOUND,
              playSoundS2CPacket.getX(),
              playSoundS2CPacket.getY(),
              playSoundS2CPacket.getZ(),
              text));
    }
  }

  public static boolean test() {
    FakeLootDropModule.ensureInitialized();

    AntiVanishModule antiVanishModule = getAntiVanishModule();
    return antiVanishModule != null && antiVanishModule.isEnabled() && antiVanishModule.test4();
  }

  public boolean test4() {
    FakeLootDropModule.ensureInitialized();

    long longValue = System.currentTimeMillis();
    if (longValue < this.timestamp13) {
      return true;
    }

    for (AntiVanishSupport antiVanishSupport :
        (java.lang.Iterable<AntiVanishSupport>) (java.lang.Iterable<?>) (this.map2.values())) {
      if (antiVanishSupport.timestamp > longValue && test(antiVanishSupport)) {
        return true;
      }
    }

    return false;
  }

  public static boolean test(AntiVanishSupport antiVanishSupport) {
    FakeLootDropModule.ensureInitialized();
    if (antiVanishSupport == null) {
      return false;
    }

    String text = antiVanishSupport.string == null ? "" : antiVanishSupport.string.trim();
    return !text.isBlank()
        && !"Unknown".equalsIgnoreCase(text)
        && !"You".equalsIgnoreCase(text)
        && !"CRITICAL".equalsIgnoreCase(text);
  }

  public static String getString(AntiVanishData6 antiVanishData6) {
    FakeLootDropModule.ensureInitialized();
    if (antiVanishData6 == null) {
      return "WATCH";
    } else if ("CRITICAL".equalsIgnoreCase(antiVanishData6._0())) {
      return "ALERT";
    } else {
      String text =
          antiVanishData6._A() == null ? "" : antiVanishData6._A().toLowerCase(Locale.ROOT);
      if (text.startsWith("vanish event")) {
        return "VANISH";
      } else if (text.startsWith("invisible entity")) {
        return "INVIS";
      } else if (text.startsWith("suspicious sound")) {
        return "SOUND";
      } else {
        return text.startsWith("ghost particle") ? "PARTICLE" : "WATCH";
      }
    }
  }

  public static String getString2(AntiVanishData6 antiVanishData6) {
    FakeLootDropModule.ensureInitialized();
    if (antiVanishData6 == null) {
      return "Staff";
    }

    if ("CRITICAL".equalsIgnoreCase(antiVanishData6._0())) {
      return getString(antiVanishData6._A());
    }

    String text = antiVanishData6._0() == null ? "" : antiVanishData6._0().trim();
    return !text.isBlank() && !"Unknown".equalsIgnoreCase(text) && !"You".equalsIgnoreCase(text)
        ? getString(text)
        : "Staff";
  }

  public static String getString(String text) {
    FakeLootDropModule.ensureInitialized();
    String text2 = text == null ? "" : NullSafeText.orEmpty(text.trim());
    return text2.length() <= 16 ? text2 : text2.substring(0, 16);
  }

  public static List getList2() {
    AntiVanishModule antiVanishModule = getAntiVanishModule();
    return antiVanishModule != null && test() ? antiVanishModule.hudSnapshot() : List.of();
  }

  public static AntiVanishModule getAntiVanishModule() {
    FakeLootDropModule.ensureInitialized();
    return ClientInitializer.getModuleManager() == null
        ? null
        : ClientInitializer.getModuleManager().antiVanishModule;
  }

  public void update4() {
    FakeLootDropModule.ensureInitialized();

    for (int index = 0; index < 512; index++) {
      AntiVanishData7 antiVanishData72 = (AntiVanishData7) this.concurrentLinkedQueue.poll();
      if (antiVanishData72 == null) {
        break;
      }

      this.apply(antiVanishData72);
    }

    while (this.concurrentLinkedQueue.size() > 4096) {
      this.concurrentLinkedQueue.poll();
    }
  }

  public void apply(AntiVanishData7 antiVanishData7) {
    FakeLootDropModule.ensureInitialized();
    switch (antiVanishData7.antiVanishState2().ordinal()) {
      case 0:
        {
          this.handleTabRemoval(antiVanishData7.uUID());
          break;
        }
      case 1:
        {
          this.apply(antiVanishData7.uUID());
          break;
        }
      case 2:
        {
          this.enabled = true;
          this.confirmDeparture(antiVanishData7.string());
          break;
        }
      case 3:
        {
          this.apply(antiVanishData7.string());
          break;
        }
      case 4:
        {
          this.apply(antiVanishData7.count());
          break;
        }
      case 5:
        {
          this.apply2(antiVanishData7);
          break;
        }
      case 6:
        {
          this.apply3(antiVanishData7);
          break;
        }
      case 7:
        {
          this.apply4(antiVanishData7);
          break;
        }
      case 8:
        {
          this.apply5(antiVanishData7);
          break;
        }
      case 9:
        {
          this.apply(antiVanishData7.uUID(), antiVanishData7.string());
          break;
        }
    }
  }

  public void update5() {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.getNetworkHandler() != null) {
      long longValue = System.currentTimeMillis();

      for (PlayerListEntry playerListEntry :
          minecraftClient.getNetworkHandler().getListedPlayerListEntries()) {
        if (playerListEntry != null
            && playerListEntry.getProfile() != null
            && playerListEntry.getProfile().id() != null) {
          UUID uUID2 = playerListEntry.getProfile().id();
          if (minecraftClient.player == null || !minecraftClient.player.getUuid().equals(uUID2)) {
            this.map10.putIfAbsent(uUID2, longValue);
            String string = playerListEntry.getProfile().name();
            if (string != null && !this.map.containsKey(uUID2)) {
              this.map.put(uUID2, new AntiVanishData2(uUID2, string));
            }

            if (string != null && !this.map9.isEmpty()) {
              this.test(string, false, 1000L);
            }
          }
        }
      }

      if (this.map10.size() > 1024 || this.map.size() > 1024) {
        HashSet hashSet = new HashSet();

        for (PlayerListEntry playerListEntry2 :
            minecraftClient.getNetworkHandler().getPlayerList()) {
          if (playerListEntry2 != null && playerListEntry2.getProfile() != null) {
            hashSet.add(playerListEntry2.getProfile().id());
          }
        }

        this.map10.keySet().retainAll(hashSet);
        this.map.keySet().retainAll(hashSet);
      }
    }
  }

  public boolean test(UUID uuid) {
    FakeLootDropModule.ensureInitialized();
    Long longValue = uuid == null ? null : (Long) this.map10.get(uuid);
    return longValue != null && System.currentTimeMillis() - longValue >= 1500L;
  }

  public boolean test(UUID uuid, String text) {
    FakeLootDropModule.ensureInitialized();
    return uuid != null
        && SearchTextNormalizer.test2(text)
        && StaffListModule.test(text)
        && this.test(uuid);
  }

  public void handleTabRemoval(UUID uUID2) {
    FakeLootDropModule.ensureInitialized();
    if (uUID2 != null && !minecraftClient.player.getUuid().equals(uUID2)) {
      String string = this.getString(uUID2);
      if (this.test(uUID2, string) && SearchTextNormalizer.test(string)) {
        this.deque4.removeIf(
            value -> {
              return ((bunnythekenzo.client.AntiVanishData5) (value)).uUID().equals(uUID2);
            });
        this.deque4.addLast(new AntiVanishData5(uUID2, string, this.count6 + 20));
      }
    }
  }

  public void apply(UUID uuid) {
    FakeLootDropModule.ensureInitialized();
    if (uuid != null && !minecraftClient.player.getUuid().equals(uuid)) {
      AntiVanishData2 antiVanishData2 = (AntiVanishData2) this.map.get(uuid);
      String text =
          antiVanishData2 != null && antiVanishData2.string() != null
              ? antiVanishData2.string()
              : this.getString(uuid);
      if (this.test(uuid, text) && SearchTextNormalizer.test(text)) {
        if (!this.test2(text)) {
          this.apply(uuid, text, false, "Vanish Event: hidden from TAB", "hidden from TAB", 100);
        }
      }
    }
  }

  public String getString(UUID uuid) {
    FakeLootDropModule.ensureInitialized();

    AntiVanishData2 antiVanishData2 = (AntiVanishData2) this.map.get(uuid);
    if (antiVanishData2 != null
        && antiVanishData2.string() != null
        && !antiVanishData2.string().isBlank()) {
      return antiVanishData2.string();
    }

    if (minecraftClient.getNetworkHandler() != null) {
      PlayerListEntry playerListEntry =
          minecraftClient.getNetworkHandler().getPlayerListEntry(uuid);
      if (playerListEntry != null && playerListEntry.getProfile() != null) {
        return playerListEntry.getProfile().name();
      }
    }

    return null;
  }

  public UUID getUUID(String text) {
    FakeLootDropModule.ensureInitialized();

    for (AntiVanishData2 antiVanishData2 :
        (java.lang.Iterable<AntiVanishData2>) (java.lang.Iterable<?>) (this.map.values())) {
      if (antiVanishData2.string().equalsIgnoreCase(text)) {
        return antiVanishData2.uUID();
      }
    }

    return null;
  }

  public void confirmDeparture(String text) {
    FakeLootDropModule.ensureInitialized();

    long longValue = System.currentTimeMillis();

    for (AntiVanishData2 antiVanishData2 :
        (java.lang.Iterable<AntiVanishData2>) (java.lang.Iterable<?>) (this.map.values())) {
      if (SearchTextNormalizer.test(text, antiVanishData2.string())) {
        this.map6.put(antiVanishData2.uUID(), longValue + 5000L);
        this.deque4.removeIf(
            (java.util.function.Predicate<bunnythekenzo.client.AntiVanishData5>)
                (antiVanishData5 -> {
                  return ((java.util.UUID) (antiVanishData5.uUID())).equals(antiVanishData2.uUID());
                }));
        this.map9.remove(getString2(antiVanishData2.string()));
        AntiVanishSupport antiVanishSupport =
            (AntiVanishSupport) this.map2.get(antiVanishData2.uUID().toString());
        if (antiVanishSupport != null && test(antiVanishSupport.string2)) {
          this.map2.remove(antiVanishData2.uUID().toString());
        }

        this.deque2.removeIf(
            (java.util.function.Predicate<bunnythekenzo.client.AntiVanishData8>)
                (antiVanishData8 -> {
                  FakeLootDropModule.ensureInitialized();
                  return ((bunnythekenzo.client.AntiVanishData8) (antiVanishData8)).antiVanishState()
                          == AntiVanishState.VANISH
                      && ((java.lang.String) (antiVanishData8.string()))
                          .equalsIgnoreCase(antiVanishData2.string())
                      && test(((bunnythekenzo.client.AntiVanishData8) (antiVanishData8)).string2());
                }));
      }
    }
  }

  public void update8() {
    FakeLootDropModule.ensureInitialized();

    while (!this.deque4.isEmpty()
        && ((AntiVanishData5) this.deque4.peekFirst()).count() <= this.count6) {
      AntiVanishData5 antiVanishData5 = (AntiVanishData5) this.deque4.removeFirst();
      if (minecraftClient.getNetworkHandler().getPlayerListEntry(antiVanishData5.uUID()) == null) {
        long timestamp = System.currentTimeMillis();
        if (this.map6.getOrDefault(antiVanishData5.uUID(), 0L) <= timestamp) {
          if (this.test2(antiVanishData5.string())) {
            this.enabled = true;
          } else {
            PlayerEntity playerByUuid =
                minecraftClient.world.getPlayerByUuid(antiVanishData5.uUID());
            if (playerByUuid != null && !playerByUuid.isRemoved()) {
              this.apply(
                  antiVanishData5.uUID(),
                  antiVanishData5.string(),
                  false,
                  "Vanish Event: entity remained",
                  "off TAB, still nearby",
                  100);
            } else {
              String string22 = "Vanish Event: silent TAB disappearance";
              int a_ = computeInt(this.enabled);
              if (this.enabled) {
                this.apply(
                    antiVanishData5.uUID(),
                    antiVanishData5.string(),
                    false,
                    string22,
                    "left TAB silently",
                    a_);
              } else {
                AntiVanishData4 antiVanishData4 = this.getAntiVanishData4(antiVanishData5.uUID());
                if (antiVanishData4 != null) {
                  this.deque2.addLast(
                      new AntiVanishData8(
                          AntiVanishState.VANISH,
                          antiVanishData5.string(),
                          antiVanishData4,
                          string22,
                          a_,
                          timestamp));
                  this.update13();
                }
              }
            }
          }
        }
      }
    }
  }

  public static int computeInt(boolean flag) {
    FakeLootDropModule.ensureInitialized();
    return flag ? 100 : 30;
  }

  public static boolean test(String text) {
    FakeLootDropModule.ensureInitialized();
    if (text == null) {
      return false;
    }

    String text2 = text.toLowerCase(Locale.ROOT);
    return text2.startsWith("vanish event:")
        && (text2.contains("tab")
            || text2.contains("entity remained")
            || text2.contains("no leave packet"));
  }

  public void apply(String string) {
    FakeLootDropModule.ensureInitialized();
    if (string != null && !string.isBlank()) {
      long timestamp = System.currentTimeMillis();
      this.deque5.addLast(new AntiVanishData(string, timestamp));

      while (!this.deque5.isEmpty()
          && (timestamp - ((AntiVanishData) this.deque5.peekFirst()).timestamp() > 8000L
              || this.deque5.size() > 64)) {
        this.deque5.removeFirst();
      }
    }
  }

  public boolean test2(String text) {
    FakeLootDropModule.ensureInitialized();
    if (text != null && !text.isBlank()) {
      long longValue = System.currentTimeMillis();

      for (AntiVanishData antiVanishData :
          (java.lang.Iterable<AntiVanishData>) (java.lang.Iterable<?>) (this.deque5)) {
        if (longValue - antiVanishData.timestamp() <= 8000L
            && SearchTextNormalizer.test2(antiVanishData.string(), text)) {
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  public void update9() {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.getNetworkHandler() != null) {
      String text = "minecraft:msg";
      int intValue = this.count5++;
      if (this.count5 > 40000) {
        this.count5 = 30000;
      }

      this.set.add(intValue);

      while (this.set.size() > 8) {
        this.set.remove(this.set.iterator().next());
      }

      try {
        minecraftClient
            .getNetworkHandler()
            .sendPacket(new RequestCommandCompletionsC2SPacket(intValue, text.trim() + " "));
      } catch (Throwable failure) {
      }
    }
  }

  public void update10() {
    FakeLootDropModule.ensureInitialized();

    List list2 = this.list;
    if (list2 != null) {
      this.list = null;
      if (minecraftClient.getNetworkHandler() != null && minecraftClient.player != null) {
        HashSet hashSet = new HashSet();

        for (PlayerListEntry playerListEntry :
            minecraftClient.getNetworkHandler().getPlayerList()) {
          if (playerListEntry != null
              && playerListEntry.getProfile() != null
              && playerListEntry.getProfile().name() != null) {
            hashSet.add(playerListEntry.getProfile().name().toLowerCase(Locale.ROOT));
          }
        }

        String string = minecraftClient.player.getName().getString();
        ArrayList arrayList = new ArrayList();

        for (String text : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (list2)) {
          if (StaffListModule.test(text)
              && SearchTextNormalizer.test(text)
              && !text.equalsIgnoreCase(string)
              && !hashSet.contains(text.toLowerCase(Locale.ROOT))
              && !this.test2(text)) {
            arrayList.add(text);
          }
        }

        if (!arrayList.isEmpty() && arrayList.size() <= 3) {
          for (String text2 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (arrayList)) {
            this.apply(
                this.getUUID(text2),
                text2,
                false,
                "Vanish Event: hidden but targetable",
                "off TAB, still targetable",
                90);
          }
        }
      }
    }
  }

  public static String getString(Text text) {
    FakeLootDropModule.ensureInitialized();
    if (text != null
        && text.getContent() instanceof TranslatableTextContent translatableTextContent
        && "multiplayer.player.left".equals(translatableTextContent.getKey())) {
      Object[] args = translatableTextContent.getArgs();
      if (args.length != 0 && args[0] != null) {
        return args[0] instanceof Text text2 ? text2.getString() : String.valueOf(args[0]);
      } else {
        return "";
      }
    } else {
      return "";
    }
  }

  public void apply(
      UUID uuid, String text, boolean flag, String text2, String text3, int intValue) {
    FakeLootDropModule.ensureInitialized();

    this.apply(getString(uuid, text), text, text2, intValue, System.currentTimeMillis() + 20000L);
    AntiVanishSupport2 antiVanishSupport2 = (AntiVanishSupport2) this.map9.get(getString2(text));
    if (antiVanishSupport2 == null || antiVanishSupport2.enabled != flag) {
      this.map9.put(
          getString2(text),
          new AntiVanishSupport2(
              uuid, text, flag, text3, this.getAntiVanishData4(uuid), this.count6));
    }
  }

  public void update11() {
    FakeLootDropModule.ensureInitialized();

    Iterator iterator = this.map9.values().iterator();

    while (iterator.hasNext()) {
      AntiVanishSupport2 antiVanishSupport2 = (AntiVanishSupport2) iterator.next();
      if (antiVanishSupport2.enabled
          && minecraftClient.getNetworkHandler().getPlayerListEntry(antiVanishSupport2.uUID)
              == null) {
        iterator.remove();
      } else if (!antiVanishSupport2.enabled3 && antiVanishSupport2.count < this.count6) {
        AntiVanishData4 antiVanishData4 = this.getAntiVanishData4(antiVanishSupport2.uUID);
        if (antiVanishData4 == null && !antiVanishSupport2.enabled2) {
          antiVanishData4 = antiVanishSupport2.antiVanishData4;
        }

        if (antiVanishData4 != null) {
          antiVanishSupport2.enabled2 = true;
          antiVanishSupport2.enabled3 = true;
          this.apply(antiVanishSupport2.string, antiVanishData4);
        } else if (!antiVanishSupport2.enabled2) {
          antiVanishSupport2.enabled2 = true;
          this.apply(
              antiVanishSupport2.string, antiVanishSupport2.getText(), antiVanishSupport2.string2);
        }
      }
    }
  }

  public boolean test(String text, boolean flag, long longValue) {
    FakeLootDropModule.ensureInitialized();

    AntiVanishSupport2 antiVanishSupport2 = (AntiVanishSupport2) this.map9.get(getString2(text));
    if (antiVanishSupport2 != null
        && antiVanishSupport2.enabled == flag
        && System.currentTimeMillis() - antiVanishSupport2.timestamp >= longValue) {
      this.map9.remove(getString2(text));
      this.map2.remove(getString(antiVanishSupport2.uUID, antiVanishSupport2.string));
      if (antiVanishSupport2.enabled2) {
        this.apply(
            antiVanishSupport2.string,
            ClientChat.getChatMessageBuilder()
                .getChatMessageBuilder("is ")
                .getChatMessageBuilder4("visible again")
                .getText(),
            "");
      }

      return true;
    } else {
      return false;
    }
  }

  public AntiVanishData4 getAntiVanishData4(UUID uUID2) {
    FakeLootDropModule.ensureInitialized();
    if (uUID2 == null) {
      return null;
    } else {
      PlayerEntity playerByUuid = minecraftClient.world.getPlayerByUuid(uUID2);
      if (playerByUuid != null && playerByUuid != minecraftClient.player) {
        return new AntiVanishData4(
            uUID2, Math.round(playerByUuid.distanceTo(minecraftClient.player)));
      } else {
        return StaffListModule.test(uUID2) ? new AntiVanishData4(uUID2, -1) : null;
      }
    }
  }

  public static String getString2(String text) {
    return text.toLowerCase(Locale.ROOT);
  }

  public static String getString(UUID uuid, String text) {
    FakeLootDropModule.ensureInitialized();
    return uuid != null ? uuid.toString() : text;
  }

  public void update12() {
    double value = this.computeDouble();
    FakeLootDropModule.ensureInitialized();

    for (PlayerEntity playerEntity : minecraftClient.world.getPlayers()) {
      if (playerEntity != null
          && playerEntity != minecraftClient.player
          && playerEntity.isInvisible()
          && !(playerEntity.squaredDistanceTo(minecraftClient.player) > value)
          && this.test(playerEntity)) {
        this.apply(
            AntiVanishState.INVISIBLE, playerEntity, "Invisible Entity: metadata flag", 35, 5000L);
      }
    }
  }

  public void apply(int intValue) {
    FakeLootDropModule.ensureInitialized();

    if (minecraftClient.world.getEntityById(intValue) instanceof PlayerEntity playerEntity
        && playerEntity != minecraftClient.player
        && playerEntity.isInvisible()) {
      if (!(playerEntity.squaredDistanceTo(minecraftClient.player) > this.computeDouble())) {
        if (this.test(playerEntity)) {
          this.apply(
              AntiVanishState.INVISIBLE,
              playerEntity,
              "Invisible Entity: metadata flag",
              35,
              5000L);
        }
      }
    }
  }

  public boolean test(PlayerEntity playerEntity) {
    return this.test(playerEntity.getUuid(), getString(playerEntity));
  }

  public static String getString(PlayerEntity playerEntity) {
    FakeLootDropModule.ensureInitialized();
    return playerEntity.getGameProfile() == null
        ? playerEntity.getName().getString()
        : playerEntity.getGameProfile().name();
  }

  public void apply2(AntiVanishData7 antiVanishData72) {
    FakeLootDropModule.ensureInitialized();
    if (WorldSignalClassifier.isInteractionSound(antiVanishData72.string())) {
      Vec3d vec3d = antiVanishData72.getVec3d();
      long longValue = System.currentTimeMillis();
      if (this.test2(vec3d)
          && !this.test3(vec3d)
          && !this.test4(vec3d)
          && !this.test2(vec3d, antiVanishData72.string())
          && longValue - this.timestamp10 >= 1000L
          && !this.test(vec3d)
          && !this.test(antiVanishData72.string(), vec3d)
          && (!this.test5() || !test3(antiVanishData72.string()))) {
        this.apply(
            AntiVanishState.SOUND,
            this.getString(vec3d),
            "Suspicious Sound: " + getString3(antiVanishData72.string()),
            14,
            3000L);
      }
    }
  }

  public void apply3(AntiVanishData7 antiVanishData72) {
    FakeLootDropModule.ensureInitialized();
    if (WorldSignalClassifier.isInteractionSound(antiVanishData72.string())) {
      if (minecraftClient.world.getEntityById(antiVanishData72.count())
              instanceof PlayerEntity playerEntity
          && playerEntity != minecraftClient.player
          && playerEntity.isInvisible()
          && playerEntity.squaredDistanceTo(minecraftClient.player) <= this.computeDouble()
          && this.test(playerEntity)) {
        this.apply(
            AntiVanishState.SOUND, playerEntity, "Suspicious Sound: invisible source", 16, 3000L);
      }
    }
  }

  public void apply4(AntiVanishData7 antiVanishData72) {
    FakeLootDropModule.ensureInitialized();
    if (WorldSignalClassifier.isCombatParticle(antiVanishData72.string())) {
      Vec3d vec3d = antiVanishData72.getVec3d();
      long longValue = System.currentTimeMillis();
      if (this.test2(vec3d)
          && !this.test3(vec3d)
          && !this.test4(vec3d)
          && longValue - this.timestamp10 >= 900L
          && !this.test(vec3d, antiVanishData72.string())) {
        String text = getString3(antiVanishData72.string());
        if (!text.equals("block") && !text.contains("smoke") || !this.test(vec3d, 6.25)) {
          if (!text.equals("block") && !text.contains("smoke")
              || this.particleBurstReady(text, longValue)) {
            this.apply(
                AntiVanishState.PARTICLE,
                this.getString(vec3d),
                "Ghost Particle: " + getString3(antiVanishData72.string()),
                16,
                3000L);
          }
        }
      }
    }
  }

  public boolean test(String text, Vec3d vec3d) {
    FakeLootDropModule.ensureInitialized();

    String string = WorldSignalClassifier.normalizeIdentifier(text);
    boolean flag =
        string.contains("door") && !string.contains("trapdoor") || string.contains("fence_gate");
    if (flag && vec3d != null && minecraftClient.world != null) {
      for (Entity entity : minecraftClient.world.getEntities()) {
        if (entity.getType() == EntityType.VILLAGER
            && entity.getEntityPos().squaredDistanceTo(vec3d) <= 9.0) {
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  public boolean test(Vec3d vec3d) {
    FakeLootDropModule.ensureInitialized();
    if (vec3d == null) {
      return false;
    }

    Long longValue = (Long) this.map5.get(BlockPos.ofFloored(vec3d).asLong());
    return longValue != null && longValue > System.currentTimeMillis();
  }

  public void apply5(AntiVanishData7 antiVanishData72) {
    FakeLootDropModule.ensureInitialized();

    long timestamp = System.currentTimeMillis();
    this.deque3.addLast(
        new AntiVanishData3(
            antiVanishData72.getVec3d(),
            Math.max(2.0, computeDouble(antiVanishData72.string(), 4.0) + 4.0),
            timestamp));

    while (this.deque3.size() > 8) {
      this.deque3.removeFirst();
    }
  }

  public boolean particleBurstReady(String text, long longValue) {
    FakeLootDropModule.ensureInitialized();

    Deque deque6 =
        (java.util.Deque)
            (this.map8.computeIfAbsent(
                text,
                value2 -> {
                  return new ArrayDeque();
                }));
    deque6.addLast(longValue);

    while (!deque6.isEmpty() && longValue - (Long) deque6.peekFirst() > 2000L) {
      deque6.removeFirst();
    }

    if (deque6.size() < 2) {
      return false;
    }

    deque6.clear();
    return true;
  }

  public void apply(
      AntiVanishState antiVanishState, String text, String text2, int intValue, long longValue) {
    this.apply(antiVanishState, text, null, text2, intValue, longValue);
  }

  public void apply(
      AntiVanishState antiVanishState,
      PlayerEntity playerEntity,
      String text,
      int intValue,
      long longValue) {
    this.apply(
        antiVanishState,
        getString(playerEntity),
        this.getAntiVanishData4(playerEntity.getUuid()),
        text,
        intValue,
        longValue);
  }

  public void apply(
      AntiVanishState antiVanishState,
      String string,
      AntiVanishData4 n_,
      String string22,
      int a_,
      long longValue) {
    FakeLootDropModule.ensureInitialized();

    long timestamp = System.currentTimeMillis();
    String text = antiVanishState.name() + "|" + string.toLowerCase(Locale.ROOT);
    long longValue2 = (java.lang.Long) (this.map3.getOrDefault(text, 0L));
    if (timestamp - longValue2 >= longValue) {
      this.map3.put(text, timestamp);
      this.deque2.addLast(
          new AntiVanishData8(antiVanishState, string, n_, string22, a_, timestamp));
      this.apply("signal:" + text, string, string22, a_, timestamp + 20000L);
      this.apply(text, string, n_ != null, string22);
      this.update13();
    }
  }

  public void update13() {
    FakeLootDropModule.ensureInitialized();

    long longValue = System.currentTimeMillis();
    this.apply(longValue);
    var enumMap = new EnumMap<>(AntiVanishState.class);
    AntiVanishData8 antiVanishData8 = null;

    for (AntiVanishData8 antiVanishData82 :
        (java.lang.Iterable<AntiVanishData8>) (java.lang.Iterable<?>) (this.deque2)) {
      enumMap.merge(
          antiVanishData82.antiVanishState(),
          antiVanishData82.a_(),
          (referenceArgument60_0, referenceArgument60_1) ->
              Math.max(
                  (java.lang.Integer) referenceArgument60_0,
                  (java.lang.Integer) referenceArgument60_1));
      if (antiVanishData82.n_() != null) {
        antiVanishData8 = antiVanishData82;
      }
    }

    int intValue =
        enumMap.values().stream()
            .mapToInt(
                (referenceArgument61_0) -> ((java.lang.Integer) referenceArgument61_0).intValue())
            .sum();
    if (antiVanishData8 != null && enumMap.size() >= 2 && intValue >= 35) {
      if (longValue - this.timestamp12 >= 12000L) {
        this.apply(antiVanishData8.string(), antiVanishData8.n_());
      }
    }
  }

  public void apply(String string22, AntiVanishData4 antiVanishData4) {
    long timestamp122 = System.currentTimeMillis();
    this.timestamp12 = timestamp122;
    this.timestamp13 = timestamp122 + 7000L;
    this.string2 = string22;
    this.set2.add(antiVanishData4.uUID());
    this.uUID = antiVanishData4.uUID();
    this.string3 = string22;
  }

  public void update14() {
    FakeLootDropModule.ensureInitialized();
    if (!this.set2.isEmpty()) {
      boolean flag =
          this.set2.stream()
              .allMatch(
                  (referenceArgument62_0) ->
                      StaffListModule.test2((java.util.UUID) referenceArgument62_0));
      this.set2.clear();
      if (!flag) {
        if (minecraftClient.getSoundManager() != null) {
          minecraftClient
              .getSoundManager()
              .play(
                  PositionedSoundInstance.ui(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, 1.0F, 1.0F));
        }

        StaffWatchNotification.apply(this.uUID, this.string3, 0);
      }
    }
  }

  public void apply(String text, Text text2, String text3) {
    FakeLootDropModule.ensureInitialized();

    ChatMessageBuilder chatMessageBuilder =
        ClientChat.getChatMessageBuilder()
            .getChatMessageBuilder2(NullSafeText.orEmpty(text))
            .getChatMessageBuilder(" ")
            .getLookupSupport4(text2);
    if (!text3.isEmpty()) {
      chatMessageBuilder.getChatMessageBuilder(" (" + text3 + ")");
    }

    ClientChat.apply(this, chatMessageBuilder);
  }

  public void apply(PlayerListS2CPacket playerListS2CPacket) {
    FakeLootDropModule.ensureInitialized();
    if (!playerListS2CPacket.getActions().contains(Action.ADD_PLAYER)) {
      for (Entry entry : playerListS2CPacket.getEntries()) {
        if (entry != null && entry.gameMode() != null && entry.profileId() != null) {
          this.concurrentLinkedQueue.offer(
              AntiVanishData7.getAntiVanishData7(entry.profileId(), entry.gameMode().getId()));
        }
      }
    }
  }

  public void apply(UUID uuid, String text) {
    FakeLootDropModule.ensureInitialized();
    if (uuid != null
        && (minecraftClient.player == null || !minecraftClient.player.getUuid().equals(uuid))) {
      AntiVanishData2 antiVanishData2 = (AntiVanishData2) this.map.get(uuid);
      if (antiVanishData2 != null && StaffListModule.test(antiVanishData2.string())) {
        if (GameMode.SPECTATOR.getId().equals(text)) {
          if (this.test(uuid, antiVanishData2.string())) {
            this.apply(
                uuid,
                antiVanishData2.string(),
                true,
                "Vanish Event: switched to spectator",
                "",
                100);
            return;
          }
        } else if (this.test(antiVanishData2.string(), true, 0L)) {
          return;
        }

        ClientChat.apply(
            this,
            StaffListModule.getChatMessageBuilder(
                ClientChat.getChatMessageBuilder()
                    .getChatMessageBuilder2(NullSafeText.orEmpty(antiVanishData2.string()))
                    .getChatMessageBuilder(" "),
                text));
      }
    }
  }

  public void apply(String text, String text2, boolean flag, String text3) {
    FakeLootDropModule.ensureInitialized();

    long longValue = System.currentTimeMillis();
    Long longValue2 = (Long) this.map4.get(text);
    if (longValue2 == null || longValue - longValue2 >= 1500L) {
      while (!this.deque.isEmpty() && longValue - this.deque.peekFirst() > 4000L) {
        this.deque.removeFirst();
      }

      if (this.deque.size() < 6) {
        this.map4.put(text, longValue);
        this.deque.addLast(longValue);
        int intValue = text3.indexOf(": ");
        ChatMessageBuilder chatMessageBuilder =
            intValue < 0
                ? ClientChat.getChatMessageBuilder().getChatMessageBuilder5(text3)
                : ClientChat.getChatMessageBuilder()
                    .getChatMessageBuilder5(text3.substring(0, intValue))
                    .getChatMessageBuilder(text3.substring(intValue));
        boolean flag2 =
            text2 != null
                && !text2.isBlank()
                && !"Unknown".equalsIgnoreCase(text2)
                && !"CRITICAL".equalsIgnoreCase(text2);
        if (flag2) {
          chatMessageBuilder.getChatMessageBuilder(" (");
          if (flag) {
            chatMessageBuilder.getChatMessageBuilder2(NullSafeText.orEmpty(text2));
          } else {
            chatMessageBuilder.getChatMessageBuilder3(text2);
          }

          chatMessageBuilder.getChatMessageBuilder(")");
        }

        ClientChat.apply(this, chatMessageBuilder);
      }
    }
  }

  public void apply(String text, String string, String string22, int count, long longValue) {
    FakeLootDropModule.ensureInitialized();

    AntiVanishSupport antiVanishSupport = (AntiVanishSupport) this.map2.get(text);
    if (antiVanishSupport == null) {
      this.map2.put(text, new AntiVanishSupport(string, string22, count, longValue));
    } else {
      antiVanishSupport.string = string;
      antiVanishSupport.timestamp = Math.max(antiVanishSupport.timestamp, longValue);
      if (count >= antiVanishSupport.count) {
        antiVanishSupport.string2 = string22;
        antiVanishSupport.count = count;
      }
    }
  }

  public List hudSnapshot() {
    FakeLootDropModule.ensureInitialized();

    long longValue = System.currentTimeMillis();
    ArrayList arrayList = new ArrayList();
    if (longValue < this.timestamp13) {
      arrayList.add(new AntiVanishData6("CRITICAL", this.string2));
    }

    HashSet hashSet = new HashSet();
    this.map2.values().stream()
        .filter(
            value2 -> {
              FakeLootDropModule.ensureInitialized();
              return ((bunnythekenzo.client.AntiVanishSupport) (value2)).timestamp > longValue;
            })
        .filter(
            (referenceArgument63_0) ->
                AntiVanishModule.test((bunnythekenzo.client.AntiVanishSupport) referenceArgument63_0))
        .sorted(
            Comparator.<AntiVanishSupport>comparingInt(
                    antiVanishSupport -> {
                      return antiVanishSupport.count;
                    })
                .reversed()
                .thenComparing(
                    antiVanishSupport2 -> {
                      return antiVanishSupport2.string;
                    },
                    String.CASE_INSENSITIVE_ORDER))
        .map(
            (java.util.function.Function<
                    bunnythekenzo.client.AntiVanishSupport, bunnythekenzo.client.AntiVanishData6>)
                (antiVanishSupport3 -> {
                  return new AntiVanishData6(antiVanishSupport3.string, antiVanishSupport3.string2);
                }))
        .filter(
            (java.util.function.Predicate<bunnythekenzo.client.AntiVanishData6>)
                (antiVanishData6 -> {
                  return hashSet.add(
                      getString(antiVanishData6) + "\u0000" + getString2(antiVanishData6));
                }))
        .limit(4L)
        .forEach(arrayList::add);
    return List.copyOf(arrayList);
  }

  public void pruneState() {
    FakeLootDropModule.ensureInitialized();

    long longValue = System.currentTimeMillis();
    this.map2
        .entrySet()
        .removeIf(
            value2 -> {
              FakeLootDropModule.ensureInitialized();
              return ((AntiVanishSupport) ((java.util.Map.Entry) (value2)).getValue()).timestamp
                  <= longValue;
            });
    this.map3
        .entrySet()
        .removeIf(
            value3 -> {
              FakeLootDropModule.ensureInitialized();
              return longValue - (Long) ((java.util.Map.Entry) (value3)).getValue() > 60000L;
            });
    this.map4
        .entrySet()
        .removeIf(
            value4 -> {
              FakeLootDropModule.ensureInitialized();
              return longValue - (Long) ((java.util.Map.Entry) (value4)).getValue() > 60000L;
            });
    this.map5
        .entrySet()
        .removeIf(
            value5 -> {
              FakeLootDropModule.ensureInitialized();
              return (Long) ((java.util.Map.Entry) (value5)).getValue() <= longValue;
            });
    this.map6
        .entrySet()
        .removeIf(
            entry -> {
              FakeLootDropModule.ensureInitialized();
              return (Long) ((java.util.Map.Entry) (entry)).getValue() <= longValue;
            });
    this.map7
        .entrySet()
        .removeIf(
            entry2 -> {
              FakeLootDropModule.ensureInitialized();
              return (Long) ((java.util.Map.Entry) (entry2)).getValue() <= longValue;
            });
    this.map8
        .values()
        .removeIf(
            (java.util.function.Predicate<java.util.Deque>)
                (deque6 -> {
                  FakeLootDropModule.ensureInitialized();

                  while (!((java.util.Deque) (deque6)).isEmpty()
                      && longValue - (Long) ((java.util.Deque) (deque6)).peekFirst() > 2000L) {
                    ((java.util.Deque) (deque6)).removeFirst();
                  }

                  return ((java.util.Deque) (deque6)).isEmpty();
                }));

    while (!this.deque3.isEmpty()
        && longValue - ((AntiVanishData3) this.deque3.peekFirst()).timestamp() > 3000L) {
      this.deque3.removeFirst();
    }

    this.apply(longValue);
  }

  public void apply(long longValue) {
    FakeLootDropModule.ensureInitialized();

    while (!this.deque2.isEmpty()
        && longValue - ((AntiVanishData8) this.deque2.peekFirst()).timestamp() > 15000L) {
      this.deque2.removeFirst();
    }
  }

  public boolean test2(Vec3d vec3d) {
    FakeLootDropModule.ensureInitialized();
    return vec3d != null
        && vec3d.squaredDistanceTo(minecraftClient.player.getEntityPos()) <= this.computeDouble();
  }

  public boolean test(Vec3d vec3d, double doubleValue) {
    FakeLootDropModule.ensureInitialized();
    return vec3d != null
        && minecraftClient.player != null
        && vec3d.squaredDistanceTo(minecraftClient.player.getEntityPos()) < doubleValue;
  }

  public boolean test5() {
    FakeLootDropModule.ensureInitialized();
    return System.currentTimeMillis() - this.timestamp11 < 2500L;
  }

  public static boolean test3(String text) {
    FakeLootDropModule.ensureInitialized();

    String string = WorldSignalClassifier.normalizeIdentifier(text);
    return string.contains("chest") || string.contains("barrel") || string.contains("shulker");
  }

  public String getString(Vec3d vec3d) {
    FakeLootDropModule.ensureInitialized();
    if (vec3d != null && minecraftClient.player != null) {
      Vec3d entityPos = minecraftClient.player.getEntityPos();
      double doubleValue = vec3d.x - entityPos.x;
      double doubleValue2 = vec3d.y - entityPos.y;
      double doubleValue3 = vec3d.z - entityPos.z;
      long longValue =
          Math.round(
              Math.sqrt(
                  doubleValue * doubleValue
                      + doubleValue2 * doubleValue2
                      + doubleValue3 * doubleValue3));
      String text = getString(doubleValue, doubleValue3);
      return text.isEmpty() ? longValue + "m" : longValue + "m " + text;
    } else {
      return "nearby";
    }
  }

  public static String getString(double doubleValue, double doubleValue2) {
    FakeLootDropModule.ensureInitialized();
    String text = doubleValue2 < -1.0 ? "N" : (doubleValue2 > 1.0 ? "S" : "");
    String text2 = doubleValue > 1.0 ? "E" : (doubleValue < -1.0 ? "W" : "");
    return text + text2;
  }

  public boolean test3(Vec3d vec3d) {
    FakeLootDropModule.ensureInitialized();
    if (vec3d == null) {
      return true;
    }

    for (Entity entity : minecraftClient.world.getEntities()) {
      if (entity != minecraftClient.player) {
        if (entity instanceof PlayerEntity playerEntity) {
          if (!playerEntity.isInvisible()
              && entity.getEntityPos().squaredDistanceTo(vec3d) <= 16.0) {
            return true;
          }
        } else if (entity instanceof ProjectileEntity
            && entity.getEntityPos().squaredDistanceTo(vec3d) <= 16.0) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean test4(Vec3d vec3d) {
    FakeLootDropModule.ensureInitialized();
    if (vec3d == null) {
      return false;
    }

    long longValue = System.currentTimeMillis();

    while (!this.deque3.isEmpty()
        && longValue - ((AntiVanishData3) this.deque3.peekFirst()).timestamp() > 3000L) {
      this.deque3.removeFirst();
    }

    for (AntiVanishData3 antiVanishData3 :
        (java.lang.Iterable<AntiVanishData3>) (java.lang.Iterable<?>) (this.deque3)) {
      if (antiVanishData3.vec3d().squaredDistanceTo(vec3d)
          <= antiVanishData3.value() * antiVanishData3.value()) {
        return true;
      }
    }

    return false;
  }

  public boolean test(Vec3d vec3d, String text) {
    FakeLootDropModule.ensureInitialized();
    if (vec3d != null && getString3(text).contains("smoke")) {
      BlockPos blockPos = BlockPos.ofFloored(vec3d);

      for (int index = -2; index <= 2; index++) {
        for (int index2 = -2; index2 <= 2; index2++) {
          for (int index3 = -2; index3 <= 2; index3++) {
            BlockPos blockPos2 = blockPos.add(index, index2, index3);
            Identifier id =
                Registries.BLOCK.getId(minecraftClient.world.getBlockState(blockPos2).getBlock());
            String text2 = getString3(id == null ? "" : id.toString());
            if (text2.contains("campfire")
                || text2.contains("furnace")
                || text2.contains("smoker")
                || text2.contains("torch")
                || text2.contains("fire")
                || text2.contains("candle")
                || text2.contains("respawn_anchor")) {
              return true;
            }
          }
        }
      }

      return false;
    } else {
      return false;
    }
  }

  public boolean test2(Vec3d vec3d, String text) {
    FakeLootDropModule.ensureInitialized();

    String text2 = getString3(text);
    if (!text2.contains("door") && !text2.contains("trapdoor")) {
      return false;
    }

    BlockPos blockPos = BlockPos.ofFloored(vec3d);
    long longValue = System.currentTimeMillis();

    for (int index = -1; index <= 1; index++) {
      BlockPos blockPos2 = blockPos.add(0, index, 0);
      BlockState blockState = minecraftClient.world.getBlockState(blockPos2);
      long longValue2 = blockPos2.asLong();
      boolean flag = blockState.contains(Properties.POWERED) && blockState.get(Properties.POWERED);
      if (flag || minecraftClient.world.isReceivingRedstonePower(blockPos2)) {
        this.map7.put(longValue2, longValue + 5000L);
        return true;
      }

      if (this.map7.getOrDefault(longValue2, 0L) > longValue) {
        return true;
      }
    }

    return false;
  }

  public double computeDouble() {
    return 4096.0;
  }

  public static double computeDouble(String text, double doubleValue) {
    try {
      return Double.parseDouble(text);
    } catch (Exception failure) {
      return doubleValue;
    }
  }

  public static String getString(Identifier identifier) {
    FakeLootDropModule.ensureInitialized();
    return identifier == null ? "" : identifier.toString();
  }

  public static String getString3(String text) {
    return WorldSignalClassifier.normalizeIdentifier(text);
  }

  public void update15() {
    this.concurrentLinkedQueue.clear();
    this.map.clear();
    this.map2.clear();
    this.map3.clear();
    this.map4.clear();
    this.deque.clear();
    this.map5.clear();
    this.map6.clear();
    this.map7.clear();
    this.map8.clear();
    this.deque2.clear();
    this.deque3.clear();
    this.deque4.clear();
    this.map9.clear();
    this.worldChangeTracker.update();
    this.count6 = 0;
    this.timestamp10 = 0L;
    this.timestamp11 = 0L;
    this.timestamp12 = 0L;
    this.timestamp13 = 0L;
    this.string2 = "";
    this.set2.clear();
    this.uUID = null;
    this.string3 = null;
    this.deque5.clear();
    this.map10.clear();
    this.enabled = false;
    this.set.clear();
    this.list = null;
    this.count5 = 30000;
  }

  static {
    setClientInitializerArray(null);
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }

  public static void ensureInitialized() {}
}
