package io.sapiens.app.backend.model;

import io.sapiens.awesome.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
public class StaticPage extends AbstractModel {

  private String title;
  private String content;

  @Override
  public String toString() {
    return null;
  }
}
