package com.example.User.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class DepartmentCreateDto {

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 10, message = "Code must be between 2-10 characters")
    private String code;

    @Size(max = 2000)
    private String description;
}
