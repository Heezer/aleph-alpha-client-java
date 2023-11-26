package dev.ai4j.alephalpha.embeddings;

import static dev.ai4j.alephalpha.Models.BASE_MODEL;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class EmbeddingsRequest {

  @Builder.Default
  private String model = BASE_MODEL;

  private String hosting;

  @NonNull
  private String prompt;

  @NonNull
  private List<Integer> layers;

  private Boolean tokens;

  @NonNull
  private List<String> pooling;

  private String type;
  private Boolean normalize;
  private Double contextualControlThreshold;
  private Boolean controlLogAdditive;
}
