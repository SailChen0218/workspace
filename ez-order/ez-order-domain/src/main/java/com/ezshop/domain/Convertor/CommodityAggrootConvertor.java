package com.ezshop.domain.Convertor;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.tunnel.Convertor;
import com.ezddd.core.utils.BeanCopyUtils;
import com.ezshop.domain.aggregate.CommodityAggroot;
import com.ezshop.infra.tunnel.dataobject.CommodityAggrootDo;

@EzComponent
public class CommodityAggrootConvertor implements Convertor<CommodityAggroot, CommodityAggrootDo> {

    @Override
    public CommodityAggrootDo EntityToDataObject(CommodityAggroot entity) {
        CommodityAggrootDo commodityAggrootDo = BeanCopyUtils.cloneByProperties(entity, CommodityAggrootDo.class);
        return commodityAggrootDo;
    }

    @Override
    public CommodityAggroot DataObjectToEntity(CommodityAggrootDo commodityAggrootDo) {
        CommodityAggroot commodityAggroot = BeanCopyUtils.cloneByProperties(commodityAggrootDo, CommodityAggroot.class);
        return commodityAggroot;
    }
}
