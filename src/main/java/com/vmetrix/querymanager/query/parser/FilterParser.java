package com.vmetrix.querymanager.query.parser;

import com.vmetrix.querymanager.query.model.FilterNode;

public interface FilterParser {

    FilterNode parse(Object request);
}