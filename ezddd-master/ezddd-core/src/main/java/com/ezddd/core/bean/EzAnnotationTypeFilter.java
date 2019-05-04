package com.ezddd.core.bean;

import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzDomainService;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class EzAnnotationTypeFilter {
    public static final AnnotationTypeFilter[] annotationTypeFilter = {
            new AnnotationTypeFilter(EzAppService.class),
            new AnnotationTypeFilter(EzComponent.class),
            new AnnotationTypeFilter(EzCommandHandler.class),
            new AnnotationTypeFilter(EzDomainService.class)
    };
}
