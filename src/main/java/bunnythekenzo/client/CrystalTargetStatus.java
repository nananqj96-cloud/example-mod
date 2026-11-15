package bunnythekenzo.client;

public enum CrystalTargetStatus {
  READY("Target ready"),
  NO_PLAYER("No living player target"),
  OUT_OF_RANGE("Player outside Target Range"),
  OUT_OF_BLAST_RANGE("Damage Check: player outside blast radius"),
  IMMUNE("Damage Check: player appears immune"),
  COVERED("Damage Check: player fully covered from crystal"),
  PEACEFUL("Damage Check: server reports Peaceful");

  private final String description;

  CrystalTargetStatus(String description2) {
    this.description = description2;
  }

  public String getDescription() {
    return this.description;
  }
}
