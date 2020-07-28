<template>
    <div class="home">

        <el-container>
            <el-header>
                <div class="total-warpper">

                    <div class="new-connection">

                        <el-button icon="el-icon-plus"  @click="dialogisible=true"></el-button>

                    </div>
                    <el-dialog :visible.sync="dialogisible">
                        <connection @click="addItem"></connection>

                    </el-dialog>
                </div>

            </el-header>
            <el-container>
                <el-aside width="200px">
                    <data-base-panel @click="showData" :connections="connections"></data-base-panel>
                </el-aside>
                <el-main>

                    <el-tabs v-model="editableTabsValue" type="card"  closable @edit="handleTabsEdit">
                        <el-tab-pane
                                :key="item.name"
                                v-for="(item) in editableTabs"
                                :label="item.title"
                                :name="item.name"                        >
                            <component :source-id="item.sourceId" :table-name="item.tableName"
                                       :is="activeName"></component>
                        </el-tab-pane>
                    </el-tabs>
                    <!--                    <sql-panel v-if="sourceId!=''" :source-id="sourceId" :table-name="tableName"></sql-panel>-->


                </el-main>
            </el-container>

        </el-container>


    </div>
</template>

<script>
    import Connection from "../pages/Connection";
    import DataBasePanel from "../pages/DataBasePanel";
    import main from "../js/main";
    import SqlPanel from "../pages/SqlPanel";
    import IconFont from "../components/IconFont";

    export default {
        name: 'Home',
        data() {
            return {
                activeName: 'sql-panel',

                editableTabsValue: '2',
                editableTabs: [],
                tabIndex: 2,


                sourceId: '',
                tableName: '',
                connections: [],
                dialogisible: false

            }
        },
        methods: {
            handleTabsEdit(targetName, action) {
                if (action === 'add') {
                    let newTabName = ++this.tabIndex + '';
                    this.editableTabs.push({
                        title: 'New Tab',
                        name: newTabName,
                        content: 'New Tab content'
                    });
                    this.editableTabsValue = newTabName;
                }
                if (action === 'remove') {
                    let tabs = this.editableTabs;
                    let activeName = this.editableTabsValue;
                    if (activeName === targetName) {
                        tabs.forEach((tab, index) => {
                            if (tab.name === targetName) {
                                let nextTab = tabs[index + 1] || tabs[index - 1];
                                if (nextTab) {
                                    activeName = nextTab.name;
                                }
                            }
                        });
                    }

                    this.editableTabsValue = activeName;
                    this.editableTabs = tabs.filter(tab => tab.name !== targetName);
                }
            },

            showData({sourceId, tableName}) {
                this.sourceId = sourceId;
                this.tableName = tableName;

                let newTabName = ++this.tabIndex + '';
                this.editableTabs.push({
                    title: tableName,
                    sourceId,
                    tableName,
                    name: newTabName,
                    content: 'New Tab content'
                });
                this.editableTabsValue = newTabName;
            },
            async addItem(item) {

                item['leaf'] = true
                const tables = await main.getTables(item.sourceId, item.connection.dataBaseName)
                let nodeList = []
                for (let i = 0; i < tables.length; i++) {
                    nodeList.push({
                        name: tables[i].tableName, connection: {
                            type: 'table',
                            sourceId: item.sourceId
                        }
                    })
                }

                item.children = nodeList
                this.connections.push(item)
                this.dialogisible = false
            }
        },
        components: {
            IconFont,
            SqlPanel,
            DataBasePanel,
            Connection,

        }
    }
</script>


<style scoped lang="scss">

    .total-warpper {
        display: flex;
        align-content: center;
        justify-content: flex-start;
        margin-top: 10px;

        > .new-connection {
            text-align: center;
            padding: 5px 8px;
        }
    }
</style>
