package io.github.heezer.alephalpha.client;

public class ObjectOperations {

  public static <T> T getOrDefault(T value, T defaultValue) {
    return value != null ? value : defaultValue;
  }
}
