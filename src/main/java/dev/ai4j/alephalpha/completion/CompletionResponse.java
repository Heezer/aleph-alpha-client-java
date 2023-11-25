package dev.ai4j.alephalpha.completion;

import java.util.List;
import lombok.Data;

@Data
public class CompletionResponse {

  private String modelVersion;
  private List<Completion> completions;

  @Data
  public static class Completion {

    private String completion;
    private String finish_reason;
  }
}
