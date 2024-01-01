package dev.ai4j.alephalpha.evaluate;

import lombok.Data;

@Data
public class EvaluateResponse {

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
