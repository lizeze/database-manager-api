<template>

    <div>
        <sql-exec @search="search"></sql-exec>
        <el-table :data="tableData" size="small" max-height="700px" stripe border>
            <el-table-column
                    align="center"
                    type="index"
                    width="50">
            </el-table-column>
            <el-table-column v-for="item in columdItem" :key="item.prop"
                             :prop=item.columnName
                             align="center"
                             :label=item.columnName
            >
            </el-table-column>

        </el-table>


        <my-pagination :total-count="totalCount" @pageChanged="pageChanged" default-size="100"></my-pagination>
    </div>
</template>

<script>
    import main from "../js/main";
    import SqlExec from "./SqlExec";
    import MyPagination from "../components/MyPagination";

    export default {
        name: "SqlPanel",
        components: {MyPagination, SqlExec},
        props: ["sourceId", "tableName"],
        data() {
            return {
                columdItem: [],
                tableData: [],
                pageIndex: 1,
                pageSize: 100,
                totalCount: 0,

                sqlText: ''
            }
        }, methods: {
            pageChanged({pageIndex, pageSize}) {
                this.pageSize = pageSize;
                this.pageIndex = pageIndex
            },
            async dataload() {
                const data = await main.getColumns(this.sourceId, this.tableName)
                this.columdItem = data
                const tableData = await main.getTableList({
                    sqlText: this.sqlText,
                    sourceId: this.sourceId,
                    pageSize: this.pageSize,
                    pageIndex: this.pageIndex,
                    tableName: this.tableName
                });
                // eslint-disable-next-line no-debugger
                debugger
                this.tableData = tableData.data;
                this.totalCount = tableData.totalCount

            },
            search({ sqlText}) {
                // eslint-disable-next-line no-debugger
                debugger
                this.sqlText = sqlText;

            }
        },

        mounted() {
            this.dataload()


        }, watch: {
            tableName: function () {
                this.dataload()
            },
            pageSize: function () {
                this.dataload()

            },
            pageIndex: function () {
                this.dataload()

            },
            sqlText: function () {
                this.dataload()

            }
        }
    }
</script>

<style scoped>

</style>
