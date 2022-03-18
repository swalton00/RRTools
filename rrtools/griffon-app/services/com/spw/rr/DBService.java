package com.spw.rr;

import com.spw.rr.model.ViewCar;
import com.spw.rr.mybatis.DBMapper;
import com.spw.rr.parameter.ViewParameter;
import griffon.core.artifact.GriffonService;
import griffon.metadata.ArtifactProviderFor;
import griffon.plugins.mybatis.MybatisCallback;
import griffon.plugins.mybatis.MybatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

@ArtifactProviderFor(GriffonService.class)
public class DBService extends AbstractGriffonService {

    private static final Logger log = LoggerFactory.getLogger(DBService.class);

    @Inject
    MybatisHandler mybatisHandler;

    Object returnVariable = null;

    List<ViewCar> listViewCars(ViewParameter theView) {
        log.debug("Returning a list of cars with the view: {}", theView);
        mybatisHandler.withSqlSession(new MybatisCallback<String>() {
                    public String handle(@Nonnull String sessionFactoryName, @Nonnull SqlSession session) {
                        DBMapper mapper = session.getMapper(DBMapper.class);
                        returnVariable = mapper.listViewCars(theView);
                        return "";
                    }
            });
        log.debug("returning a list of size {}", ((List<ViewCar>)returnVariable).size());
        return (List<ViewCar>) returnVariable;

    }

}
