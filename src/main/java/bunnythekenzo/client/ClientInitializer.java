package bunnythekenzo.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bunnythekenzo.client.hud.NotificationsHud;
import bunnythekenzo.client.module.AntiVanishModule;
import bunnythekenzo.client.module.FreecamModule;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.render.AnimatedGlintTexture;
import bunnythekenzo.client.render.AuroraRenderer;
import bunnythekenzo.client.render.CosmeticsFeatureRenderer;
import bunnythekenzo.client.render.CrystalPulseRenderer;
import bunnythekenzo.client.render.LumaRenderer;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.render.SkyRenderer;
import bunnythekenzo.client.setting.SoundSettings;
import bunnythekenzo.client.ui.ClientScreen;
import bunnythekenzo.client.ui.PanelMenu;
import bunnythekenzo.client.ui.ScreenSupport;
import bunnythekenzo.client.ui.ScreenSupport3;
import bunnythekenzo.client.ui.ScreenSupport4;
import bunnythekenzo.client.ui.WelcomeScreen;

public class ClientInitializer implements ClientModInitializer {

  public static final Logger logger = LoggerFactory.getLogger("bunnythekenzo");
  public static ModuleManager moduleManager;
  public static ThemeManager themeManager;
  public static ClientConfig clientConfig;
  public static ConfigRepository configRepository;
  public static NotificationsHud notificationsHud;
  public static SpotifyBridge spotifyBridge;
  public static SoundSettings soundSettings;
  public static EmoteCatalog emoteCatalog;
  public static boolean enabled;
  public static boolean enabled2;
  public static String[] stringArray;

  public static ModuleManager getModuleManager() {
    return moduleManager;
  }

  public static ThemeManager getThemeManager() {
    return themeManager;
  }

  public static ClientConfig getClientConfig() {
    return clientConfig;
  }

  public static ConfigRepository getConfigRepository() {
    return configRepository;
  }

  public static SpotifyBridge getSpotifyBridge() {
    return spotifyBridge;
  }

  public static SoundSettings getSoundSettings() {
    return soundSettings;
  }

  public static NotificationsHud getNotificationsHud() {
    return notificationsHud;
  }

  public static EmoteCatalog getEmoteCatalog() {
    return emoteCatalog;
  }

  @Override
  public void onInitializeClient() {
    LookupSupport10.update();

    logger.info("{} {} initializing", "bunnythekenzo", "2.0.3");
    AntiVanishModule.ensureInitialized();

    ClientSounds.update();
    GuiPreferences.getGuiPreferences();
    themeManager = new ThemeManager();
    soundSettings = new SoundSettings();
    moduleManager = new ModuleManager();
    emoteCatalog = new EmoteCatalog();
    spotifyBridge = new SpotifyBridge();
    notificationsHud = new NotificationsHud(themeManager, moduleManager.hUDModule);
    MovementOverrides.init();
    PanicOrderAutomation.update();
    PearlEscapeAutomation.init();
    SkyRenderer.register();
    CosmeticsFeatureRenderer.register();
    LumaRenderer.update();
    AuroraRenderer.update();
    CrystalPulseRenderer.update();
    ClientEntityEvents.ENTITY_LOAD.register(
        (entity, clientWorld) -> {
          moduleManager.crystalPulseModule.apply(entity);
        });
    ClientEntityEvents.ENTITY_UNLOAD.register(
        (entity2, clientWorld2) -> {
          moduleManager.crystalPulseModule.apply2(entity2);
        });
    JsonSupport2.getList();
    JsonSupport2.getList3();
    ScreenSupport3.register();
    clientConfig = new ClientConfig(moduleManager, themeManager);

    final var referenceReceiver2 = PanelMenu.getPanelLayout();
    clientConfig.apply(
        "dropdownPanels",
        PanelMenu.getPanelLayout()::getJsonObject,
        (referenceArgument2_0) ->
            referenceReceiver2.apply((com.google.gson.JsonObject) referenceArgument2_0));

    clientConfig.apply(
        "hud",
        JsonSupport2::getJsonObject2,
        (referenceArgument3_0) ->
            JsonSupport2.apply2((com.google.gson.JsonObject) referenceArgument3_0));
    final var referenceReceiver4 = soundSettings;
    clientConfig.apply(
        "sounds",
        soundSettings::getJsonObject,
        (referenceArgument4_0) ->
            referenceReceiver4.apply((com.google.gson.JsonObject) referenceArgument4_0));
    clientConfig.update4();
    configRepository = new ConfigRepository(clientConfig);
    configRepository.update();
    ClientAudio.apply(soundSettings);
    moduleManager.setRunnable(
        () -> {
          MinecraftClient.getInstance().setScreen(ScreenSupport4.getScreen(null));
        });
    moduleManager.setBiConsumer(
        (java.util.function.BiConsumer<bunnythekenzo.client.module.Module, java.lang.Boolean>)
            ((module, flag) -> {
              AntiVanishModule.ensureInitialized();
              if (!ConfigRepository.enabled) {
                if (((bunnythekenzo.client.module.Module) (module)).getModuleCategory()
                    != ModuleCategory.getInstance()) {
                  if (MinecraftClient.getInstance().world != null) {
                    if ((Boolean) moduleManager.hUDModule.notificationsSetting.getValue()) {
                      notificationsHud.apply(module.getName(), flag);
                      ClientAudio.apply2(flag);
                    }
                  }
                }
              }
            }));
    WorldOverlayDispatcher.update();
    ScreenEvents.AFTER_INIT.register(
        (minecraftClient, screen, intValue, intValue2) -> {
          AntiVanishModule.ensureInitialized();
          if (screen instanceof TitleScreen
              && !enabled
              && ProfileStorage.getProfileStorage().getProfileDefaults().enabled) {
            enabled = true;
            ClientAudio.update8();
          }
        });
    ScreenSupport.register();
    ClientPlayConnectionEvents.JOIN.register(
        (clientPlayNetworkHandler, packetSender, minecraftClient2) -> {
          update();
        });
    ClientPlayConnectionEvents.DISCONNECT.register(
        (clientPlayNetworkHandler2, minecraftClient3) -> {
          update();
        });
    ClientChunkEvents.CHUNK_UNLOAD.register(
        (clientWorld3, worldChunk) -> {
          moduleManager.deepscanChunkFinderModule.deepscanWorker.apply(
              worldChunk.getPos().toLong());
        });
    ClientTickEvents.END_CLIENT_TICK.register(
        minecraftClient4 -> {
          clientConfig.update2();
          AntiVanishModule.ensureInitialized();

          ProfileStorage profileStorage = ProfileStorage.getProfileStorage();
          profileStorage.apply(minecraftClient4);
          if (OverlayRenderer.enabled && minecraftClient4.currentScreen instanceof ClientScreen) {
            minecraftClient4.setScreen(minecraftClient4.world == null ? new TitleScreen() : null);
          }
          if (!enabled2
              && !OverlayRenderer.enabled
              && !profileStorage.getProfileDefaults().enabled
              && minecraftClient4.currentScreen instanceof TitleScreen
              && minecraftClient4.getOverlay() == null
              && (!Boolean.getBoolean("fabric.client.gametest")
                  || Boolean.getBoolean("bunnythekenzo.test.profile"))) {
            enabled2 = true;
            enabled = true;
            minecraftClient4.setScreen(new WelcomeScreen(minecraftClient4.currentScreen));
          }

          moduleManager.update2();
          CameraChunkCulling.apply(minecraftClient4);
          emoteCatalog.update2();
        });
    ClientTickEvents.START_CLIENT_TICK.register(
        minecraftClient5 -> {
          AntiVanishModule.ensureInitialized();
          if (!PearlEscapeAutomation.test()) {
            FreecamModule freecamModule = moduleManager.freecamModule;
            if (freecamModule != null && freecamModule.test()) {
              FreecamModule.apply2(minecraftClient5);
            }

            moduleManager.update3();
          }
        });
    ClientLifecycleEvents.CLIENT_STOPPING.register(
        minecraftClient6 -> {
          ProfileStorage.getProfileStorage().test2();
          TextureImageCache.update();
          GuiDialogManager.closeAll();
          AnimatedGlintTexture.update2();
          clientConfig.update3();
          emoteCatalog.update4();
          emoteCatalog.update();
          spotifyBridge.update2();
        });
  }

  public static void update() {
    AntiVanishModule.ensureInitialized();
    if (moduleManager.autoCrystalModule != null) {
      moduleManager.autoCrystalModule.update4();
    }

    LightSectionCache.getInstance().update();
    moduleManager.susChunkFinderModule.suspiciousChunkScanner.update();
    LookupSupport3.update();
    if (moduleManager.clusterESPModule != null) {
      moduleManager.clusterESPModule.update4();
    }

    if (moduleManager.chunkFinderModule != null) {
      moduleManager.chunkFinderModule.update5();
    }

    if (moduleManager.deepscanChunkFinderModule != null) {
      moduleManager.deepscanChunkFinderModule.deepscanWorker.update();
    }

    if (moduleManager.blockEntityESPModule != null) {
      moduleManager.blockEntityESPModule.update5();
    }

    StorageScanner.update();
    BlockEntityScanner.update();
    HoleScanner.update();
    if (moduleManager.spawnerBeamModule != null) {
      moduleManager.spawnerBeamModule.update4();
    }

    if (moduleManager.client67JumpCirclesModule != null) {
      moduleManager.client67JumpCirclesModule.update4();
    }

    if (moduleManager.hitParticlesModule != null) {
      moduleManager.hitParticlesModule.update4();
    }

    if (moduleManager.targetESPModule != null) {
      moduleManager.targetESPModule.update4();
    }

    if (moduleManager.customBlockOutlineModule != null) {
      moduleManager.customBlockOutlineModule.blockOutlineTransition.update();
    }

    if (moduleManager.cosmeticsModule != null) {
      moduleManager.cosmeticsModule.update4();
    }

    if (moduleManager.lumaModule != null) {
      moduleManager.lumaModule.update4();
    }

    if (moduleManager.auroraModule != null) {
      moduleManager.auroraModule.update4();
    }

    if (moduleManager.crystalPulseModule != null) {
      moduleManager.crystalPulseModule.update4();
    }

    MotionBlurRenderer.update();
    if (emoteCatalog != null) {
      emoteCatalog.update();
    }
  }

  static {
    setStringArray(null);
  }

  public static void setStringArray(String[] stringArray2) {
    stringArray = stringArray2;
  }

  public static String[] getStringArray() {
    return stringArray;
  }

  public static void ensureInitialized() {}
}
