package com.dev.acouplefarms.models.images;

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
public class Image {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String url;
  private Date createDate;
  private Date updateDate;
}
