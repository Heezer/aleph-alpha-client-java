package dev.ai4j.alephalpha.embeddings;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmbeddingsPoolingOperations {

  public static final String POOLING_MEAN = "mean";
  public static final String POOLING_WEIGHTED_MEAN = "weighted_mean";
  public static final String POOLING_MAX = "max";
  public static final String POOLING_LAST_TOKEN = "last_token";
  public static final String POOLING_ABS_MAX = "abs_max";
}
