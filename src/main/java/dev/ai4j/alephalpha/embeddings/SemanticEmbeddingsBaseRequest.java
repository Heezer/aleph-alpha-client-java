package dev.ai4j.alephalpha.embeddings;

import static dev.ai4j.alephalpha.Models.BASE_MODEL;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsRepresentations.REPRESENTATION_SYMMETRIC;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
abstract class SemanticEmbeddingsBaseRequest {

  @Builder.Default
  private String model = BASE_MODEL;

  private String hosting;

  @Builder.Default
  private String representation = REPRESENTATION_SYMMETRIC;

  private Integer compressToSize;
  private Boolean normalize;
  private Double contextualControlThreshold;
  private Boolean controlLogAdditive;
}
