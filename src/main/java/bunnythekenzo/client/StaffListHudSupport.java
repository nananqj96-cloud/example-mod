package bunnythekenzo.client;

public final class StaffListHudSupport {
  public ClientData clientData;
  public final CurveTween curveTween = new CurveTween(220.0F, 0.0F, AnimationCurve.EASE_OUT_CUBIC);

  public StaffListHudSupport(ClientData clientData2) {
    this.clientData = clientData2;
  }
}
