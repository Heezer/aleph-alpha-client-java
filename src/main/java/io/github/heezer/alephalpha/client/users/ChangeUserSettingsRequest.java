package io.github.heezer.alephalpha.client.users;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ChangeUserSettingsRequest {

  @NonNull
  private Integer outOfCreditsThreshold;
}
