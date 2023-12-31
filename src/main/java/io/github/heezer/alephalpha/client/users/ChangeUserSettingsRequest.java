package io.github.heezer.alephalpha.client.users;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class ChangeUserSettingsRequest {

  @NotNull
  private Integer outOfCreditsThreshold;
}
