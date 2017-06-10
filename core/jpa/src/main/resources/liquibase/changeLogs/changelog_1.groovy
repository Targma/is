databaseChangeLog {
    changeSet(id: '1496938440074-1', author: 'Targma (generated)') {
        createTable(tableName: 'address') {
            column(name: 'id', type: 'SERIAL', autoIncrement: true) {
                constraints(primaryKey: true, primaryKeyName: 'address_pkey')
            }
            column(name: 'created_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'edited_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'is_deleted', type: 'BOOLEAN') {
                constraints(nullable: false)
            }
            column(name: 'version', type: 'INT')
            column(name: 'is_latest', type: 'BOOLEAN') {
                constraints(nullable: false)
            }
            column(name: 'origin_id', type: 'INT')
            column(name: 'version_order', type: 'INT') {
                constraints(nullable: false)
            }
            column(name: 'city', type: 'VARCHAR(128)')
            column(name: 'code', type: 'VARCHAR(32)')
            column(name: 'customer_id', type: 'INT') {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: '1496938440074-2', author: 'Targma (generated)') {
        createTable(tableName: 'customer') {
            column(name: 'id', type: 'SERIAL', autoIncrement: true) {
                constraints(primaryKey: true, primaryKeyName: 'customer_pkey')
            }
            column(name: 'created_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'edited_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'is_deleted', type: 'BOOLEAN') {
                constraints(nullable: false)
            }
            column(name: 'version', type: 'INT')
            column(name: 'is_latest', type: 'BOOLEAN') {
                constraints(nullable: false)
            }
            column(name: 'origin_id', type: 'INT')
            column(name: 'version_order', type: 'INT') {
                constraints(nullable: false)
            }
            column(name: 'authenticationid', type: 'VARCHAR(128)') {
                constraints(nullable: false)
            }
            column(name: 'email', type: 'VARCHAR(128)') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(128)')
            column(name: 'surname', type: 'VARCHAR(128)')
            column(name: 'telephonenumber', type: 'VARCHAR(128)')
        }
    }

    changeSet(id: '1496938440074-3', author: 'Targma (generated)') {
        createTable(tableName: 'order') {
            column(name: 'id', type: 'SERIAL', autoIncrement: true) {
                constraints(primaryKey: true, primaryKeyName: 'order_pkey')
            }
            column(name: 'created_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'edited_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'is_deleted', type: 'BOOLEAN') {
                constraints(nullable: false)
            }
            column(name: 'version', type: 'INT')
            column(name: 'address_id', type: 'INT') {
                constraints(nullable: false)
            }
            column(name: 'customer_id', type: 'INT') {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: '1496938440074-4', author: 'Targma (generated)') {
        createTable(tableName: 'product') {
            column(name: 'id', type: 'SERIAL', autoIncrement: true) {
                constraints(primaryKey: true, primaryKeyName: 'product_pkey')
            }
            column(name: 'created_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'edited_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'is_deleted', type: 'BOOLEAN') {
                constraints(nullable: false)
            }
            column(name: 'version', type: 'INT')
            column(name: 'is_latest', type: 'BOOLEAN') {
                constraints(nullable: false)
            }
            column(name: 'origin_id', type: 'INT')
            column(name: 'version_order', type: 'INT') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(255)')
            column(name: 'price', type: 'numeric(10, 2)') {
                constraints(nullable: false)
            }
            column(name: 'title', type: 'VARCHAR(128)')
        }
    }

    changeSet(id: '1496938440074-5', author: 'Targma (generated)') {
        createTable(tableName: 'product_on_order') {
            column(name: 'id', type: 'SERIAL', autoIncrement: true) {
                constraints(primaryKey: true, primaryKeyName: 'product_on_order_pkey')
            }
            column(name: 'created_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'edited_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'is_deleted', type: 'BOOLEAN') {
                constraints(nullable: false)
            }
            column(name: 'version', type: 'INT')
            column(name: 'discount', type: 'numeric(10, 2)') {
                constraints(nullable: false)
            }
            column(name: 'order_number', type: 'SMALLINT') {
                constraints(nullable: false)
            }
            column(name: 'quantity', type: 'numeric(10, 2)') {
                constraints(nullable: false)
            }
            column(name: 'order_id', type: 'INT') {
                constraints(nullable: false)
            }
            column(name: 'product_id', type: 'INT') {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: '1496938440074-6', author: 'Targma (generated)') {
        createTable(tableName: 'user') {
            column(name: 'id', type: 'SERIAL', autoIncrement: true) {
                constraints(primaryKey: true, primaryKeyName: 'user_pkey')
            }
            column(name: 'created_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'edited_on', type: 'TIMESTAMP(6) WITHOUT TIME ZONE') {
                constraints(nullable: false)
            }
            column(name: 'is_deleted', type: 'BOOLEAN') {
                constraints(nullable: false)
            }
            column(name: 'version', type: 'INT')
            column(name: 'authenticationid', type: 'VARCHAR(128)')
            column(name: 'email', type: 'VARCHAR(128)')
        }
    }

    changeSet(id: '1496938440074-7', author: 'Targma (generated)') {
        addUniqueConstraint(columnNames: 'email', constraintName: 'uk_dwk6cx0afu8bs9o4t536v1j5v', tableName: 'customer')
    }

    changeSet(id: '1496938440074-8', author: 'Targma (generated)') {
        addForeignKeyConstraint(baseColumnNames: 'address_id', baseTableName: 'order', constraintName: 'fk145gxdq9etc0fv3a0vutmkbye', deferrable: false, initiallyDeferred: false, onDelete: 'NO ACTION', onUpdate: 'NO ACTION', referencedColumnNames: 'id', referencedTableName: 'address')
    }

    changeSet(id: '1496938440074-9', author: 'Targma (generated)') {
        addForeignKeyConstraint(baseColumnNames: 'customer_id', baseTableName: 'order', constraintName: 'fk1oduxyuuo3n2g98l3j7754vym', deferrable: false, initiallyDeferred: false, onDelete: 'NO ACTION', onUpdate: 'NO ACTION', referencedColumnNames: 'id', referencedTableName: 'customer')
    }

    changeSet(id: '1496938440074-10', author: 'Targma (generated)') {
        addForeignKeyConstraint(baseColumnNames: 'customer_id', baseTableName: 'address', constraintName: 'fk93c3js0e22ll1xlu21nvrhqgg', deferrable: false, initiallyDeferred: false, onDelete: 'NO ACTION', onUpdate: 'NO ACTION', referencedColumnNames: 'id', referencedTableName: 'customer')
    }

    changeSet(id: '1496938440074-11', author: 'Targma (generated)') {
        addForeignKeyConstraint(baseColumnNames: 'product_id', baseTableName: 'product_on_order', constraintName: 'fkdujqh9ogotyjn2u6c4bu4g778', deferrable: false, initiallyDeferred: false, onDelete: 'NO ACTION', onUpdate: 'NO ACTION', referencedColumnNames: 'id', referencedTableName: 'product')
    }

    changeSet(id: '1496938440074-12', author: 'Targma (generated)') {
        addForeignKeyConstraint(baseColumnNames: 'order_id', baseTableName: 'product_on_order', constraintName: 'fkeshj7cuhkkanw21fhitdstnpu', deferrable: false, initiallyDeferred: false, onDelete: 'NO ACTION', onUpdate: 'NO ACTION', referencedColumnNames: 'id', referencedTableName: 'order')
    }

}
