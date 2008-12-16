package org.qi4j.library.struts2.support.view;

import static org.qi4j.library.struts2.util.ParameterizedTypes.findTypeVariables;

import org.qi4j.api.injection.scope.This;
import org.qi4j.library.struts2.support.ProvidesEntityOfMixin;

public abstract class ProvidesViewOfMixin<T> extends ProvidesEntityOfMixin<T> implements ProvidesViewOf<T> {

    @This ProvidesViewOf<T> action;

    public String execute() {
        loadEntity();
        return SUCCESS;
    }
    
    protected Class<T> typeToLoad() {
        return (Class<T>) findTypeVariables(action.getClass(), ProvidesViewOf.class)[0];
    }
}