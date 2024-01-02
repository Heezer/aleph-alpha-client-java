package dev.ai4j.alephalpha.versions;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.BaseTest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
@Slf4j
class VersionsTest extends BaseTest {

  @Test
  void receivingVersionWorks() {
    val response = client.version();

    log.info("API version: {}", response);
    assertThat(response).isNotNull().hasSizeGreaterThan(5);
  }

  @Test
  void receivingOpenApiVersionsWorks() {
    val response = client.openApiVersions();

    log.info("Available versions of OpenAPI description: {}", response);
    assertThat(response).isNotNull().hasSizeGreaterThan(1);
  }

  @Test
  void receivingOpenApiDescriptionWorks() {
    val lastOpenApiVersion = client.openApiVersions().stream().reduce((first, second) -> second).orElse(null);

    val response = client.openApiDescription(lastOpenApiVersion);

    log.info("OpenAPI description for API version {}: {}", lastOpenApiVersion, response);
    assertThat(response).isNotNull().hasSizeGreaterThan(1000);
  }
}
