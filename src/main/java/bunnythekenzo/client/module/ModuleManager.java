package bunnythekenzo.client.module;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.GambleRiggerStateSupport;
import bunnythekenzo.client.PearlEscapeAutomation;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.Setting;

public class ModuleManager {
  public final List list = new ArrayList();
  public final Map map = new LinkedHashMap();
  public final ClickGUIModule clickGUIModule;
  public final HUDModule hUDModule;
  public final SpotifyHUDModule spotifyHUDModule;
  public final CustomBlockOutlineModule customBlockOutlineModule;
  public final SusChunkFinderModule susChunkFinderModule;
  public FullBrightModule fullBrightModule;
  public AutoWalkModule autoWalkModule;
  public WeatherNotifierModule weatherNotifierModule;
  public AnimationsModule animationsModule;
  @Deprecated public AnimationsModule animationsModule2;
  public StorageESPModule storageESPModule;
  public BlockESPModule blockESPModule;
  public AutoTotemModule autoTotemModule;
  public AnchorMacroModule anchorMacroModule;
  public AutoCrystalModule autoCrystalModule;
  public HitBoxModule hitBoxModule;
  public TriggerbotModule triggerbotModule;
  public AimAssistModule aimAssistModule;
  public DoubleAnchorModule doubleAnchorModule;
  public NameProtectModule nameProtectModule;
  public SkinProtectModule skinProtectModule;
  public NameTagsModule nameTagsModule;
  public FastUseModule fastUseModule;
  public InvTotemModule invTotemModule;
  public PlayerESPModule playerESPModule;
  public MobESPModule mobESPModule;
  public BlockEntityESPModule blockEntityESPModule;
  public SpawnerNametagsModule spawnerNametagsModule;
  public SpawnerBeamModule spawnerBeamModule;
  public HoleESPModule holeESPModule;
  public FreecamModule freecamModule;
  public AutoTPAModule autoTPAModule;
  public Client67JumpCirclesModule client67JumpCirclesModule;
  public CustomCrosshairModule customCrosshairModule;
  public ZoomModule zoomModule;
  public CustomFOVModule customFOVModule;
  public HitParticlesModule hitParticlesModule;
  public TargetESPModule targetESPModule;
  public MotionBlurModule motionBlurModule;
  public SkyModule skyModule;
  public CustomGlintModule customGlintModule;
  public CosmeticsModule cosmeticsModule;
  public LumaModule lumaModule;
  public AuroraModule auroraModule;
  public CrystalPulseModule crystalPulseModule;
  public ChunkFinderModule chunkFinderModule;
  public DeepscanChunkFinderModule deepscanChunkFinderModule;
  public FreeLookModule freeLookModule;
  public AutoClickerModule autoClickerModule;
  public AutoToolModule autoToolModule;
  public CoordSnapperModule coordSnapperModule;
  public RegionMapModule regionMapModule;
  public ChatMacroModule chatMacroModule;
  public FakePayModule fakePayModule;
  public FakeStatsModule fakeStatsModule;
  public FakeLootDropModule fakeLootDropModule;
  public StaffListModule staffListModule;
  public AntiVanishModule antiVanishModule;
  public ArmorTrimHiderModule armorTrimHiderModule;
  public SpawnerProtectModule spawnerProtectModule;
  public GambleRiggerStateSupport gambleRiggerStateSupport;
  public CreamyKeysModule creamyKeysModule;
  public ClusterESPModule clusterESPModule;
  public Runnable runnable = () -> {};
  public BiConsumer biConsumer = (value, value2) -> {};

  public ModuleManager() {
    for (ModuleCategory moduleCategory : ModuleCategory.values()) {
      this.map.put(moduleCategory, new ArrayList());
    }

    this.susChunkFinderModule = new SusChunkFinderModule();
    this.update();
    this.apply(this.customBlockOutlineModule = new CustomBlockOutlineModule());
    this.apply(this.hitParticlesModule = new HitParticlesModule());
    this.apply(this.targetESPModule = new TargetESPModule());
    this.apply(this.cosmeticsModule = new CosmeticsModule());
    this.apply(this.lumaModule = new LumaModule());
    this.apply(this.auroraModule = new AuroraModule());
    this.apply(this.crystalPulseModule = new CrystalPulseModule());
    this.apply(this.motionBlurModule = new MotionBlurModule());
    this.apply(this.skyModule = new SkyModule());
    this.apply(this.hUDModule = new HUDModule());
    this.apply(this.spotifyHUDModule = new SpotifyHUDModule());
    this.apply(this.chatMacroModule = new ChatMacroModule());
    this.apply(
        "ConfigShare",
        "Import/export configs via codes",
        ModuleCategory.getInstance(),
        new Setting[] {new BooleanSetting("Include HUD Layout", "Share HUD positions too", true)});
    this.apply(this.animationsModule = new AnimationsModule());
    this.animationsModule2 = this.animationsModule;
    this.apply(this.client67JumpCirclesModule = new Client67JumpCirclesModule());
    this.apply(this.creamyKeysModule = new CreamyKeysModule());
    this.apply(this.clickGUIModule = new ClickGUIModule());
  }

  public void update() {
    this.apply(this.autoTotemModule = new AutoTotemModule());
    this.apply(this.autoCrystalModule = new AutoCrystalModule());
    this.apply(this.anchorMacroModule = new AnchorMacroModule());
    this.apply(this.doubleAnchorModule = new DoubleAnchorModule());
    this.apply(this.invTotemModule = new InvTotemModule());
    this.apply(this.aimAssistModule = new AimAssistModule());
    this.apply(this.hitBoxModule = new HitBoxModule());
    this.apply(this.triggerbotModule = new TriggerbotModule());
    this.apply(this.skinProtectModule = new SkinProtectModule());
    this.apply(this.nameProtectModule = new NameProtectModule());
    this.apply(this.freecamModule = new FreecamModule());
    this.apply(this.autoTPAModule = new AutoTPAModule());
    this.apply(this.autoClickerModule = new AutoClickerModule());
    this.apply(this.autoToolModule = new AutoToolModule());
    this.apply(this.fastUseModule = new FastUseModule());
    this.apply(this.nameTagsModule = new NameTagsModule());
    this.apply(this.fakePayModule = new FakePayModule());
    this.apply(this.weatherNotifierModule = new WeatherNotifierModule());
    this.apply(this.fakeStatsModule = new FakeStatsModule());
    this.apply(this.fakeLootDropModule = new FakeLootDropModule());
    this.apply(this.armorTrimHiderModule = new ArmorTrimHiderModule());
    this.apply(this.customCrosshairModule = new CustomCrosshairModule());
    this.apply(this.staffListModule = new StaffListModule());
    this.apply(this.antiVanishModule = new AntiVanishModule());
    this.apply(this.customGlintModule = new CustomGlintModule());
    this.apply(this.customFOVModule = new CustomFOVModule());
    this.apply(this.coordSnapperModule = new CoordSnapperModule());
    this.apply(this.autoWalkModule = new AutoWalkModule());
    this.apply(this.zoomModule = new ZoomModule());
    this.apply(this.freeLookModule = new FreeLookModule());
    this.apply(this.spawnerProtectModule = new SpawnerProtectModule());
    this.apply(this.gambleRiggerStateSupport = new GambleRiggerStateSupport());
    this.apply(this.blockESPModule = new BlockESPModule());
    this.apply(this.storageESPModule = new StorageESPModule());
    this.apply(this.blockEntityESPModule = new BlockEntityESPModule());
    this.apply(this.holeESPModule = new HoleESPModule());
    this.apply(this.fullBrightModule = new FullBrightModule());
    this.apply(this.playerESPModule = new PlayerESPModule());
    this.apply(this.mobESPModule = new MobESPModule());
    this.apply(this.spawnerNametagsModule = new SpawnerNametagsModule());
    this.apply(this.spawnerBeamModule = new SpawnerBeamModule());
    this.apply(this.clusterESPModule = new ClusterESPModule());
    this.apply(this.susChunkFinderModule);
    this.apply(this.chunkFinderModule = new ChunkFinderModule());
    this.apply(this.deepscanChunkFinderModule = new DeepscanChunkFinderModule());
    this.apply(this.regionMapModule = new RegionMapModule());
  }

  public void apply(
      String text, String text2, ModuleCategory moduleCategory, Setting[] settingArray) {
    this.apply(new SettingGroupModule(text, text2, moduleCategory, settingArray));
  }

  public void apply(Module module) {
    this.list.add(module);
    ((List) this.map.get(module.getModuleCategory())).add(module);
    module.setStateChangeListener(
        (referenceArgument64_0, referenceArgument64_1) ->
            this.apply(
                (bunnythekenzo.client.module.Module) referenceArgument64_0,
                (java.lang.Boolean) referenceArgument64_1));
  }

  public List getList() {
    return this.list;
  }

  public List getList(ModuleCategory moduleCategory) {
    return (List) this.map.get(moduleCategory);
  }

  public void setRunnable(Runnable runnable2) {
    this.runnable = runnable2;
  }

  public void setBiConsumer(BiConsumer biConsumer2) {
    this.biConsumer = biConsumer2;
  }

  public void apply(Module module, boolean flag) {
    this.biConsumer.accept(module, Boolean.valueOf(flag));
  }

  public boolean test(int intValue) {
    if (PearlEscapeAutomation.test()) {
      return false;
    }

    if (this.clickGUIModule.getKeybindSetting().matches(intValue)) {
      this.runnable.run();
      return true;
    }

    boolean flag = false;

    for (Module module : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.list)) {
      if (module != this.clickGUIModule && module.getKeybindSetting().matches(intValue)) {
        module.toggleFromKeybind();
        flag = true;
      }
    }

    for (Module module2 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.list)) {
      if (module2.isEnabled() && module2.onKeyPress(intValue)) {
        flag = true;
      }
    }

    return flag;
  }

  public boolean test2(int intValue) {
    boolean flag = false;

    for (Module module : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.list)) {
      if (module != this.clickGUIModule
          && module.getKeybindSetting().matches(intValue)
          && module.test2()) {
        flag = true;
      }
    }

    return flag;
  }

  public void update2() {
    if (!PearlEscapeAutomation.test2()) {
      for (Module module : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.list)) {
        if (module.isEnabled() && !module.usesCombatTick()) {
          module.onTick();
        }
      }
    }
  }

  public void update3() {
    this.anchorMacroModule.update4();
    this.autoCrystalModule.update9();
    if (!PearlEscapeAutomation.test2()) {
      for (Module module : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.list)) {
        if (module.isEnabled() && module.usesCombatTick()) {
          module.onTick();
        }
      }
    }
  }
}
