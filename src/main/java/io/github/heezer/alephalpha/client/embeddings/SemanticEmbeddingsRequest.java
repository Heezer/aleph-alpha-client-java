package io.github.heezer.alephalpha.embeddings;

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
  private Object prompt;
}
