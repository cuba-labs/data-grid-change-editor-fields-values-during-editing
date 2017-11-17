package com.company.sample.web.orderitem;

import com.company.sample.entity.OrderItem;
import com.company.sample.entity.Product;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class OrderItemBrowse extends AbstractLookup {
    @Inject
    private DataGrid<OrderItem> dataGrid;
    @Inject
    private GroupDatasource<OrderItem, UUID> orderItemsDs;
    @Inject
    private ComponentsFactory componentsFactory;

    @Override
    public void init(Map<String, Object> params) {
        initDataGridActions();
        initDataGridColumns();
        setupDataGridEditor();
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

    private void initDataGridColumns() {
        dataGrid.addGeneratedColumn("total", new DataGrid.ColumnGenerator<OrderItem, BigDecimal>() {
            @Override
            public BigDecimal getValue(DataGrid.ColumnGeneratorEvent<OrderItem> event) {
                OrderItem item = event.getItem();
                return getTotal(item);
            }

            @Override
            public Class<BigDecimal> getType() {
                return BigDecimal.class;
            }
        });
    }

    private BigDecimal getTotal(OrderItem item) {
        Product product = item.getProduct();
        BigDecimal price = product != null ? product.getPrice() : null;
        Integer quantity = item.getQuantity();
        return (price != null && quantity != null)
                ? price.multiply(BigDecimal.valueOf(quantity))
                : BigDecimal.ZERO;
    }

    private void setupDataGridEditor() {
        dataGrid.getColumnNN("product.name").setEditorFieldGenerator((datasource, property) -> {
            TextField field = componentsFactory.createComponent(TextField.class);
            OrderItem item = ((OrderItem) datasource.getItem());
            field.setValue(item.getProduct() != null ? item.getProduct().getName() : null);
            field.setEditable(false);
            return field;
        });

        dataGrid.getColumnNN("total").setEditorFieldGenerator((datasource, property) -> {
            CurrencyField field = componentsFactory.createComponent(CurrencyField.class);
            field.setValue(getTotal((OrderItem) datasource.getItem()));
            field.setEditable(false);
            return field;
        });

        dataGrid.addEditorOpenListener(event -> {
            Map<String, Field> fieldMap = event.getFields();
            Field productField = fieldMap.get("product");
            Field productNameField = fieldMap.get("product.name");
            Field quantityField = fieldMap.get("quantity");
            Field totalField = fieldMap.get("total");

            ValueChangeListener listener = e -> {
                Product product = productField.getValue();
                Integer quantity = quantityField.getValue();
                if (product != null && product.getPrice() != null && quantity != null) {
                    totalField.setValue(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
                }

                productNameField.setValue(product != null ? product.getName() : null);
            };

            productField.addValueChangeListener(listener);
            quantityField.addValueChangeListener(listener);
        });
    }
}