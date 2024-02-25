package io.github.heezer.alephalpha.client.prompt;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class MultimodalImage {

  private final String type = "image";

  @NonNull
  private String data;

  private Integer x;
  private Integer y;
  private Integer size;

  private List<Control> controls;

  @Data
  @Builder
  public static class Control {

    @NonNull
    private Rect rect;

    @NonNull
    private Float factor;

    private String tokenOverlap;
  }

  @Data
  @Builder
  public static class Rect {

    @NonNull
    private Float left;

    @NonNull
    private Float top;

    @NonNull
    private Float width;

    @NonNull
    private Float height;
  }
}
