package io.github.heezer.alephalpha.client.evaluation;

import static io.github.heezer.alephalpha.client.Models.BASE_MODEL;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class EvaluationRequest {

  @Builder.Default
  private String model = BASE_MODEL;

  private String hosting;

  @NonNull
  private Object prompt;

  @NonNull
  private String completionExpected;

  private Double contextualControlThreshold;
  private Boolean controlLogAdditive;
}
