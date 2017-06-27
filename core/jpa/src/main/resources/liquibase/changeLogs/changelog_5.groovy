databaseChangeLog {
    changeSet(id: '1498328624568-1', author: 'Targma (generated)') {
        addColumn(tableName: 'address') {
            column(name: 'street', type: 'varchar(32 BYTE)')
        }
    }
}