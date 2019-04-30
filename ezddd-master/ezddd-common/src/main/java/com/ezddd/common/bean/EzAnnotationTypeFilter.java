package com.ezddd.common.bean;

import com.ezddd.common.annotation.EzAppService;
import com.ezddd.common.annotation.EzCommandHandler;
import com.ezddd.common.annotation.EzComponent;
import com.ezddd.common.annotation.EzDomainService;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class EzAnnotationTypeFilter {
    public static final AnnotationTypeFilter[] annotationTypeFilter = {
            new AnnotationTypeFilter(EzAppService.class),
            new AnnotationTypeFilter(EzComponent.class),
            new AnnotationTypeFilter(EzCommandHandler.class),
            new AnnotationTypeFilter(EzDomainService.class)
    };
}
