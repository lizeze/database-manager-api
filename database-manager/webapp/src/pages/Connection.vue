<template>
    <div>
        <el-form
                :model="connection"
                ref="ruleForm"
                label-width="100px"
                class="demo-ruleForm"
        >
            <el-form-item label="连接名称" prop="name">
                <el-input v-model="connection.name"></el-input>
            </el-form-item>
            <el-form-item label="主机" prop="host">
                <el-input v-model="connection.host"></el-input>
            </el-form-item>
            <el-form-item label="端口" required>
                <el-input v-model="connection.port"></el-input>
            </el-form-item>
            <el-form-item label="用户名" prop="delivery">
                <el-input v-model="connection.userName"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="desc">
                <el-input v-model="connection.passWord"></el-input>
            </el-form-item>
            <el-form-item label="数据库" prop="dataBaseName">
                <el-input v-model="connection.dataBaseName"></el-input>
            </el-form-item>
            <el-form-item label="类型" prop="type">
                <el-select
                        v-model="connection.type"
                        style="width:100%"
                        placeholder="请选择"
                >
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    >
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')"
                >立即创建
                </el-button
                >
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import main from "../js/main";

    export default {
        name: "Connection",
        data() {
            return {
                options: [
                    {
                        value: "mysql",
                        label: "mysql",
                    },
                    {
                        value: "dm",
                        label: "达梦",
                    },
                    {
                        value: "sqlserver",
                        label: "Sql Server",
                    },
                    {
                        label: 'postgresql',
                        value: 'postgresql'
                    }
                ],
                connection: {
                    name: "aa",
                    host: "127.0.0.1",
                    port: "5001",
                    userName: "lzz",
                    passWord: "123456",
                    type: "postgresql",
                    dataBaseName: "lzz",
                },
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        const data = await main.createConnection(this.connection);

                        this.$emit("click", {
                            name: data.name,
                            type: data.type,
                            sourceId: data.sourceId,
                            connection: data,
                            leaf: true,
                        });
                    } else {
                        console.log("error submit!!");
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
        },
    };
</script>

<style scoped></style>
