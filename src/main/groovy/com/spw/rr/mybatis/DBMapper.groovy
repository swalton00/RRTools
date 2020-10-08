package com.spw.rr.mybatis

import com.spw.rr.model.BadOrder
import com.spw.rr.model.BOViewModel
import com.spw.rr.model.Inspection
import com.spw.rr.model.MaintenanceItem
import com.spw.rr.model.RRCar
import com.spw.rr.model.ReferenceItem
import com.spw.rr.model.ReportingMark
import com.spw.rr.model.ViewCar
import com.spw.rr.parameter.BadOrderUpdateParameter
import griffon.metadata.TypeProviderFor
import griffon.plugins.mybatis.MybatisMapper

@TypeProviderFor(MybatisMapper)
interface DBMapper extends MybatisMapper {
    List<ReferenceItem> listReferences(String type)
    int addReferenceItem(ReferenceItem newItem)
    int saveReferenceItem(ReferenceItem item)
    int badOrders(int id)
    ReferenceItem getReferenceItem(int id, String type)
    List<BOViewModel> getBadOrders(int carId)
    List<ReportingMark> listReportingMarks()
    int addReportingMark(ReportingMark mark)
    List<ViewCar> listViewCars(int viewSelection)
    int addRRCar(RRCar)
    RRCar getRRCar(int id)
    int updateCar(RRCar)
    int addInspection(Inspection inspection)
    int addBadOrder(BadOrder badOrder)
    int addMaintenanceItem(MaintenanceItem item)
    int updateBadOrders(BadOrderUpdateParameter boList)
}