
const getDataBase = async (sourceId) => {
    return await window.axios({
        url: 'v1/database/' + sourceId,
        metnod: 'get'
    })
}
const createConnection = async (connection) => {
    const data = await window.axios({
        method: "post",
        url: "connection/",
        data: connection
    })
    return data
}

const getColumns = async (sourceId, tableName) => {
// eslint-disable-next-line no-debugger
debugger
    const data = await window.axios({
        method: 'post',
        url: 'v1/columns/' + sourceId,
        data: {tableName}
    })
    return data;

}

const getTables = async (sourceId, dataBaseName) => {

    const data = await window.axios({
        method: 'get',
        url: 'v1/table/' + sourceId + "/" + dataBaseName
    })
    return data
}

 const  getTableList=async (sqlVo)=>{
    const  data= await  window.axios({
         method:'post',
        url:'v1/table/list/',
        data:sqlVo
    })
     return  data;
 }
const main = {
    createConnection, getDataBase, getColumns, getTables,getTableList
}
export default main
