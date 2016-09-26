package com.sf.Roboletic.sfhttptest;

import com.basesmartframe.request.SFHttpGsonHandler;
import com.sf.Roboletic.SFRoboletricTestRunner;
import com.sf.Roboletic.customshadow.LoginUtil;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.internal.bytecode.ClassInfo;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;

/**
 * Created by NetEase on 2016/8/17 0017.
 */
public class CustomSFHttpTestRunner extends RobolectricGradleTestRunner {

    public CustomSFHttpTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public InstrumentationConfiguration createClassLoaderConfig() {
        InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
        /**
         * 添加要进行Shadow的对象
         */
        builder.addInstrumentedClass(SFHttpGsonHandler.class.getName());
        return builder.build();
    }
    
}
