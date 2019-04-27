package com.ezshop.domain;

import com.ezddd.domain.domain.BaseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderEntity extends BaseEntity {
    public void create() {
        System.out.println("create OrderEntity...");
    }
}
