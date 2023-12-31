package io.github.heezer.alephalpha.client.embeddings;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class SemanticEmbeddingsResponse {

  private String modelVersion;
  private List<Float> embedding;
}
