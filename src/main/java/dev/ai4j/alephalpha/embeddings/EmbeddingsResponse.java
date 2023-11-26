package dev.ai4j.alephalpha.embeddings;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class EmbeddingsResponse {

  private String modelVersion;
  private Map<String, Map<String, List<Float>>> embeddings;
  private String[] tokens;
}
