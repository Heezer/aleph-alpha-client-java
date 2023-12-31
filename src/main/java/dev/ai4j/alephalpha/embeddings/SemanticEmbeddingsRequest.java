package dev.ai4j.alephalpha.embeddings;

import static dev.ai4j.alephalpha.Models.BASE_MODEL;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsRepresentations.REPRESENTATION_SYMMETRIC;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SemanticEmbeddingsRequest extends SemanticEmbeddingsBaseRequest {

  @NonNull
  private String prompt;
}
