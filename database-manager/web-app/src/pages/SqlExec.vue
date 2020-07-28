<template>
    <el-form :model="sqlData" :inline="true">
        <el-form-item>
            <el-input type="input" v-model="sqlData.sqlText" placeholder="查询条件" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit" icon="el-icon-search">查询</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        name: 'SqlExec',
        data() {
            return {
                sqlData: {
                    sqlText: ''
                }
            }
        },
        methods: {
            onSubmit() {
                // eslint-disable-next-line no-debugger
                debugger
                this.$emit('search', this.sqlData)
            }
        }
    }
</script>

<style scoped>

</style>
