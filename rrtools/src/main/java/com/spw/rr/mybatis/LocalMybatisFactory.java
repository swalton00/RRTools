package com.spw.rr.mybatis;

import griffon.core.Configuration;
import griffon.core.GriffonApplication;
import org.codehaus.griffon.runtime.mybatis.DefaultMybatisFactory;

import javax.annotation.Nonnull;

public class MybatisFactory extends DefaultMybatisFactory {
    public MybatisFactory(@Nonnull Configuration configuration, @Nonnull GriffonApplication application) {
        super(configuration, application);
    }
}
