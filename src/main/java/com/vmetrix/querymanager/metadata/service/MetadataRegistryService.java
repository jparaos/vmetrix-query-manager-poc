package com.vmetrix.querymanager.metadata.service;

import com.vmetrix.querymanager.metadata.model.MetadataRegistry;

public interface MetadataRegistryService {

    void initialize(MetadataRegistry registry);

    MetadataRegistry getRegistry();
}