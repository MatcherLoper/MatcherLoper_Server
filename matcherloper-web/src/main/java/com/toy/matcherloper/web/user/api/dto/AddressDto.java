package com.toy.matcherloper.web.user.api.dto;

import com.toy.matcherloper.core.user.model.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class AddressDto {

    @NotEmpty(message = "도시를 입력해주십시오.")
    private String city;

    @NotEmpty(message = "상세 주소를 입력하주십시오.")
    private String detailed;

    public AddressDto(Address address) {
        this.city = address.getCity();
        this.detailed = address.getDetailed();
    }
}
