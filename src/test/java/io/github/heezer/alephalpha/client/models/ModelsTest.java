package io.github.heezer.alephalpha.client.models;

import static io.github.heezer.alephalpha.client.Models.BASE_MODEL;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.heezer.alephalpha.client.BaseTest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

@Slf4j
public class ModelsTest extends BaseTest {

  @Test
  void receivingAvailableModelsWorks() {
    val response = client.models();

    log.info("Available models: {}", response);
    assertThat(response).isNotNull().hasSizeGreaterThan(5);
  }

  @Test
  void receivingTokenizerThatWasUsedToTrainModelWorks() {
    val response = client.tokenizerForModel(BASE_MODEL);

    log.info("Tokenizer that was used to train models {}: {}", BASE_MODEL, response);
    assertThat(response).isNotNull();
  }
}
