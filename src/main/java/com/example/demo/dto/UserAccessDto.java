package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAccessDto {
    private Long id;
    @NotBlank(message = "userLogin is required")
    @Size(max = 255, message = "userLogin must be at most 255 characters")
    private String userLogin;
    @NotBlank(message = "userPassword is required")
    @Size(max = 255, message = "userPassword must be at most 255 characters")
    private String userPassword;
    @NotBlank(message = "fillName is required")
    @Size(max = 255, message = "fillName must be at most 255 characters")
    private String fillName;
    @NotBlank(message = "userRole is required")
    @Size(max = 255, message = "userRole must be at most 255 characters")
    private String userRole;
}
