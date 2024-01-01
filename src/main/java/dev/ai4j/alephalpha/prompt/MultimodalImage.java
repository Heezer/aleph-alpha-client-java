package dev.ai4j.alephalpha.prompt;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class MultimodalImage {

  private final String type = "image";

  @NotNull
  private String data;

  private Integer x;
  private Integer y;
  private Integer size;

  private List<Control> controls;

  @Data
  @Builder
  public static class Control {

    @NotNull
    private Rect rect;

    @NotNull
    private Float factor;

    private String tokenOverlap;
  }

  @Data
  @Builder
  public static class Rect {

    @NotNull
    private Float left;

    @NotNull
    private Float top;

    @NotNull
    private Float width;

    @NotNull
    private Float height;
  }
}
