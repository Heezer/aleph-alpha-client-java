package dev.ai4j.alephalpha.completion;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.Client;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class CompletionTest {

  private final Client client = Client
    .builder()
    .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
    .logRequests(true)
    .logResponses(true)
    .build();

  @Test
  void simplePromptWorks() {
    val response = client.complete(CompletionRequest.builder().prompt("An apple a day").build());

    assertThat(response.getCompletions().get(0).getCompletion()).contains("keeps the doctor away");
  }

  @Test
  void extendedPromptWorks() {
    val response = client.complete(
      CompletionRequest
        .builder()
        .model("luminous-supreme-control")
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

    val completion = response.getCompletions().get(0);
    assertThat(completion.getCompletion()).contains("plants");
    assertThat(completion.getRawCompletion()).contains("plants");
  }
}
