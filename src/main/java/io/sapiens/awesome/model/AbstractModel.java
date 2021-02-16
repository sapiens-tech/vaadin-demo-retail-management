package io.sapiens.awesome.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
public abstract class AbstractModel implements Serializable {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @Getter
  private String id;

  @Getter @Setter @Version private Integer version;
  @Getter @Setter private String createdBy;
  @Getter @Setter private String updatedBy;
  @Getter @Setter private LocalDate createdOn;
  @Getter @Setter private LocalDate updatedOn;

  // this is used for preSave & postSave method communications!
  @Transient @Getter @Setter private Boolean creatingNewObject = false;

  public void setId(String id) {
    if (id != null && id.length() > 0) this.id = id;
    else this.id = null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AbstractModel other = (AbstractModel) obj;
    if (id == null) {
      return other.id == null;
    } else return id.equals(other.id);
  }

  @Override
  public abstract String toString();
}
