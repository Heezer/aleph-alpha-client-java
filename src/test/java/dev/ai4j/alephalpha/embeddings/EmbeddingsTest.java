package dev.ai4j.alephalpha.embeddings;

import dev.ai4j.alephalpha.Client;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsLayers.INPUT_LAYER_MINUS_ONE;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsLayers.OUTPUT_LAYER_MINUS_ONE;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsPoolingOperations.POOLING_MAX;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsPoolingOperations.POOLING_WEIGHTED_MEAN;
import lombok.val;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

@Disabled("Cannot be executed automatically because of the API key")
public class EmbeddingsTest {

  private static final String DEFAULT_PROMPT = "An apple a day keeps the doctor away.";

  private final Client client = Client
    .builder()
    .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
    .logRequests()
    .logResponses()
    .build();

  @Test
  void embeddingWithTwoPoolingsWorks() {
    val response = client.embed(
      null,
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

  @Test
  void batchedSemanticEmbeddingWorks() {
    val response = client.batchSemanticEmbed(
      BatchedSemanticEmbeddingsRequest
        .builder()
        .prompts(Arrays.asList(DEFAULT_PROMPT, "Something else"))
        .compressToSize(128)
        .normalize(true)
        .build()
    );

    assertThat(response)
      .isNotNull()
      .extracting(BatchedSemanticEmbeddingsResponse::getEmbeddings)
      .isNotNull()
      .asList()
      .hasSize(2)
      .first()
      .asList()
      .hasSize(128);
  }
}
