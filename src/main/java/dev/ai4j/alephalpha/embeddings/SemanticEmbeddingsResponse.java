package dev.ai4j.alephalpha.embeddings;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class SemanticEmbeddingsResponse {

  private String modelVersion;
  private List<Float> embedding;
}
