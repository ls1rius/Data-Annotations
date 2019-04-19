<template>
    <div id="datatable-page">
        <div id="datatable-serach-box">
            <div id="datatable-serach-input">
                <input @keyup.enter="handleSearch" type="text" id="datatable-serach-input-box" ref="searchInputBox">
                <div id="datatable-search-input-btn" @click="handleSearch">搜索</div>
            </div>
        </div>
        <div id="datatable-box">
            <el-table
                @filter-change="handleFilterChange"
                @sort-change="handleSortChange"
                highlight-current-row
                :header-cell-style="{padding:'3px 0'}"
                :cell-style="{padding:'3px 0'}"
                height="100%"
                id="datatable-main"
                ref="filterTable"
                :data="tableData"
                style="width: 100%; font-size: 14px;">
                <el-table-column
                width="90"
                column-key="date"
                prop="date"
                label="日期"
                sortable
                :filters="dateList"
                >
                </el-table-column>
                <el-table-column
                width="80"
                column-key="time"
                prop="time"
                label="时间">
                </el-table-column>
                <el-table-column
                column-key="filename"
                prop="filename"
                label="文档名称">
                </el-table-column>
                <el-table-column

                width="130"
                label="操作">
                    <template slot-scope="scope">
                        <el-button
                        size="mini"
                        type="primary"
                        @click="handleEdit(scope.$index, tableData)">编辑</el-button>
                        <el-button
                        size="mini"
                        type="success"
                        @click="handleDownload(scope.$index, tableData)">下载</el-button>
                        <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, tableData)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div id="pagination-part">
            <div class="block">
                <el-pagination
                small
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next, jumper"
                :total="totalDataNum">
                </el-pagination>
            </div>
        </div>
    </div>
</template>
<script>

export default {
    name:'DataTablePart',
    data() {
        return {
            tableData: [],
            dateList: [],
            currentPage: 1,
            pageSize: 15,
            totalDataNum: 100,
            choosedTime: "",
            filters:[],
            sortStatus: 0,
            isSearching: false,
            searchFile: ""
        }
    },
    methods: {
        handleFilterChange(fil) {
            this.currentPage = 1;
            this.filters = fil.date;
            this.getFileList();
        },
        handleSortChange(curStatus) {
            if(curStatus.order=="ascending"){
                this.sortStatus = 1;
            }
            else if(curStatus.order=="descending"){
                this.sortStatus = -1;
            }
            else{
                this.sortStatus = 0;
            }
            this.getFileList();
            // console.log(this.sortStatus);
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.currentPage = val;
            this.getFileList();
        },
        handleEdit(index,rows){
            let info = {
                "id": rows[index].id
            };
            let self = this;
            let url = "http://localhost:8079/file/getOneData";
            this.axios.post(url,info)
            .then(function (response) {
                self.$store.commit('updateId',response.data.id);
                self.$store.commit('updateFilename',response.data.filename);
                self.$store.commit('updateContent',JSON.parse(response.data.content));
                self.$store.commit('updateIsChangeMarkData',true);
                self.$message({
                    title: '成功',
                    message: '数据获取成功',
                    type: 'success'
                });
            })
            .catch(function (error) {
                self.$message({
                    title: '失败',
                    message: '数据获取失败',
                    type: 'error'
                });
            })
        },
        handleDownload(index,rows){
            let info = {
                "id": rows[index].id
            };
            let self = this;
            let url = "http://localhost:8079/file/getOneData";
            this.axios.post(url,info)
            .then(function (response) {

                let eleLink = document.createElement('a');
                eleLink.download = response.data.filename;
                eleLink.style.display = 'none';
                let blob = new Blob([response.data.content]);
                eleLink.href = URL.createObjectURL(blob);
                document.body.appendChild(eleLink);
                eleLink.click();
                document.body.removeChild(eleLink);

                self.$message({
                    title: '成功',
                    message: '下载成功',
                    type: 'success'
                });
            })
            .catch(function (error) {
                self.$message({
                    title: '失败',
                    message: '下载失败',
                    type: 'error'
                });
            })
        },
        handleDelete(index,rows) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                let info = {
                    "id": rows[index].id
                };
                let self = this;
                let url = "http://localhost:8079/file/deleteData";
                this.axios.delete(url,{data:{info}})
                .then(function (response) {
                    rows.splice(index, 1);
                    self.$message({
                        title: '成功',
                        type: 'success',
                        message: '删除成功'
                    });
                })
                .catch(function (error) {
                    self.$message({
                        title: '失败',
                        message: '删除失败',
                        type: 'error'
                    });
                })
            }).catch(() => {
                this.$message({
                    title:'信息',
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        getFileList(){
            let info = {
                "dateList": this.filters,
                "currentPage": this.currentPage,
                "pageSize": this.pageSize,
                "sortStatus": this.sortStatus,
                "isSearching": this.isSearching,
                "searchFile": this.searchFile
            };
            let self = this;
            let url = "http://localhost:8079/file/getFileList";
            this.axios.post(url,info)
            .then(function (response) {
                self.tableData = [];
                self.totalDataNum = response.data.fileCount;
                self.tableData = response.data.fileList;
            })
            .catch(function (error) {
                self.$message({
                    title: '失败',
                    message: '数据获取失败',
                    type: 'error'
                });
            })
        },
        getDateList(){
            let info = {};
            let self = this;
            let url = "http://localhost:8079/file/getDistinctDate";
            this.axios.get(url,{params:info})
            .then(function (response) {
                self.dateList = [];
                let len = response.data.length;
                for(let i = 0;i < len;i++){
                    let obj = {text: response.data[i], value: response.data[i]};
                    self.dateList.push(obj);
                }
            })
            .catch(function (error) {
                self.$message({
                    title: '失败',
                    message: '数据获取失败',
                    type: 'error'
                });
            })
        },
        handleSearch(){
            this.currentPage = 1;
            console.log(this.$refs.searchInputBox.value);
            this.searchFile = this.$refs.searchInputBox.value;
            if(this.isSearching.length == 0)
                this.isSearching = false;
            else
                this.isSearching = true;
            this.getFileList();
            this.isSearching = false;
        }
    },
    mounted() {
        this.getFileList();
        this.getDateList();
    }
}
</script>

<style scoped>

#datatable-page {
    width: 100%;
    height: 100%;
    box-sizing: border-box;
}
#datatable-serach-box {
    width: 100%;
    height: 40px;
    border-bottom: 1px solid lightgray;
    box-sizing: border-box;
}
#datatable-serach-input {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
}
#datatable-serach-input-box {
    width: 70%;
    height: 30px;;
    padding: 0 5px;
    margin: 0;
    border: 1px solid lightgray;
    outline: none;
    box-sizing: border-box;
    font-size: 14px;
}
#datatable-search-input-btn {
    width: 60px;
    height: 30px;
    box-sizing: border-box;
    border: 1px solid lightgray;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 16px;
    cursor: pointer;
}
#datatable-box {
    width: 100%;
    height: calc(100% - 80px);
    /* padding: 0 20px; */
    box-sizing: border-box;
    border-bottom: 1px solid lightgray;

}
#pagination-part {
    width: 100%;
    height: 40px;
    display: flex;
    justify-content: center;
    align-content: center;
    align-items: center;
    /* padding: 0 20px; */
    box-sizing: border-box;
    /* border: 1px solid lightgray; */
}
#datatable-main {
    height: 100%; 
    overflow: scroll;
    box-sizing: border-box;
}
.el-table::before {
    z-index: inherit;
}

.el-button--mini {
    margin: 5px 0;
    padding: 3px;
    font-size: 12px;
}
.el-table th div  {
    display: flex;
    justify-content: center;
}
</style>

