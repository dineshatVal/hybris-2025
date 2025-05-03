package com.sample.module.facades.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;
import de.hybris.platform.converters.Populator;

import java.util.List;

public class CustomPopulatingConverter<S, T> implements Converter<S, T> {

    private Class<T> targetClass;
    private List<Populator<S, T>> populators;

    public void setTargetClass(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public void setPopulators(List<Populator<S, T>> populators) {
        this.populators = populators;
    }

    @Override
    public T convert(S source) {
        Assert.notNull(source, "Source must not be null");
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            for (Populator<S, T> populator : populators) {
                populator.populate(source, target);
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate target class: " + targetClass.getName(), e);
        }
    }
}