package com.dev.acouplefarms.models.organization;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrganizationResponse {
  private Long id;
  private String name;
  private String email;
  private String phoneNumber;
  private Date createDate;
  private Date updateDate;
  private Boolean active;
  private boolean isAdmin;

  public OrganizationResponse(final Organization organization, final boolean isAdmin) {
    this.id = organization.getId();
    this.name = organization.getName();
    this.email = organization.getEmail();
    this.phoneNumber = organization.getPhoneNumber();
    this.createDate = organization.getCreateDate();
    this.updateDate = organization.getUpdateDate();
    this.active = organization.getActive();
    this.isAdmin = isAdmin;
  }
}
