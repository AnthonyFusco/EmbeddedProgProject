package com.project.unice.embeddedprogproject.sqlite;

import com.project.unice.embeddedprogproject.models.AbstractModel;

import java.util.List;

public interface IDatabaseManager {
    void add(AbstractModel model);
    void removeRange(List<AbstractModel> list);
    void remove(AbstractModel model);
    void modifyId(String columnAttribute, int newValue, String phoneNumber);
    List<IModel> selectWhere(Class<? extends IModel> classe, String columnName, Object value);
    IModel findFirstValue(Class<? extends IModel> classe, String columnName, Object value);
    List<IModel> selectAll(Class<? extends IModel> classe);
}
