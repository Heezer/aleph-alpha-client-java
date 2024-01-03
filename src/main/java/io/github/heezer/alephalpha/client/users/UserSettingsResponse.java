package io.github.heezer.alephalpha.users;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class UserSettingsResponse {

  @NotNull
  private Integer id;

  @NotNull
  private String email;

  @NotNull
  private String role;

  @NotNull
  private Float creditsRemaining;

  @NotNull
  private Boolean invoiceAllowed;

  @NotNull
  private Float outOfCreditsThreshold;

  @NotNull
  private String termsOfServiceVersion;
}
