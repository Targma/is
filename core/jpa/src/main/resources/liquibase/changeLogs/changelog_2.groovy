databaseChangeLog {
    changeSet(id: '1497125072193-1', author: 'Targma (generated)') {
        addNotNullConstraint(columnDataType: 'varchar(128)', columnName: 'title', tableName: 'product')
    }
}
