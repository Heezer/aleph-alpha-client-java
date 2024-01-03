package io.github.heezer.alephalpha.embeddings;

import java.util.List;
import lombok.Data;

@Data
public class BatchedSemanticEmbeddingsResponse {

  private String modelVersion;
  private List<List<Float>> embeddings;
}
