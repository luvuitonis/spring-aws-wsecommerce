package com.verassoft.evaluaciones.microservvicios.test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ValidateInputTest {

    @ParameterizedTest
    @ValueSource(ints = {0,1,17})
    void validateIntFalse(int age) {
        assertThat(ValidateInput.isElegible(age)).isFalse();
    }
    @ParameterizedTest
    @ValueSource(ints = {18,19,99999})
    void validateInt(int age) {
        assertThat(ValidateInput.isElegible(age)).isTrue();
    }


    @ParameterizedTest
    @ValueSource(strings = {"REM", "VIS", "SWF"})
    void validateStringFalse(String s) {
        assertThat(ValidateInput.trxOper(s))
                .containsAnyOf("REM", "SWF", "VIS");
    }
    @ParameterizedTest
    @ValueSource(strings = {"EM", "IS", "SW"})
    void validateString(String s) {
        assertThat(ValidateInput.trxOper(s))
                .doesNotContain("REM", "SWF", "VIS");
    }

}
