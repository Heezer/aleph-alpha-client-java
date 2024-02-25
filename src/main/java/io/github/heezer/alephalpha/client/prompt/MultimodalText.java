package io.github.heezer.alephalpha.client.prompt;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class MultimodalText {

  private final String type = "text";

  @NonNull
  private String data;

  private List<Control> controls;

  @Data
  @Builder
  public static class Control {

    @NonNull
    private Integer start;

    @NonNull
    private Integer length;

    @NonNull
    private Double factor;

    private String tokenOverlap;
  }
}
