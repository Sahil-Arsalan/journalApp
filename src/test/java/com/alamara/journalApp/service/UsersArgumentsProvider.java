package com.alamara.journalApp.service;

import com.alamara.journalApp.Entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UsersArgumentsProvider implements ArgumentsProvider {
    @Disabled
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("Sahil").password("Sahil").build()),
                Arguments.of(User.builder().userName("Aman").password("Aman").build())
        );
    }
}
