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


    void addReferenceItem(ReferenceItem newItem, String tableName) {
        log.debug("adding a new item {}", newItem)
        newItem.tableName = tableName
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                return mapper.addReferenceItem(newItem)
        }
        log.debug("Reference item is now {}", newItem)
    }

    List<ReferenceItem> getReferenceList(String tableName) {
        log.debug("getting a list of references for {}", tableName)
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                return mapper.listReferences(tableName)
        }
    }

    void saveReferenceItem(ReferenceItem item, String tableName) {
        log.debug("Saving the Reference item values {}", item)
        item.tableName = tableName
        mybatisHandler.withSqlSession({String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            mapper.saveReferenceItem(item)
        })
    }



}