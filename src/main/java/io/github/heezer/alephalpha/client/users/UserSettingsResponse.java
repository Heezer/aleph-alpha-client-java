package io.github.heezer.alephalpha.client.users;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserSettingsResponse {

  @NonNull
  private Integer id;

  @NonNull
  private String email;

  @NonNull
  private String role;

  @NonNull
  private Double creditsRemaining;

  @NonNull
  private Boolean invoiceAllowed;

  @NonNull
  private Double outOfCreditsThreshold;

  @NonNull
  private String termsOfServiceVersion;
}
