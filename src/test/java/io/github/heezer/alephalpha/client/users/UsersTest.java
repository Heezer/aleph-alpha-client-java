package io.github.heezer.alephalpha.client.users;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.heezer.alephalpha.BaseTest;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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

  @Test
  @Disabled("seems to be a problem on Aleph Alpha side - all the time status code 400 is returned")
  void checkPrivilegesWorks() {
    val response = client.checkPrivileges();

    assertThat(response).isNotNull();
  }
}
