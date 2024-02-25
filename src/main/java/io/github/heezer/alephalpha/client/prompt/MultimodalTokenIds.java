package io.github.heezer.alephalpha.client.prompt;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class MultimodalTokenIds {

  @Builder.Default
  private final String type = "token_ids";

  @NonNull
  private List<Integer> data;

  private List<Control> controls;

  @Data
  @Builder
  public static class Control {

    @NonNull
    private Integer index;

    @NonNull
    private Double factor;
  }
}
