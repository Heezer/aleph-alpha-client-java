package dev.ai4j.alephalpha.completion;

import java.util.List;
import lombok.Data;

@Data
public class CompletionResponse {

  private String modelVersion;
  private List<Completion> completions;
  private List<Object> optimizedPrompt;
  private Integer numTokensPromptTotal;
  private Integer numTokensGenerated;

  @Data
  public static class Completion {

    private String logProbs;
    private String completion;
    private String rawCompletion;
    private String[] completionTokens;
    private String finishReason;
  }
}
