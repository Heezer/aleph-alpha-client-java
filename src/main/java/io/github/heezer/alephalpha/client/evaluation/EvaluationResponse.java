package io.github.heezer.alephalpha.client.evaluation;

import lombok.Data;

@Data
public class EvaluationResponse {

  private String modelVersion;
  private Result result;

  @Data
  public static class Result {

    private Float logProbability;
    private Float logPerplexity;
    private Float logPerplexityPerToken;
    private Float logPerplexityPerCharacter;
    private Boolean correctGreedy;
    private Integer tokenCount;
    private Integer characterCount;
    private String completion;
  }
}
