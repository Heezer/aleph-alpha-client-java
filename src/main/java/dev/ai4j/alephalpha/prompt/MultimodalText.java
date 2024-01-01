package dev.ai4j.alephalpha.prompt;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class MultimodalText {

  private final String type = "text";

  @NotNull
  private String data;

  private List<Control> controls;

  @Data
  @Builder
  public static class Control {

    @NotNull
    private Integer start;

    @NotNull
    private Integer length;

    @NotNull
    private Float factor;

    private String tokenOverlap;
  }
}
