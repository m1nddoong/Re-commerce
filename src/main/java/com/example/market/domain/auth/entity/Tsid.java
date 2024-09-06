package com.example.market.domain.auth.entity;

import com.github.f4b6a3.tsid.TsidFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Clock;
import java.time.ZoneId;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.IdGeneratorType;

@IdGeneratorType(TsidGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Tsid {

    Class<? extends Supplier<TsidFactory>> value() default FactorySupplierCustom.class;

    @Slf4j
    public static class FactorySupplierCustom implements Supplier<TsidFactory> {
        private static int node = 1;
        private static int nodeBits = 2;
        private static String clock = "Asia/Seoul";

        public static final FactorySupplierCustom INSTANCE = new FactorySupplierCustom();
        private final TsidFactory tsidFactory;

        public FactorySupplierCustom() {
            this.tsidFactory = TsidFactory.builder()
                    .withNode(node)
                    .withNodeBits(nodeBits)
                    .withClock(Clock.system(ZoneId.of(clock)))
                    .withRandomFunction(() -> ThreadLocalRandom.current().nextInt())
                    .build();
        }

        @Override
        public TsidFactory get() {
            return this.tsidFactory;
        }
    }
}