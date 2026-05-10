package com.vmetrix.querymanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "metadata")
public class MetadataProperties {

    private String entitiesPath;
    private String relationshipsPath;
    private String comparatorsPath;

    public String getEntitiesPath() {
        return entitiesPath;
    }

    public void setEntitiesPath(String entitiesPath) {
        this.entitiesPath = entitiesPath;
    }

    public String getRelationshipsPath() {
        return relationshipsPath;
    }

    public void setRelationshipsPath(String relationshipsPath) {
        this.relationshipsPath = relationshipsPath;
    }

    public String getComparatorsPath() {
        return comparatorsPath;
    }

    public void setComparatorsPath(String comparatorsPath) {
        this.comparatorsPath = comparatorsPath;
    }
}