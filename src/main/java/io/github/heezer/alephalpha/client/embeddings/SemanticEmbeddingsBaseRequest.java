package io.github.heezer.alephalpha.embeddings;

import static io.github.heezer.alephalpha.Models.BASE_MODEL;
import static io.github.heezer.alephalpha.embeddings.EmbeddingsRepresentations.REPRESENTATION_SYMMETRIC;

import lombok.Builder;
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
