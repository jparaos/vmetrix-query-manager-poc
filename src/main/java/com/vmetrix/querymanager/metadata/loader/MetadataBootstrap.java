package com.vmetrix.querymanager.metadata.loader;

import com.vmetrix.querymanager.metadata.service.MetadataRegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetadataBootstrap implements CommandLineRunner {

    private final MetadataLoader metadataLoader;

    private final MetadataRegistryService metadataRegistryService;

    @Override
    public void run(String... args) {

        metadataRegistryService.initialize(
                metadataLoader.load()
        );

        log.info("Metadata registry initialized successfully");
    }
}