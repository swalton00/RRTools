application {
    title = 'rrtools'
    startupGroups = ['rrTools']
    autoShutdown = true
}
mvcGroups {
    // MVC Group for "rrTools"
    'rrTools' {
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
}