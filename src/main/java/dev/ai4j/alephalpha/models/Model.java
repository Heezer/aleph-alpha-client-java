package dev.ai4j.alephalpha.models;

import lombok.Data;

import java.util.List;

@Data
public class Model {
    String name;
    String description;
    List<String> hostings;
}
