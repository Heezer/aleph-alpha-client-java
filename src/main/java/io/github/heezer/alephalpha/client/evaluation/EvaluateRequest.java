package io.github.heezer.alephalpha.evaluate;

import static io.github.heezer.alephalpha.Models.BASE_MODEL;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class EvaluateRequest {

  @Builder.Default
  private String model = BASE_MODEL;

  private String hosting;

  @NonNull
  private Object prompt;

  @NonNull
  private String completionExpected;

  private Integer contextualControlThreshold;
  private Boolean controlLogAdditive;
}
