package com.dev.acouplefarms.models.organization;

import static javax.persistence.GenerationType.AUTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Organization {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String name;
  private String nameKey;
  private String email;
  private String phoneNumber;
  private Date createDate;
  private Date updateDate;
  private Boolean active;
}
