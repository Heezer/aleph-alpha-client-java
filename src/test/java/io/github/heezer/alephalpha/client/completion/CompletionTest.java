package io.github.heezer.alephalpha.client.completion;

import static io.github.heezer.alephalpha.client.Models.SUPREME_CONTROL_MODEL;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.heezer.alephalpha.client.BaseTest;
import io.github.heezer.alephalpha.client.prompt.MultimodalImage;
import io.github.heezer.alephalpha.client.prompt.MultimodalText;
import io.github.heezer.alephalpha.client.prompt.MultimodalTokenIds;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

class CompletionTest extends BaseTest {

  @Test
  void simplePromptWorks() {
    val response = client.complete(CompletionRequest.builder().prompt("An apple a day").build());

    assertThat(response).isNotNull().extracting(CompletionResponse::getCompletions).isNotNull();
    assertThat(response.getCompletions())
      .isNotEmpty()
      .first()
      .extracting(CompletionResponse.Completion::getCompletion)
      .asString()
      .contains("keeps the doctor away");
  }

  @Test
  void simplePromptWithNiceFlagWorks() {
    val response = client.complete(TRUE, CompletionRequest.builder().prompt("An apple a day").build());

    assertThat(response).isNotNull().extracting(CompletionResponse::getCompletions).isNotNull();
    assertThat(response.getCompletions())
      .isNotEmpty()
      .first()
      .extracting(CompletionResponse.Completion::getCompletion)
      .asString()
      .contains("keeps the doctor away");
  }

  @Test
  void extendedPromptWorks() {
    val response = client.complete(
      CompletionRequest
        .builder()
        .model(SUPREME_CONTROL_MODEL)
        .prompt(
          "### Instruction:\n" +
          "Identify the topic of the text.\n" +
          "Reply like this: \"The topic is: <topic>\"\n" +
          "\n" +
          "### Input:\n" +
          "Flowering plants grow fruits to spread their seeds. Edible fruits function symbiotically. " +
          "Animals obtain nourishment from consuming the fruit and the animal's movement helps spread the seed.\n" +
          "\n" +
          "### Response:"
        )
        .maximumTokens(16)
        .rawCompletion(true)
        .build()
    );

    assertThat(response).isNotNull().extracting(CompletionResponse::getCompletions).isNotNull().asList().isNotEmpty();

    val completion = response.getCompletions().get(0);
    assertThat(completion.getCompletion()).contains("plants");
    assertThat(completion.getRawCompletion()).contains("plants");
  }

  @Test
  void multimodalTextPromptWorks() {
    val response = client.complete(
      CompletionRequest
        .builder()
        .prompt(
          Collections.singletonList(
            MultimodalText
              .builder()
              .data("an apple")
              .controls(
                Collections.singletonList(
                  MultimodalText.Control.builder().start(3).length(5).factor(0.1f).tokenOverlap("complete").build()
                )
              )
              .build()
          )
        )
        .build()
    );

    assertThat(response).isNotNull().extracting(CompletionResponse::getCompletions).isNotNull();
    assertThat(response.getCompletions())
      .isNotEmpty()
      .first()
      .extracting(CompletionResponse.Completion::getCompletion)
      .asString()
      .contains("a pear");
  }

  @Test
  @SneakyThrows
  void multimodalImagePromptWorks() {
    val base64Image = Base64
      .getEncoder()
      .encodeToString(
        Files.readAllBytes(
          Paths.get(
            Objects
              .requireNonNull(Thread.currentThread().getContextClassLoader().getResource("images/bird.png"))
              .toURI()
          )
        )
      );

    val response = client.complete(
      CompletionRequest
        .builder()
        .prompt(Collections.singletonList(MultimodalImage.builder().data(base64Image).build()))
        .build()
    );

    assertThat(response).isNotNull().extracting(CompletionResponse::getCompletions).isNotNull();
    assertThat(response.getCompletions())
      .isNotEmpty()
      .first()
      .extracting(CompletionResponse.Completion::getCompletion)
      .asString()
      .contains("black and white silhouette of a bird");
  }

  @Test
  void multimodalTokenIdsPromptWorks() {
    val response = client.complete(
      CompletionRequest
        .builder()
        .prompt(Collections.singletonList(MultimodalTokenIds.builder().data(Collections.singletonList(1)).build()))
        .build()
    );

    assertThat(response).isNotNull().extracting(CompletionResponse::getCompletions).isNotNull();
    assertThat(response.getCompletions())
      .isNotEmpty()
      .first()
      .extracting(CompletionResponse.Completion::getCompletion)
      .isNotNull();
  }
}
