package io.github.heezer.alephalpha.tokenize;

import static io.github.heezer.alephalpha.Models.BASE_MODEL;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class TokenizationRequest {

  @Builder.Default
  private String model = BASE_MODEL;

  @NonNull
  private String prompt;

  private Boolean tokens;
  private Boolean tokenIds;
}
