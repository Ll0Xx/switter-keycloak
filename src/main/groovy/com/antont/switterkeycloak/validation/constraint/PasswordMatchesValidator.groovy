package com.antont.switterkeycloak.validation.constraint

import com.antont.switterkeycloak.validation.PasswordMatches
import com.antont.switterkeycloak.web.dto.CreateUserDto
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    void initialize(final PasswordMatches constraintAnnotation) {

    }

    @Override
    boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final CreateUserDto user = (CreateUserDto) obj
        return user.password == user.passwordConfirmation
    }

}
