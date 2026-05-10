package com.vmetrix.querymanager.api.request;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ValidationRequest extends QueryBuildRequest {
}