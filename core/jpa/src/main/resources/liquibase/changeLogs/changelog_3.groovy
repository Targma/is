databaseChangeLog {
    changeSet(id: '1497353006506-1', author: 'Targma (generated)') {
        addColumn(tableName: 'order') {
            column(name: 'price', type: 'numeric(10, 2)') {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: '1497353006506-2', author: 'Targma (generated)') {
        addColumn(tableName: 'user') {
            column(name: 'name', type: 'varchar(128 BYTE)')
        }
    }

    changeSet(id: '1497353006506-3', author: 'Targma (generated)') {
        addColumn(tableName: 'product') {
            column(name: 'discount', type: 'numeric(10, 2)') {
                constraints(nullable: false)
            }
        }
    }

}
