package bunnythekenzo.client;

import net.minecraft.client.render.state.OutlineRenderState;
import net.minecraft.util.hit.BlockHitResult;
import bunnythekenzo.client.module.CustomBlockOutlineModule;

public final class BlockOutlineTransition {
  public AnimatedBlockOutline animatedBlockOutline;
  public AnimatedBlockOutline outlineDirectionSwitchTable;
  public Object object;
  public long timestamp;
  public double value;

  public AnimatedBlockOutline getAnimatedBlockOutline() {
    return this.animatedBlockOutline;
  }

  public AnimatedBlockOutline getOutlineDirectionSwitchTable() {
    return this.outlineDirectionSwitchTable;
  }

  public double getValue() {
    return this.value;
  }

  public void update() {
    this.animatedBlockOutline = this.outlineDirectionSwitchTable = null;
    this.object = null;
    this.timestamp = 0L;
    this.value = 0.0;
  }

  public void apply(
      OutlineRenderState outlineRenderState,
      BlockHitResult blockHitResult,
      Object object2,
      long timestamp2,
      CustomBlockOutlineModule customBlockOutlineModule) {
    if (this.object != object2) {
      this.update();
      this.object = object2;
    }

    double doubleValue =
        this.timestamp == 0L
            ? 0.016666666666666666
            : Math.clamp((timestamp2 - this.timestamp) / 1.0E9, 0.0, 0.1);
    this.timestamp = timestamp2;
    boolean flag =
        AnimationPreferences.isEnabled()
            && !customBlockOutlineModule.animationSetting.isSelected("Static")
            && (Double) customBlockOutlineModule.animationSpeedSetting.getValue() > 0.0;
    boolean flag2 = flag && (Boolean) customBlockOutlineModule.transitionsSetting.getValue();
    if (flag) {
      this.value =
          this.value
              + doubleValue * (Double) customBlockOutlineModule.animationSpeedSetting.getValue();
    }

    if (outlineRenderState != null && !outlineRenderState.shape().isEmpty()) {
      if (this.animatedBlockOutline == null
          || !this.animatedBlockOutline.blockPos.equals(outlineRenderState.pos())) {
        AnimatedBlockOutline blockOutlineTransition =
            this.outlineDirectionSwitchTable != null
                    && this.outlineDirectionSwitchTable.blockPos.equals(outlineRenderState.pos())
                ? this.outlineDirectionSwitchTable
                : null;
        this.outlineDirectionSwitchTable = this.animatedBlockOutline;
        this.animatedBlockOutline =
            blockOutlineTransition == null
                ? new AnimatedBlockOutline(outlineRenderState)
                : blockOutlineTransition;
      }

      this.animatedBlockOutline.apply(outlineRenderState, blockHitResult);
      this.animatedBlockOutline.value =
          flag2 ? Math.min(1.0, this.animatedBlockOutline.value + doubleValue / 0.2) : 1.0;
    } else if (this.animatedBlockOutline != null) {
      this.outlineDirectionSwitchTable = this.animatedBlockOutline;
      this.animatedBlockOutline = null;
    }

    if (this.outlineDirectionSwitchTable != null) {
      this.outlineDirectionSwitchTable.value =
          flag2 ? Math.max(0.0, this.outlineDirectionSwitchTable.value - doubleValue / 0.16) : 0.0;
      if (this.outlineDirectionSwitchTable.value <= 0.0) {
        this.outlineDirectionSwitchTable = null;
      }
    }
  }
}
