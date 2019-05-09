package com.ezddd.core.spring;

import com.ezddd.core.annotation.*;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class EzAnnotationTypeFilter {
    public static final AnnotationTypeFilter[] annotationTypeFilterForApplication = {
            new AnnotationTypeFilter(EzAppService.class),
            new AnnotationTypeFilter(EzComponent.class)
    };

    public static final AnnotationTypeFilter[] annotationTypeFilterForDomain = {
            new AnnotationTypeFilter(EzComponent.class),
            new AnnotationTypeFilter(EzAggregate.class),
            new AnnotationTypeFilter(EzDomainService.class),
            new AnnotationTypeFilter(EzCommand.class)
    };
}
