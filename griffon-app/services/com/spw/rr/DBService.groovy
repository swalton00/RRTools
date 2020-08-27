package com.spw.rr

import com.spw.rr.model.ReferenceItem
import griffon.core.artifact.GriffonService
import griffon.metadata.ArtifactProviderFor
import javax.inject.Inject
import griffon.plugins.mybatis.MybatisHandler
import org.apache.ibatis.session.SqlSession
import com.spw.rr.mybatis.DBMapper

@javax.inject.Singleton
@ArtifactProviderFor(GriffonService)
class DBService {

    @Inject
    private MybatisHandler mybatisHandler

    List<ReferenceItem> getKitTypes() {
        mybatisHandler.withSqlsession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                return mapper.listReferences("KIT_TYPE")
        }
    }

    List<ReferenceItem> getReferenceList(String tableName) {
        log.debug("getting a list of references for {}", tableName)
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                return mapper.listReferences(tableName)
        }
    }


}