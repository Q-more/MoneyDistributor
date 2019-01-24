package com.mediatoolkit.moneydistributor.api.model;

import com.mediatoolkit.moneydistributor.api.exceptions.enums.ApiErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @Size(max = 25, message = ApiErrorCode.INVALID_SIZE_CODE)
    @NotEmpty(message = ApiErrorCode.EMPTY_FIELD_CODE)
    private String firstName;
    @Size(max = 25, message = ApiErrorCode.INVALID_SIZE_CODE)
    @NotEmpty(message = ApiErrorCode.EMPTY_FIELD_CODE)
    private String lastName;
    private String username;
    @Size(max = 70, message = ApiErrorCode.INVALID_SIZE_CODE)
    @NotEmpty(message = ApiErrorCode.EMPTY_FIELD_CODE)
    @Email(message = "Email should be valid")
    private String email;

    @Override
    public String toString() {
        return "id=" + id + " name= " + firstName + " " + lastName + " username= " + username + " email = " + email;
    }
}
