package com.example.market.domain.auth.entity;

import java.io.Serializable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TsidGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Tsid.FactorySupplierCustom factorySupplier = Tsid.FactorySupplierCustom.INSTANCE;
        return factorySupplier.get().create().toLong();
    }

}
