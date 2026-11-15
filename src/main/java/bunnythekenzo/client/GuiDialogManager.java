package bunnythekenzo.client;

import bunnythekenzo.client.setting.ItemReplacementSetting;
import bunnythekenzo.client.ui.GalleryModel;
import bunnythekenzo.client.ui.ItemReplacementDialog;
import bunnythekenzo.client.ui.SelectionDialog;

public final class GuiDialogManager {
  public static GuiModal currentDialog;
  public static GuiModal parentDialog;
  public static Runnable onReturn;

  public static void open(GalleryModel galleryModel) {
    closeAll();
    currentDialog = new SelectionDialog(galleryModel);
  }

  public static void open(ItemReplacementSetting itemReplacementSetting) {
    closeAll();
    currentDialog = new ItemReplacementDialog(itemReplacementSetting);
  }

  public static void open(GalleryModel galleryModel, Runnable runnable2) {
    parentDialog = currentDialog;
    onReturn = runnable2;
    currentDialog = new SelectionDialog(galleryModel);
  }

  public static GuiModal getCurrentDialog() {
    return currentDialog;
  }

  public static SelectionDialog getSelectionDialog() {
    return currentDialog instanceof SelectionDialog selectionDialog ? selectionDialog : null;
  }

  public static boolean hasDialog() {
    return currentDialog != null;
  }

  public static void finishClosedDialog() {
    if (currentDialog != null && currentDialog.isCloseComplete()) {
      currentDialog.onClosed();
      currentDialog = parentDialog;
      parentDialog = null;
      Runnable runnable2 = onReturn;
      onReturn = null;
      if (runnable2 != null) {
        runnable2.run();
      }
    }
  }

  public static void closeAll() {
    if (currentDialog != null) {
      currentDialog.onClosed();
    }

    if (parentDialog != null) {
      parentDialog.onClosed();
    }

    currentDialog = null;
    parentDialog = null;
    onReturn = null;
  }
}
