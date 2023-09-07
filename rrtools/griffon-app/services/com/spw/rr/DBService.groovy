package com.spw.rr

import com.spw.rr.model.BadOrder
import com.spw.rr.model.CarType
import com.spw.rr.model.ExportCar
import com.spw.rr.model.Inspection
import com.spw.rr.model.MaintenanceItem
import com.spw.rr.model.Manufacturer
import com.spw.rr.model.RRCar
import com.spw.rr.model.ReferenceItem
import com.spw.rr.model.ReportingMark
import com.spw.rr.model.Vendor
import com.spw.rr.model.ViewCar
import com.spw.rr.parameter.BadOrderUpdateParameter
import com.spw.rr.parameter.CarParameter
import com.spw.rr.parameter.ViewParameter
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

    List<ViewCar> listViewCars(ViewParameter view) {
        log.debug("returning a list of cars with parameter {}", view)
        List<ViewCar> retList
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                retList = mapper.listViewCars(view)
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

    List<Vendor> getVendors() {
        log.debug("returning a list of vendors")
        mybatisHandler.withSqlSession { String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            return mapper.listVendors()
        }
    }

    int addVendor(Vendor newVendor) {
        log.debug("adding the new vendor {}", newVendor)
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                mapper.addVendor(newVendor)
        }
        log.debug("Vendor idis {}", newVendor.id)
        return newVendor.id
    }

    List<Manufacturer> getManufacturers() {
        log.debug("returning a list of manufacturers")
        mybatisHandler.withSqlSession { String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            return mapper.listManufacturers()
        }
    }

    int addManufacturer(Manufacturer newManf) {
        log.debug("adding the new Manufacturer {}", newManf)
        mybatisHandler.withSqlSession {
            String sessionFactoryName, SqlSession session ->
                DBMapper mapper = session.getMapper(DBMapper.class)
                mapper.addManufacturer(newManf)
        }
        log.debug("Returned id is {}", newManf.id)
        return newManf.id
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

    RRCar findCar(int reportingMark, String carNumber) {
        log.debug("looking for mark " + reportingMark.toString() + " car number " + carNumber)
        RRCar carFound = null
        CarParameter parm = new CarParameter()
        parm.reportingMark = reportingMark
        parm.carNumber = carNumber
        mybatisHandler.withSqlSession( {String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            carFound = mapper.findCar(parm)
        })
    }

    CarType findType(String carType) {
        log.debug("looking up car type of " + carType)
        CarType retValue = null
        mybatisHandler.withSqlSession( {String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            CarType tempValue = mapper.findType(carType)
            log.debug("tempValue was ${tempValue}")
            retValue = tempValue
            log.debug("value found was ${retValue}")
        })
        return retValue
    }

    CarType addCarType(CarType carType) {
        log.debug("adding a new car Type: " + carType.toString())
        mybatisHandler.withSqlSession( {String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            mapper.addCarType(carType)
        })
        log.debug("carType is now " + carType.toString())
    }

    List<ExportCar> exportCarList() {
        log.debug("exporting cars")
        List<ExportCar> retList = null
        mybatisHandler.withSqlSession({String sessionFactoryName, SqlSession session ->
            DBMapper mapper = session.getMapper(DBMapper.class)
            retList =  mapper.listCarsForExport()
        })
        return retList
    }

}