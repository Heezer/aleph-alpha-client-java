package io.github.heezer.alephalpha.client.prompt;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class MultimodalTokenIds {

  private final String type = "token_ids";

  @NotNull
  private List<Integer> data;

  private List<Control> controls;

  @Data
  @Builder
  public static class Control {

    @NotNull
    private Integer index;

    @NotNull
    private Float factor;
  }
}
