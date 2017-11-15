package com.company.sample.web.orderitem;

import com.company.sample.entity.OrderItem;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.Field;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.GroupDatasource;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class OrderItemBrowse extends AbstractLookup {
    @Inject
    private DataGrid<OrderItem> dataGrid;
    @Inject
    private GroupDatasource<OrderItem, UUID> orderItemsDs;

    @Override
    public void init(Map<String, Object> params) {
        initDataGridActions();

        dataGrid.addEditorOpenListener(event -> {
            Map<String, Field> fieldMap = event.getFields();
            Field priceField = fieldMap.get("price");
            Field quantityField = fieldMap.get("quantity");
            Field totalField = fieldMap.get("total");

            ValueChangeListener listener = e -> {
                BigDecimal price = priceField.getValue();
                Integer quantity = quantityField.getValue();
                if (price != null && quantity != null) {
                    totalField.setValue(price.multiply(BigDecimal.valueOf(quantity)));
                }
            };

            priceField.addValueChangeListener(listener);
            quantityField.addValueChangeListener(listener);
        });
    }

    private void initDataGridActions() {
        dataGrid.addAction(new CreateAction(dataGrid) {
            @Override
            public void actionPerform(Component component) {
                OrderItem newItem = metadata.create(OrderItem.class);
                orderItemsDs.addItem(newItem);
                dataGrid.edit(newItem);
            }
        });

        dataGrid.addAction(new EditAction(dataGrid) {
            @Override
            public void actionPerform(Component component) {
                OrderItem selected = dataGrid.getSingleSelected();
                if (selected != null) {
                    dataGrid.edit(selected);
                } else {
                    showNotification("Item is not selected");
                }
            }
        });

        dataGrid.addEditorPostCommitListener(event -> orderItemsDs.commit());
    }
}