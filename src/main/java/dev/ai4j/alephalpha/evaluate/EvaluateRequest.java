package dev.ai4j.alephalpha.evaluate;

import static dev.ai4j.alephalpha.Models.BASE_MODEL;

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
