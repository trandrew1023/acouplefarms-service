package com.dev.acouplefarms.models.images;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImage {
  @Id private Long userId;
  private Long imageId;
  private Date createDate;
  private Date updateDate;
}
