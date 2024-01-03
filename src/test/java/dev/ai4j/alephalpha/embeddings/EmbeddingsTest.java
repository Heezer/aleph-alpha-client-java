package dev.ai4j.alephalpha.embeddings;

import static dev.ai4j.alephalpha.embeddings.EmbeddingsLayers.INPUT_LAYER_MINUS_ONE;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsLayers.OUTPUT_LAYER_MINUS_ONE;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsPoolingOperations.POOLING_LAST_TOKEN;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsPoolingOperations.POOLING_MAX;
import static dev.ai4j.alephalpha.embeddings.EmbeddingsPoolingOperations.POOLING_WEIGHTED_MEAN;
import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.BaseTest;
import dev.ai4j.alephalpha.prompt.MultimodalText;
import java.util.Arrays;
import java.util.Collections;
import lombok.val;
import org.junit.jupiter.api.Test;

public class EmbeddingsTest extends BaseTest {

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

  @Test
  void embeddingWithMultimodalTextPromptWorks() {
    val response = client.embed(
      EmbeddingsRequest
        .builder()
        .prompt(Collections.singletonList(MultimodalText.builder().data(DEFAULT_PROMPT).build()))
        .layers(Collections.singletonList(INPUT_LAYER_MINUS_ONE))
        .pooling(Collections.singletonList(POOLING_LAST_TOKEN))
        .build()
    );

    assertThat(response).isNotNull().extracting(EmbeddingsResponse::getEmbeddings).isNotNull();
  }

  @Test
  void semanticEmbeddingWithMultimodalTextPromptWorks() {
    val response = client.semanticEmbed(
      SemanticEmbeddingsRequest
        .builder()
        .prompt(Collections.singletonList(MultimodalText.builder().data(DEFAULT_PROMPT).build()))
        .compressToSize(128)
        .build()
    );

    assertThat(response)
      .isNotNull()
      .extracting(SemanticEmbeddingsResponse::getEmbedding)
      .isNotNull()
      .asList()
      .hasSize(128);
  }

  @Test
  void batchedSemanticEmbeddingWithTextPromptWorks() {
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

  @Test
  void batchedSemanticEmbeddingWithMultimodalTextWorks() {
    val response = client.batchSemanticEmbed(
      BatchedSemanticEmbeddingsRequest
        .builder()
        .prompts(
          Collections.singletonList(Collections.singletonList(MultimodalText.builder().data(DEFAULT_PROMPT).build()))
        )
        .compressToSize(128)
        .normalize(true)
        .build()
    );

    assertThat(response).isNotNull().extracting(BatchedSemanticEmbeddingsResponse::getEmbeddings).isNotNull();
  }
}
