package com.spw.rr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.awt.*;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import java.util.concurrent.Callable;

@Singleton
public class Bindings implements PropertyChangeListener {
    private static final Bindings instance = new Bindings();

    private Bindings() {
    }

    public static Bindings getInstance() {
        return instance;
    }

    private static final Logger log = LoggerFactory.getLogger(Bindings.class);
    private Hashtable<Component, Bind> sources = new Hashtable<>();


    public void addBinding(Component source, String sourceName, Object target) {
        Bind binding = new Bind(source, sourceName, target);
        sources.put(source, binding);
        source.addPropertyChangeListener(sourceName, this);
        log.debug("adding a binding for {}, {}", source, sourceName);
    }

    public void removeBinding(Object obj) {
        log.debug("removing a binding {}", obj);
        Bind thisBind = sources.get(obj);
        if (thisBind == null) {
            log.error("removal of binding failed -- binding does not exist");
            throw new IllegalArgumentException("Illegal attempt to remove non-existent binding");
        }
        removeBinding(thisBind);
    }

    private void removeBinding(Bind binding) {
        Component thisComponent = binding.getSource();
        thisComponent.addPropertyChangeListener(binding.getSourceName(), this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        log.debug("in the PropertyChange");
        if (evt.getSource() == null) {
            return;
        }
        if (evt.getSource() instanceof Component) {
            Bind sourceVal = sources.get(evt.getSource());
            if (sourceVal != null) {
                Object theTarget = sourceVal.getTarget();
                theTarget = evt.getPropertyName();
            }
        }
    }

    class Bind {
        Component source;
        String sourceName;
        Object target;

        public Bind(Component source, String sourceName, Object target) {
            this.source = source;
            this.sourceName = sourceName;
            this.target = target;
        }

        Component getSource() {
            return this.source;
        }

        String getSourceName() {
            return sourceName;
        }

        Object getTarget() {
            return target;
        }

    }


}
