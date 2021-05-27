package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonPropertyOrder({"タスクID", "タスク名","締切日"})
@Data
public class CsvColumn {

  @JsonProperty("タスクID")
  private Integer id;
  
  @JsonProperty("タスク名")
  private String taskName;

  @JsonProperty("締切日")
  private String deadline;

  public CsvColumn () {}

  public CsvColumn (Integer id, String taskName, String deadline) {
    this.id = id;
    this.taskName = taskName;
    this.deadline = deadline;
  }
}