<template>
    <div id="mark-lists">
        <div class="mark-list">
            <div class="headtitle"  v-bind:style="{background:timeColor}">
                <div class="headtitle-left">
                    <div class="count-circle" style="background: #ffffff;"  v-bind:style="{color:timeColor}">{{timeTableData.length}}</div>
                    <p>时间</p>
                </div>
                <!-- <div class="headtitle-right">
                    <i class="el-icon-edit"></i>
                    <p>编辑</p>
                </div> -->
            </div>
            <div class="mark-list-data">
                <el-table
                    :data="timeTableData"
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
                    label="时间">
                    </el-table-column>
                </el-table>
            </div>
        </div>

        <div class="mark-list">
            <div class="headtitle" v-bind:style="{background:eventColor}">
                <div class="headtitle-left">
                    <div class="count-circle" style="background: #ffffff" v-bind:style="{color:eventColor}">{{eventTableData.length}}</div>
                    <p>事件</p>
                </div>
                <!-- <div class="headtitle-right">
                    <i class="el-icon-edit"></i>
                    <p>编辑</p>
                </div> -->
            </div>
            <div class="mark-list-data">
                <el-table
                    :data="eventTableData"
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
                    label="事件">
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
            timeColor:'#7ebf50',
            eventColor:'#579ef8',
            relationColor:'#dda450',
            eventTableData: [],
            timeTableData: [],
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
            this.timeTableData = [];
            this.eventTableData = [];
            this.relationTableData = [];
            let newValueJson = JSON.parse(this.$store.getters.getContent);
            // console.log(newValueJson.labelCategories[0].color);
            this.timeColor = newValueJson.labelCategories[0].color;
            this.eventColor = newValueJson.labelCategories[1].color;
            let content = newValueJson.content;
            let labels = newValueJson.labels;
            let connections = newValueJson.connections;
            let connectionCategories = newValueJson.connectionCategories;
            let timeLabel = 0;
            let eventLabel = 0;
            for(let i = 0;i<labels.length;i++){
                if(labels[i].categoryId == 0){
                    let obj={id:'',data:''};
                    // obj.id = labels[i].id;
                    obj.id = timeLabel + 1;
                    obj.data = content.substr(labels[i].startIndex, labels[i].endIndex - labels[i].startIndex);
                    this.timeTableData.push(obj);
                    timeLabel++;
                }
                else if(labels[i].categoryId == 1){
                    let obj={id:'',data:''};
                    // obj.id = labels[i].id;
                    obj.id = eventLabel + 1;
                    obj.data = content.substr(labels[i].startIndex, labels[i].endIndex - labels[i].startIndex);
                    this.eventTableData.push(obj);
                    eventLabel++;
                }
            }
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

