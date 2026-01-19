package com.example.User.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class DepartmentUpdateDto {

    @Size(max = 100)
    private String name;

    @Size(max = 20)
    private String code;

    @Size(max = 2000)
    private String description;

   @NotBlank(message = "Status is required")
    @Size(max = 2000)
    private String status;

}
