package com.sf.Roboletic.customshadow;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;

/**
 * Created by NetEase on 2016/8/17 0017.
 */
public class CustomShadowTestRunner extends RobolectricGradleTestRunner {

    public CustomShadowTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public InstrumentationConfiguration createClassLoaderConfig() {
        InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
        /**
         * 添加要进行Shadow的对象
         */
        builder.addInstrumentedClass(LoginUtil.class.getName());
        return builder.build();
    }
}
