package com.spw.rr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;

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

    public void addBinding(Component source, String sourceName, Object target, Boolean modFlag) {
        Bind newBind = new Bind(source, sourceName, target, modFlag);
        doBind(newBind, source, sourceName, target);
    }

    private void doBind(Bind newBind, Component source, String sourceName, Object target) {
        sources.put(source, newBind);
        source.addPropertyChangeListener(sourceName, this);
        log.debug("adding a binding for {}, {}", source, sourceName);
    }

    public void addBinding(Component source, String sourceName, Object target) {
        Bind binding = new Bind(source, sourceName, target);
        doBind(binding, source, sourceName, target);
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
                if (sourceVal.modFlag != null) {
                    sourceVal.modFlag = true;
                }
            }
        }
    }

    class Bind {
        Component source;
        String sourceName;
        Object target;
        Boolean modFlag = null;

        private void setup(Component source, String sourceName, Object target) {
            this.source = source;
            this.sourceName = sourceName;
            this.target = target;
        }

        public Bind(Component source, String sourceName, Object target, Boolean modified) {
            modFlag = modified;
            setup(source, sourceName, target);
        }

        public Bind(Component source, String sourceName, Object target) {
            setup(source, sourceName, target);

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

        Boolean getModFlag() {
            return modFlag;
        }

    }


}
