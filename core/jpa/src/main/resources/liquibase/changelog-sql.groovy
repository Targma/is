databaseChangeLog {

    changeSet(id: 'sql-1', author: 'Targma') {
        sqlFile(
                dbms="postgresql",
                encoding="utf8",
                endDelimiter="\nGO",
                path="sql/testData.sql",
                relativeToChangelogFile="true",
                splitStatements="true",
                stripComments="true"
        )
    }

}
