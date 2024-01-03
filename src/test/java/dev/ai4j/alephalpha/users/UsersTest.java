package dev.ai4j.alephalpha.users;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.BaseTest;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
class UsersTest extends BaseTest {

  @Test
  void gettingUserSettingsWorks() {
    val response = client.userSettings();

    assertThat(response).isNotNull().extracting(UserSettingsResponse::getId).isNotNull();
  }

  @Test
  void changingUserSettingsWorks() {
    val response = client.changeUserSettings(ChangeUserSettingsRequest.builder().outOfCreditsThreshold(0).build());

    assertThat(response).isNotNull().extracting(UserSettingsResponse::getId).isNotNull();
  }
}
