package com.spw.rr;

import com.spw.rr.mybatis.LocalMybatisFactory;
import com.spw.rr.mybatis.LocalMybatisHandler;
import griffon.core.Configuration;
import griffon.core.addon.GriffonAddon;
import griffon.core.injection.Module;
import griffon.inject.Evicts;
import griffon.plugins.mybatis.MybatisFactory;
import griffon.plugins.mybatis.MybatisHandler;
import griffon.plugins.mybatis.MybatisStorage;
import org.codehaus.griffon.runtime.core.injection.AbstractModule;
import org.codehaus.griffon.runtime.mybatis.*;
import org.codehaus.griffon.runtime.util.ResourceBundleProvider;
import org.kordamp.jipsy.ServiceProviderFor;

import javax.inject.Named;
import java.util.ResourceBundle;

import static griffon.util.AnnotationUtils.named;

@Named("mybatis")
@Evicts("mybatis")
//@ServiceProviderFor(Module.class)
public class MybatisEvictionModule extends AbstractModule {
    @Override
    protected void doConfigure() {
        bind(ResourceBundle.class)
                .withClassifier(named("mybatis"))
                .toProvider(new ResourceBundleProvider("Mybatis"))
                .asSingleton();

        bind(Configuration.class)
                .withClassifier(named("mybatis"))
                .to(DefaultMybatisConfiguration.class)
                .asSingleton();

        bind(MybatisStorage.class)
                .to(DefaultMybatisStorage.class)
                .asSingleton();

        bind(MybatisFactory.class)
                .to(DefaultMybatisFactory.class)
                .asSingleton();

        bind(MybatisHandler.class)
                .to(DefaultMybatisHandler.class)
                .asSingleton();

        bind(GriffonAddon.class)
                .to(MybatisAddon.class)
                .asSingleton();
    }
}
