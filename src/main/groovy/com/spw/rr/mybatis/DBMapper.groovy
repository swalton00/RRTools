package com.spw.rr.mybatis

import com.spw.rr.model.RRCar
import com.spw.rr.model.ReferenceItem
import com.spw.rr.model.ReportingMark
import com.spw.rr.model.ViewCar
import griffon.metadata.TypeProviderFor
import griffon.plugins.mybatis.MybatisMapper

@TypeProviderFor(MybatisMapper)
interface DBMapper extends MybatisMapper {
    List<ReferenceItem> listReferences(String type)
    int addReferenceItem(ReferenceItem newItem, String type)
    int saveReferenceItem(ReferenceItem item, String type)
    ReferenceItem getReferenceItem(int id, String type)

    List<ReportingMark> listReportingMarks()
    int addReportingMark(ReportingMark mark)
    List<ViewCar> listCarsForViewing()
    int addRRCar(RRCar)
    RRCar getRRCar(int id)
    int updateCar(RRCar)
}