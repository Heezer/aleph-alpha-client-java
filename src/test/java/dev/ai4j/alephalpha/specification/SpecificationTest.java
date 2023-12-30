package dev.ai4j.alephalpha.specification;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.Client;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
@Slf4j
class SpecificationTest {

  private final Client client = Client.builder().apiKey(System.getenv("ALEPH_ALPHA_API_KEY")).build();

  @Test
  void receivingSpecificatonWorks() {
    val response = client.specification();

    log.info("Aleph Alpha API specification: {}", response);
    assertThat(response).isNotNull().hasSizeGreaterThan(1000);
  }
}
