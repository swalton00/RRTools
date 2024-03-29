package com.spw.rr.mybatis

import com.spw.rr.model.BadOrder
import com.spw.rr.model.BOViewModel
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
import griffon.metadata.TypeProviderFor
import griffon.plugins.mybatis.MybatisMapper

@TypeProviderFor(MybatisMapper)
interface DBMapper extends MybatisMapper {
    List<ReferenceItem> listReferences(String type)
    int addReferenceItem(ReferenceItem newItem)
    int saveReferenceItem(ReferenceItem item)
    ReferenceItem getReferenceItem(int id, String type)
    List<ReportingMark> listReportingMarks()
    int addReportingMark(ReportingMark mark)
    List<Vendor> listVendors()
    int addVendor(Vendor vendor)
    List<Manufacturer> listManufacturers()
    int addManufacturer(Manufacturer manufacturer)
    List<ViewCar> listViewCars(ViewParameter viewSelection)
    int addRRCar(RRCar)
    RRCar getRRCar(int id)
    RRCar findCar(CarParameter parameter)
    int updateCar(RRCar)
    List<ExportCar> listCarsForExport()
    int addInspection(Inspection inspection)
    List<BOViewModel> getBadOrders(int carId)
    int badOrders(int id)
    int updateBadOrders(BadOrderUpdateParameter boList)
    int addBadOrder(BadOrder badOrder)
    int addMaintenanceItem(MaintenanceItem item)
    CarType findType(String carType)
    int addCarType(CarType carType)
}