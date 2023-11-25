package dev.ai4j.alephalpha.completion;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompletionRequest {

  private String model;
  private String prompt;
  private int maximum_tokens;
}
