package io.github.heezer.alephalpha.client.models;

import java.util.List;
import lombok.Data;

@Data
public class Model {

  String name;
  String description;
  List<String> hostings;
}
