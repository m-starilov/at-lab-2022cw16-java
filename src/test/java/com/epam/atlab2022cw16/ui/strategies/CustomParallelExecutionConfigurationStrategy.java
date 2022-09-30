package com.epam.atlab2022cw16.ui.strategies;

import com.epam.atlab2022cw16.ui.utils.EnvironmentUtils;
import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfiguration;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfigurationStrategy;

public class CustomParallelExecutionConfigurationStrategy implements ParallelExecutionConfiguration, ParallelExecutionConfigurationStrategy {

    @Override
    public int getParallelism() {
        return EnvironmentUtils.getParallelTestCount();
    }

    @Override
    public int getMinimumRunnable() {
        return EnvironmentUtils.getParallelTestCount();
    }

    @Override
    public int getMaxPoolSize() {
        return EnvironmentUtils.getParallelTestCount();
    }

    @Override
    public int getCorePoolSize() {
        return EnvironmentUtils.getParallelTestCount();
    }

    @Override
    public int getKeepAliveSeconds() {
        return 30;
    }

    @Override
    public ParallelExecutionConfiguration createConfiguration(final ConfigurationParameters configurationParameters) {
        return this;
    }
}
