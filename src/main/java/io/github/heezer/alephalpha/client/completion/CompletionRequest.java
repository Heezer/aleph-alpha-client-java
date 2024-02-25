package io.github.heezer.alephalpha.client.completion;

import static io.github.heezer.alephalpha.client.Models.BASE_MODEL;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CompletionRequest {

  @Builder.Default
  private String model = BASE_MODEL;

  private String hosting;

  @NonNull
  private Object prompt;

  @Builder.Default
  private Integer maximumTokens = 32;

  private Integer minimumTokens;
  private Boolean echo;
  private Double temperature;
  private Integer topK;
  private Double topP;
  private Double presencePenalty;
  private Double frequencyPenalty;
  private Double sequencePenalty;
  private Integer sequencePenaltyMinLength;
  private Boolean repetitionPenaltiesIncludePrompt;
  private Boolean repetitionPenaltiesIncludeCompletion;
  private Boolean useMultiplicativePresencePenalty;
  private Boolean useMultiplicativeFrequencyPenalty;
  private Boolean useMultiplicativeSequencePenalty;
  private String penaltyBias;
  private List<String> penaltyExceptions;
  private Boolean penaltyExceptionsIncludeStopSequences;
  private Integer bestOf;
  private Integer n;
  private Object logitBias;
  private Integer logProbs;
  private List<String> stopSequences;
  private Boolean tokens;
  private Boolean rawCompletion;
  private Boolean disableOptimizations;
  private List<String> completionBiasInclusion;
  private Boolean completionBiasInclusionFirstTokenOnly;
  private List<String> completionBiasExclusion;
  private Boolean completionBiasExclusionFirstTokenOnly;
  private Double contextualControlThreshold;
  private Boolean controlLogAdditive;
}
