package bunnythekenzo.client.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientInitializer;

public final class SettingCodec {
  public static void apply(JsonObject jsonObject, SettingEntry settingEntry) {
    if (settingEntry.isBound()) {
      return;
    }
    SettingEntry settingEntry2 = settingEntry;
    Objects.requireNonNull(settingEntry2);
    SettingEntry settingEntry3 = settingEntry2;
    switch (bunnythekenzo.client.runtime.TypeDispatch.select(
        new Class<?>[] {
          StringSettingView.class,
          ToggleSettingView.class,
          NumberSettingView.class,
          ChoiceSettingView.class,
          ColorSettingView.class,
          KeybindSettingView.class,
          BlockListSettingView.class,
          EntityTypeSettingView.class,
          ItemSettingView.class,
          GlintStyleSettingView.class,
          ItemReplacementSettingView.class
        },
        (Object) settingEntry3,
        0)) {
      default:
        {
          throw new MatchException(null, null);
        }
      case 0:
        {
          StringSettingView toggleSettingViewSupport4 = (StringSettingView) settingEntry3;
          jsonObject.addProperty(
              toggleSettingViewSupport4.getName(), toggleSettingViewSupport4.getValue());
          break;
        }
      case 1:
        {
          ToggleSettingView toggleSettingView = (ToggleSettingView) settingEntry3;
          jsonObject.addProperty(toggleSettingView.getName(), toggleSettingView.getValue());
          break;
        }
      case 2:
        {
          NumberSettingView numberSettingView = (NumberSettingView) settingEntry3;
          jsonObject.addProperty(
              numberSettingView.getName(), Float.valueOf(numberSettingView.getValue()));
          break;
        }
      case 3:
        {
          ChoiceSettingView choiceSettingView = (ChoiceSettingView) settingEntry3;
          jsonObject.addProperty(choiceSettingView.getName(), choiceSettingView.getSelectedValue());
          break;
        }
      case 4:
        {
          ColorSettingView toggleSettingViewSupport2 = (ColorSettingView) settingEntry3;
          jsonObject.addProperty(
              toggleSettingViewSupport2.getName(),
              ArgbColors.toHex(toggleSettingViewSupport2.computeInt()));
          break;
        }
      case 5:
        {
          KeybindSettingView toggleSettingViewSupport = (KeybindSettingView) settingEntry3;
          jsonObject.addProperty(
              toggleSettingViewSupport.getName(), toggleSettingViewSupport.computeInt());
          break;
        }
      case 6:
        {
          break;
        }
      case 7:
        {
          break;
        }
      case 8:
        {
          break;
        }
      case 9:
        {
          break;
        }
      case 10:
        {
        }
    }
  }

  public static void apply2(JsonObject jsonObject, SettingEntry settingEntry) {
    if (settingEntry.isBound() || jsonObject == null || !jsonObject.has(settingEntry.getName())) {
      return;
    }
    JsonElement jsonElement = jsonObject.get(settingEntry.getName());
    try {
      SettingEntry settingEntry2 = settingEntry;
      Objects.requireNonNull(settingEntry2);
      SettingEntry settingEntry3 = settingEntry2;
      switch (bunnythekenzo.client.runtime.TypeDispatch.select(
          new Class<?>[] {
            StringSettingView.class,
            ToggleSettingView.class,
            NumberSettingView.class,
            ChoiceSettingView.class,
            ColorSettingView.class,
            KeybindSettingView.class,
            BlockListSettingView.class,
            EntityTypeSettingView.class,
            ItemSettingView.class,
            GlintStyleSettingView.class,
            ItemReplacementSettingView.class
          },
          (Object) settingEntry3,
          0)) {
        default:
          {
            throw new MatchException(null, null);
          }
        case 0:
          {
            StringSettingView toggleSettingViewSupport4 = (StringSettingView) settingEntry3;
            toggleSettingViewSupport4.setValue(jsonElement.getAsString());
            break;
          }
        case 1:
          {
            ToggleSettingView toggleSettingView = (ToggleSettingView) settingEntry3;
            toggleSettingView.setValue(jsonElement.getAsBoolean());
            break;
          }
        case 2:
          {
            NumberSettingView numberSettingView = (NumberSettingView) settingEntry3;
            numberSettingView.setValue(jsonElement.getAsFloat());
            break;
          }
        case 3:
          {
            ChoiceSettingView choiceSettingView = (ChoiceSettingView) settingEntry3;
            int n = choiceSettingView.getChoices().indexOf(jsonElement.getAsString());
            if (n >= 0) {
              choiceSettingView.selectIndex(n);
            }
            break;
          }
        case 4:
          {
            ColorSettingView toggleSettingViewSupport2 = (ColorSettingView) settingEntry3;
            toggleSettingViewSupport2.apply(
                ArgbColors.parseHexOrDefault(
                    jsonElement.getAsString(), toggleSettingViewSupport2.getCount()));
            break;
          }
        case 5:
          {
            KeybindSettingView toggleSettingViewSupport = (KeybindSettingView) settingEntry3;
            toggleSettingViewSupport.apply(jsonElement.getAsInt());
            break;
          }
        case 6:
          {
            break;
          }
        case 7:
          {
            break;
          }
        case 8:
          {
            break;
          }
        case 9:
          {
            break;
          }
        case 10:
          {
          }
      }
    } catch (RuntimeException runtimeException) {
      ClientInitializer.logger.warn(
          "Ignoring the unreadable saved value for '{}'", (Object) settingEntry.getName());
    }
  }

  public static JsonObject getJsonObject(Iterable iterable) {
    JsonObject jsonObject2 = new JsonObject();

    for (SettingEntry settingEntry4 :
        (java.lang.Iterable<SettingEntry>) (java.lang.Iterable<?>) (iterable)) {
      apply(jsonObject2, settingEntry4);
    }

    return jsonObject2;
  }

  public static void apply(JsonObject jsonObject2, Iterable iterable) {
    if (jsonObject2 != null) {
      for (SettingEntry settingEntry4 :
          (java.lang.Iterable<SettingEntry>) (java.lang.Iterable<?>) (iterable)) {
        apply2(jsonObject2, settingEntry4);
      }
    }
  }
}
