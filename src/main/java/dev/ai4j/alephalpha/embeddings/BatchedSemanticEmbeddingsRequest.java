package dev.ai4j.alephalpha.embeddings;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class BatchedSemanticEmbeddingsRequest extends SemanticEmbeddingsBaseRequest {

  @NonNull
  private List<Object> prompts;
}
