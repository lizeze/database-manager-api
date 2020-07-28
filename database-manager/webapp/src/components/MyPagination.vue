<template>
    <div class="my-pagination" v-if="pageCount>8">

        <el-select v-model="pageSize" placeholder="请选择" size="small" style="width: 100px" v-if="defaultSize==null">
            <el-option
                    v-for="item in sizeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
            </el-option>
        </el-select>
        <el-button size="mini" class="el-icon-d-arrow-left" @click="pageIndex=pageIndex-3" v-if="pageIndex>6"
                   :disabled="pageIndex===1?true:false"></el-button>
        <el-button size="mini" class="el-icon-arrow-left" @click="pageIndex--"
                   :disabled="pageIndex===1?true:false"></el-button>
        <span @click="currentChange(1)" class="item" v-bind:class="{active:pageIndex==1}">1</span>
        <span @click="currentChange((n+1))" class="item" v-bind:class="{active:pageIndex==(n+1)}" v-for="n in pageList"
              :key="n">{{n+1}}</span>
        <span @click="currentChange(pageCount)" class="item"
              v-bind:class="{active:pageIndex==pageCount}">{{pageCount}}</span>
        <el-button size="mini" class="el-icon-arrow-right" @click="pageIndex++"
                   :disabled="pageIndex===pageCount"></el-button>
        <el-button size="mini" class="el-icon-d-arrow-right" @click="pageIndex=pageIndex+3" v-if="pageIndex<pageCount-3"
        ></el-button>
        <span class="totalCount">共{{totalCount}}条</span>
    </div>
</template>

<script>
    export default {
        name: 'MyPagination',
        props: ['totalCount', 'defaultSize'],
        data() {
            return {
                pageSize: this.defaultSize || 20,
                pageIndex: 1,
                pageCount: 10,
                pageList: [1, 2, 3, 4, 5, 6, 7, 8],
                startPage: 2,
                sizeOptions: [
                    {label: '10条/页', value: 10},
                    {label: '20条/页', value: 20},
                    {label: '50条/页', value: 50}
                    , {label: '100条/页', value: 100}]
            }
        },
        methods: {
            getPageList() {
                // eslint-disable-next-line no-debugger
                debugger
                let list = []
                let endPage = this.startPage + 8;
                if (endPage > this.pageCount) endPage = this.pageCount - 1
                let startPage = this.startPage;
                for (let i = startPage - 3; i < endPage; i++) {
                    list.push(i)
                }
                this.pageList = list;
            },
            currentChange(pageIndex) {
                this.pageIndex = pageIndex;
                this.$emit('pageChanged', {pageIndex: this.pageIndex, pageSize: this.pageSize})
            },
            resetPageCount() {
                // eslint-disable-next-line no-debugger
                debugger
                this.pageCount = Math.ceil(this.totalCount / this.pageSize)
            }
        }, mounted() {
            this.resetPageCount()
            this.$emit('pageChanged', {pageIndex: this.pageIndex, pageSize: this.pageSize})



        }, watch: {
            pageIndex: function () {
                this.startPage = this.pageIndex
                if (this.startPage > 3)
                    this.getPageList()
                else
                    this.pageList = [1, 2, 3, 4, 5, 6, 7, 8]
                this.$emit('pageChanged', {pageIndex: this.pageIndex, pageSize: this.pageSize})
            },
            pageSize: function () {
                this.resetPageCount()
                this.$emit('pageChanged', {pageIndex: this.pageIndex, pageSize: this.pageSize})
            },
            totalCount: function () {
                this.resetPageCount()
                let pageList = []
                if (this.pageCount < 8) {
                    for (let i = 1; i <= this.pageCount; i++) {
                        pageList.push(i)
                    }
                    this.pageList = pageList;
                }
            }

        }
    }
</script>

<style scoped lang="scss">
    .my-pagination {
        display: flex;
        align-items: center;
        cursor: pointer;
        padding: 5px;
        margin: 5px 0px;

        > .totalCount {
            color: #606266;
            font-size: 13px;
            font-weight: 400;
            display: inline-block;
            vertical-align: top;
            height: 28px;
            line-height: 28px;
            margin-left: 10px;
            vertical-align: top;
        }

        > .item {
            cursor: pointer;
            margin: 0 5px;
            color: #606266;
            min-width: 30px;
            border-radius: 2px;
            padding: 0 4px;
            background: #f4f4f5;
            vertical-align: top;
            display: inline-block;
            font-size: 13px;
            height: 28px;
            line-height: 28px;
            text-align: center;

            &.active {
                background-color: #409eff;
                color: #fff;
            }
        }

    }


</style>
