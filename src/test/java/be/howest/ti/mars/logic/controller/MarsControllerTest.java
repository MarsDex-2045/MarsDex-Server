package be.howest.ti.mars.logic.controller;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MarsControllerTest {

    @Test
    void getMessageReturnsAWelcomeMessage() {
        // Arrange
        MarsController sut = new MarsController();

        // Act
        String message = sut.getMessage();

        //Assert
        assertTrue(StringUtils.isNoneBlank(message));
    }
}
