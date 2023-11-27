package dev.ai4j.alephalpha.embeddings;

import static dev.ai4j.alephalpha.embeddings.EmbeddingsLayers.INPUT_LAYER_MINUS_ONE;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsLayers.OUTPUT_LAYER_MINUS_ONE;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsPoolingOperations.POOLING_MAX;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsPoolingOperations.POOLING_WEIGHTED_MEAN;
import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.Client;
import java.util.Arrays;
import java.util.Collections;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
public class EmbeddingsTest {

  private static final String DEFAULT_PROMPT = "An apple a day keeps the doctor away.";

  private final Client client = Client
    .builder()
    .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
    .logRequests(true)
    .logResponses(true)
    .build();

  @Test
  void embeddingWithTwoPoolingsWorks() {
    val response = client.embed(
      EmbeddingsRequest
        .builder()
        .prompt(DEFAULT_PROMPT)
        .layers(Collections.singletonList(INPUT_LAYER_MINUS_ONE))
        .pooling(Arrays.asList(POOLING_MAX, POOLING_WEIGHTED_MEAN))
        .build()
    );

    val embeddings = response.getEmbeddings();
    val layer = embeddings.get(OUTPUT_LAYER_MINUS_ONE);

    assertThat(layer).isNotNull();
    assertThat(layer.get(POOLING_MAX)).isNotNull().hasSize(5120);
    assertThat(layer.get(POOLING_WEIGHTED_MEAN)).isNotNull().hasSize(5120);
  }

  @Test
  void simpleSemanticEmbeddingWorks() {
    val response = client.semanticEmbed(
      SemanticEmbeddingsRequest.builder().prompt(DEFAULT_PROMPT).compressToSize(128).build()
    );

    assertThat(response)
      .isNotNull()
      .extracting(SemanticEmbeddingsResponse::getEmbedding)
      .isNotNull()
      .asList()
      .hasSize(128);
  }
}
