<template>
    <div id="mark-lists">
        <div class="mark-list" v-for="(item,index) in this.labelCategories" :key="index">
            <div class="headtitle"  v-bind:style="{background:item.color}">
                <div class="headtitle-left">
                    <div class="count-circle" style="background: #ffffff;" v-bind:style="{color:item.color}">{{entityTableData[index].length}}</div>
                    <p>{{item.text}}</p>
                </div>
                <!-- <div class="headtitle-right">
                    <i class="el-icon-edit"></i>
                    <p>编辑</p>
                </div> -->
            </div>
            <div class="mark-list-data">
                <el-table
                    :data="entityTableData[index]"
                    height="100%"
                    border
                    :header-cell-style="{padding:'0'}"
                    :cell-style="{padding:'0'}"
                    style="width: 100%;font-size:12px;">
                    <el-table-column
                    prop="id"
                    label="ID"
                    width="50">
                    </el-table-column>
                    <el-table-column
                    prop="data"
                    :label="item.text">
                    </el-table-column>
                </el-table>
            </div>
        </div>

        <!-- <div class="mark-list">
            <div class="headtitle" v-bind:style="{background:relationColor}">
                <div class="headtitle-left">
                    <div class="count-circle" style="background: #ffffff;" v-bind:style="{color:relationColor}">{{relationTableData.length}}</div>
                    <p>关系</p>
                </div>
                <div class="headtitle-right">
                    <i class="el-icon-edit"></i>
                    <p>编辑</p>
                </div>
            </div>
            <div class="mark-list-data">
                <el-table
                    :data="relationTableData"
                    height="100%"
                    border
                    :header-cell-style="{padding:'0'}"
                    :cell-style="{padding:'0'}"
                    style="width: 100%;font-size:12px;">
                    <el-table-column
                    prop="id"
                    label="ID"
                    width="50">
                    </el-table-column>
                    <el-table-column
                    prop="fromId"
                    width="50"
                    label="起始标签">
                    </el-table-column>
                    <el-table-column
                    prop="toId"
                    width="50"
                    label="结束标签">
                    </el-table-column>
                    <el-table-column
                    prop="data"
                    label="关系类型">
                    </el-table-column>
                </el-table>
            </div>
        </div> -->

    </div>
</template>
<style scoped>
#mark-lists {
    width: 100%;
    height: 100%;
}
.mark-list {
    width: 100%;
    /* height: 32%; */
    height: 49%;
    margin-top: 2%;
    /* border-bottom: 1px solid lightgray; */
}
#mark-lists .mark-list:first-child {
    margin-top:0;
}
.headtitle {
    color: #ffffff;
    font-size: 14px;
    width: 100%;
    height: 20px;
    box-sizing: border-box;
    display: flex;
    justify-content: space-between;
    align-content: center;
    align-items: center;
    padding: 0 3px;
}
.headtitle .headtitle-left {
    display: flex;
    align-items: center;
}
.headtitle .headtitle-right {
    display: flex;
    align-items: center;
    font-size:12px;
}
.headtitle .headtitle-right:hover {
    cursor: pointer;
}
.headtitle .headtitle-left .count-circle {
    border-radius: 50%;
    width: 15px;
    height: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.headtitle p {
    margin: 0;
    padding: 0;
    margin-left: 3px;
}
.mark-list-data {
    border: 1px solid lightgray;
    box-sizing: border-box;
    width: 100%;
    height: calc(100% - 20px);
}
.testStyle {
    height: 20px;;
}
</style>
<script>
import {mapState} from 'vuex'
export default {
    name:'MarkLists',
    data() {
        return {
            labelCategories: [],
            relationColor:'#dda450',
            entityTableData: [],
            relationTableData: []
        }
    },
    computed: {
        ...mapState([
            'content'
        ])
    },
    watch: {
        content() {
            // console.log(newValue);
            this.relationTableData = [];
            let newValueJson = JSON.parse(this.$store.getters.getContent);
            this.labelCategories = newValueJson.labelCategories;
            this.entityTableData = [];
            for (let i =0;i<this.labelCategories.length;i++){
                this.entityTableData.push(new Array())
            }
            // console.log(this.entityTableData);
            let content = newValueJson.content;
            let labels = newValueJson.labels;
            let connections = newValueJson.connections;
            let connectionCategories = newValueJson.connectionCategories;
            for(let i = 0;i<labels.length;i++){
                let obj={id:'',data:''};
                obj.id = this.entityTableData[labels[i].categoryId].length + 1;
                obj.data = content.substr(labels[i].startIndex, labels[i].endIndex - labels[i].startIndex);
                this.entityTableData[labels[i].categoryId].push(obj);
            }
            // console.log(this.entityTableData);
            for(let i = 0;i<connections.length;i++){
                let obj={id:'',fromId:'',toId:'',data:'未知'};
                // obj.id = connections[i].id;
                obj.id = i + 1;
                for(let j = 0;j<connectionCategories.length;j++){
                    if(connections[i].categoryId==connectionCategories[j].id){
                        obj.data = connectionCategories[j].text;
                    }
                }
                obj.fromId = connections[i].fromId;
                obj.toId = connections[i].toId;
                this.relationTableData.push(obj);
            }
        }
    }
}
</script>

