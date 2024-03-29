package io.github.heezer.alephalpha.client.explanation;

import static io.github.heezer.alephalpha.client.Models.BASE_MODEL;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ExplanationRequest {

  @Builder.Default
  private String model = BASE_MODEL;

  private String hosting;

  @NonNull
  private Object prompt;

  @NonNull
  private String target;

  private Double controlFactor;
  private Double contextualControlThreshold;
  private Boolean controlLogAdditive;
  private String postprocessing;
  private String normalize;
  private PromptGranularity promptGranularity;
  private String targetGranularity;
  private String controlTakenOverlap;

  @Data
  @Builder
  public static class PromptGranularity {

    private String type;
    private String delimiter;
  }
}
