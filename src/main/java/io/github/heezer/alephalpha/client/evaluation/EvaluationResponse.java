package io.github.heezer.alephalpha.client.evaluation;

import lombok.Data;

@Data
public class EvaluationResponse {

  private String modelVersion;
  private Result result;

  @Data
  public static class Result {

    private Double logProbability;
    private Double logPerplexity;
    private Double logPerplexityPerToken;
    private Double logPerplexityPerCharacter;
    private Boolean correctGreedy;
    private Integer tokenCount;
    private Integer characterCount;
    private String completion;
  }
}
