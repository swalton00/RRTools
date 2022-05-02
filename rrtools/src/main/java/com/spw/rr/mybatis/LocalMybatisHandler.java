package com.spw.rr.mybatis;

import griffon.plugins.mybatis.MybatisCallback;
import griffon.plugins.mybatis.MybatisFactory;
import griffon.plugins.mybatis.MybatisHandler;
import griffon.plugins.mybatis.MybatisStorage;
import griffon.plugins.mybatis.exceptions.RuntimeMybatisException;
import org.codehaus.griffon.runtime.mybatis.DefaultMybatisHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

public class LocalMybatisHandler  extends DefaultMybatisHandler implements MybatisHandler  {
    private static final Logger log = LoggerFactory.getLogger(LocalMybatisHandler.class);

    @Inject
    public LocalMybatisHandler(MybatisFactory mybatisFactory, MybatisStorage mybatisStorage) {
        super(mybatisFactory, mybatisStorage);
        log.debug("in the constructor");
    }

    @Nullable
    @Override
    public <R> R withSqlSession(@Nonnull MybatisCallback<R> callback) throws RuntimeMybatisException {
        return null;
    }

    @Nullable
    @Override
    public <R> R withSqlSession(@Nonnull String sessionFactoryName, @Nonnull MybatisCallback<R> callback) throws RuntimeMybatisException {
        return null;
    }

    @Override
    public void closeSqlSession() {

    }

    @Override
    public void closeSqlSession(@Nonnull String sessionFactoryName) {

    }
}
