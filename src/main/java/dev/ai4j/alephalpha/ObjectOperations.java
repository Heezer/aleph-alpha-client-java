package dev.ai4j.alephalpha;

public class ObjectOperations {

  public static <T> T getOrDefault(T value, T defaultValue) {
    return value != null ? value : defaultValue;
  }
}
