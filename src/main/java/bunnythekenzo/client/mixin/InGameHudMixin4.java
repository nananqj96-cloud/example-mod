package bunnythekenzo.client.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.FakeStatsModule;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(InGameHud.class)
public class InGameHudMixin4 {
  @Inject(
      method =
          "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V",
      at = @At("HEAD"))
  public void apply(
      DrawContext drawContext, ScoreboardObjective scoreboardObjective, CallbackInfo callbackInfo) {
    FakeStatsModule fakeStatsModule = getFakeStatsModule();
    if (fakeStatsModule != null) {
      fakeStatsModule.update5();
    }
  }

  @Redirect(
      method =
          "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/font/TextRenderer;getWidth(Lnet/minecraft/text/StringVisitable;)I",
              ordinal = 0))
  public int computeInt(TextRenderer textRenderer, StringVisitable stringVisitable) {
    return stringVisitable instanceof Text text
        ? textRenderer.getWidth(getText3(text))
        : textRenderer.getWidth(stringVisitable);
  }

  @Redirect(
      method =
          "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/font/TextRenderer;getWidth(Lnet/minecraft/text/StringVisitable;)I",
              ordinal = 1))
  public int computeInt2(TextRenderer textRenderer, StringVisitable stringVisitable) {
    if (stringVisitable instanceof Text text) {
      try {
        return textRenderer.getWidth(getText(text, true));
      } catch (Exception failure) {
      }
    }

    return textRenderer.getWidth(stringVisitable);
  }

  @ModifyArg(
      method =
          "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)V",
              ordinal = 0),
      index = 1)
  public Text getText(Text text) {
    return getText3(text);
  }

  @ModifyArg(
      method =
          "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)V",
              ordinal = 1),
      index = 1)
  public Text getText2(Text text) {
    try {
      return getText(text, false);
    } catch (Exception failure) {
      return text;
    }
  }

  private static Text getText(Text text, boolean flag) {
    Text text2 = text;
    FakeStatsModule fakeStatsModule = getFakeStatsModule();
    if (fakeStatsModule != null) {
      text2 = flag ? fakeStatsModule.getText(text2) : fakeStatsModule.getText2(text2);
    }

    return getText3(text2);
  }

  private static Text getText3(Text text) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null
            && moduleManager.nameProtectModule != null
            && moduleManager.nameProtectModule.isEnabled()
        ? moduleManager.nameProtectModule.getText(text)
        : text;
  }

  private static FakeStatsModule getFakeStatsModule() {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null
            && moduleManager.fakeStatsModule != null
            && moduleManager.fakeStatsModule.isEnabled()
        ? moduleManager.fakeStatsModule
        : null;
  }
}
