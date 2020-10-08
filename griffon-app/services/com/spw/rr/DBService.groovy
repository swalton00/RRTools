package com.spw.rr

import com.spw.rr.model.BadOrder
import com.spw.rr.model.Inspection
import com.spw.rr.model.MaintenanceItem
import com.spw.rr.model.RRCar
import com.spw.rr.model.ReferenceItem
import com.spw.rr.model.ReportingMark
import com.spw.rr.model.ViewCar
import com.spw.rr.parameter.BadOrderUpdateParameter
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


    void addCar(RRCar newCar) {
        log.debug("adding a new car: {}", newCar)
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                mapper.addRRCar(newCar)
        }

    }

    void updateCar(RRCar car) {
        log.debug("updating this car: {}", car)
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                mapper.updateCar(car)
        }

    }

    List<ViewCar> listViewCars(int viewSelection) {
        log.debug("returning a list of cars")
        List<ViewCar> retList
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                retList = mapper.listViewCars(viewSelection)
        }
        log.debug("returning a list with {}", retList.size())
        log.debug("list has {}", retList)
        return retList
    }

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

    RRCar getRRCar(int id) {
        RRCar car
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                car = mapper.getRRCar(id)
        }
        log.debug("read the car {}", car)
        return car
    }

    List<ReportingMark> getReportingMarks() {
        log.debug("returning a list of reporting marks")
        mybatisHandler.withSqlSession { String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            return mapper.listReportingMarks()
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

    int addReportingMark(ReportingMark newMark) {
        log.debug("adding the new reporting mark {}", newMark)
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                 mapper.addReportingMark(newMark)
        }
        log.debug("Mark now has an ID of {}", newMark.id)
        return newMark.id
    }

    void saveReferenceItem(ReferenceItem item, String tableName) {
        log.debug("Saving the Reference item values {}", item)
        item.tableName = tableName
        mybatisHandler.withSqlSession({String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            mapper.saveReferenceItem(item)
        })
    }

    void addInspection(Inspection newInspection) {
        log.debug("adding the Inspection {}", newInspection)
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                mapper.addInspection(newInspection)
        }
    }

    int addBadOrder(BadOrder bo) {
        log.debug("adding a bad order record - {}", bo)
        Integer newId
        mybatisHandler.withSqlSession({String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            mapper.addBadOrder(bo)
            newId = bo.id
        })
        return newId
    }

    List<BadOrderView> getBadOrders(int carId) {
        log.debug("getting all open bad orders for car {}", carId)
        mybatisHandler.withSqlSession({String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            return mapper.getBadOrders(carId)
        })
    }

    int addMaintenance(MaintenanceItem item) {
        Integer newId
        mybatisHandler.withSqlSession({String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            mapper.addMaintenanceItem(item)
            newId = item.id
        })
        return newId
    }

    int updateBadOrders(BadOrderUpdateParameter boParm) {
        log.debug("updating the relevant Bad Orders {}", boParm)
        mybatisHandler.withSqlSession({String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            mapper.updateBadOrders(boParm)
        })
    }

}