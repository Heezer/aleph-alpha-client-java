package dev.ai4j.alephalpha.completion;

import dev.ai4j.alephalpha.Client;
import static dev.ai4j.alephalpha.Models.SUPREME_CONTROL_MODEL;
import static java.lang.Boolean.TRUE;
import lombok.val;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
class CompletionTest {

  private final Client client = Client
    .builder()
    .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
    .logRequests()
    .logResponses()
    .build();

  @Test
  void simplePromptWorks() {
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
}
