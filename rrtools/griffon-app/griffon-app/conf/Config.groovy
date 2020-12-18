application {
    title = 'rrtools'
    startupGroups = ['RRTools', 'Help']
    autoShutdown = true
}
mvcGroups {
    // MVC Group for "rrTools"
    'RRTools' {
        model      = 'com.spw.rr.RrToolsModel'
        view       = 'com.spw.rr.RrToolsView'
        controller = 'com.spw.rr.RrToolsController'
    }
    'Reference' {
        model    = 'com.spw.rr.ReferenceModel'
        view    = 'com.spw.rr.ReferenceView'
        controller = 'com.spw.rr.ReferenceController'
    }

    'CarEdit' {
        model    = 'com.spw.rr.CarEditModel'
        view    = 'com.spw.rr.CarEditView'
        controller = 'com.spw.rr.CarEditController'
    }

    'Prefs' {
        model = 'com.spw.rr.PrefsModel'
        view = 'com.spw.rr.PrefsView'
        controller = 'com.spw.rr.PrefsController'
    }

    'Inspection' {
        model = 'com.spw.rr.InspectionModel'
        view = 'com.spw.rr.InspectionView'
        controller = 'com.spw.rr.InspectionController'
    }

    'BadOrder' {
        model = 'com.spw.rr.BadOrderModel'
        view = 'com.spw.rr.BadOrderView'
        controller = 'com.spw.rr.BadOrderController'
    }

    'Maintenance' {
        model = 'com.spw.rr.MaintenanceModel'
        view = 'com.spw.rr.MaintenanceView'
        controller = 'com.spw.rr.MaintenanceController'
    }

    'Help' {
        model = 'com.spw.rr.HelpSystemModel'
        view = 'com.spw.rr.HelpSystemView'
        controller = 'com.spw.rr.HelpSystemController'
    }

}