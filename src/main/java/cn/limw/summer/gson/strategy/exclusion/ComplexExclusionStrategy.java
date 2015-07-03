package cn.limw.summer.gson.strategy.exclusion;

import com.google.gson.FieldAttributes;

/**
 * @author li
 * @version 1 (2014年11月12日 下午12:42:22)
 * @since Java7
 */
public class ComplexExclusionStrategy extends AbstractExclusionStrategy {
    private AbstractExclusionStrategy exclusionStrategya;

    private AbstractExclusionStrategy exclusionStrategyb;

    public ComplexExclusionStrategy(AbstractExclusionStrategy exclusionStrategya, AbstractExclusionStrategy exclusionStrategyb) {
        this.exclusionStrategya = exclusionStrategya;
        this.exclusionStrategyb = exclusionStrategyb;
    }

    public boolean shouldSkipClass(Class<?> clazz) {
        return exclusionStrategya.shouldSkipClass(clazz) || exclusionStrategyb.shouldSkipClass(clazz);
    }

    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return exclusionStrategya.shouldSkipField(fieldAttributes) || exclusionStrategyb.shouldSkipField(fieldAttributes);
    }

    public String toString() {
        return "{ " + exclusionStrategya + " || " + exclusionStrategyb + " }";
    }
}