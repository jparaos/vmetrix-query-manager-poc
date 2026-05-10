package com.vmetrix.querymanager.query.comparator;

import com.vmetrix.querymanager.shared.exception.ComparatorNotSupportedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DefaultComparatorFactory
        implements ComparatorFactory {

    private final Map<String, SqlComparator> comparators;

    public DefaultComparatorFactory(
            List<SqlComparator> comparators
    ) {

        this.comparators =
                comparators.stream()
                        .collect(
                                Collectors.toMap(
                                        SqlComparator::getName,
                                        Function.identity()
                                )
                        );
    }

    @Override
    public SqlComparator get(
            String comparatorName
    ) {

        SqlComparator comparator =
                comparators.get(comparatorName);

        if (comparator == null) {

            throw new ComparatorNotSupportedException(
                    "Unsupported comparator: "
                            + comparatorName
            );
        }

        return comparator;
    }
}