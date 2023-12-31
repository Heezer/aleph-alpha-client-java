package dev.ai4j.alephalpha.embeddings;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class BatchedSemanticEmbeddingsRequest extends SemanticEmbeddingsBaseRequest {

  @NonNull
  private List<String> prompts;
}
