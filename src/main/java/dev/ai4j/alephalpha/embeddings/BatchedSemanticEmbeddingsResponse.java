package dev.ai4j.alephalpha.embeddings;

import lombok.Data;

import java.util.List;

@Data
public class BatchedSemanticEmbeddingsResponse {

  private String modelVersion;
  private List<List<Float>> embeddings;
}
