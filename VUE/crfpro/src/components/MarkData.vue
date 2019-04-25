<template>
    <div id="mark-data">
        <div id="mark-data-headtitle">
            <h5 style="margin-right:20px;">文件名:</h5>
            <div id="mark-data-headtitle-input-box" @dblclick="changeDiv2Input()" ref="inputDiv"></div>
        </div>
        <div id="mark-data-inner" ref="svgContainer"></div>
        <div id="radio-part" v-if="isSelecting"></div>
        <transition name="el-fade-in">
            <div id="radio-label" class="radio-box" v-if="showLabelCategoriesDialog">
                <span class="radio-headline">请选择标注标签</span>
                <el-radio-group v-model="selectedLabelCategory">
                    <el-radio v-for="category in this.labelCategories"
                                            :key="category.id"
                                            :label="category.text"
                                            :value="category.id"
                                            v-bind:style="{color:category.color}"
                                            class="radio-box-el-radio"></el-radio>
                </el-radio-group>
                <div class="radio-btn okbtn" @click="createLabel">
                    确定
                </div>
                <div class="radio-btn cancelbtn" @click="showLabelCategoriesDialog = false; isSelecting=false;">
                    取消
                </div>
            </div>
        </transition>

        <transition name="el-fade-in">
            <div id="radio-conntection" class="radio-box" v-if="showConnectionCategoriesDialog">
                <span class="radio-headline">请选择标注关系</span>
                <el-radio-group v-model="selectedConnectionCategory">
                    <el-radio v-for="category in this.connectionCategories"
                                            :key="category.id"
                                            :label="category.text"
                                            :value="category.id"
                                            class="radio-box-el-radio"></el-radio>
                </el-radio-group>
                <div class="radio-btn okbtn" @click="createConnection">
                    确定
                </div>
                <div class="radio-btn cancelbtn" @click="showConnectionCategoriesDialog = false; isSelecting=false;">
                    取消
                </div>
            </div>
        </transition>
        
    </div>
</template>

<script>
import {Annotator, Action} from 'poplar-annotation'
import {mapState} from 'vuex'

export default {
    name: 'MarkData',
    data() {
        return{
            selectedLabelCategory: null,
            showLabelCategoriesDialog: false,
            selectedConnectionCategory: null,
            showConnectionCategoriesDialog: false,
            annotator: null,
            startIndex: -1,
            endIndex: -1,
            first: -1,
            second: -1,
            isSelecting:false
        }
    },
    methods: {
        constructAnnotator: function (data) {
            this.annotator = new Annotator(data, document.getElementById('mark-data-inner'));
            this.annotator.on('textSelected', (startIndex, endIndex) => {
                this.startIndex = startIndex;
                this.endIndex = endIndex;
                this.showLabelCategoriesDialog = true;
                this.isSelecting = true;
                // console.log(this.startIndex,this.endIndex);
            });
            this.annotator.on('labelRightClicked', (id) => {
                this.annotator.applyAction(Action.Label.Delete(id));
                this.getCode();
            });
            this.annotator.on('connectionRightClicked', (id) => {
                this.annotator.applyAction(Action.Connection.Delete(id));
                this.getCode();
            });
            this.annotator.on('twoLabelsClicked', (first, second) => {
                this.first = first;
                this.second = second;
                this.showConnectionCategoriesDialog = true;
                this.isSelecting = true;
            });
            // setTimeout(() => this.getCode(), 100);
        },
        useData() {
            if (this.annotator !== null) {
                this.$refs.svgContainer.innerHTML = "";
            }
            this.constructAnnotator(this.$store.getters.getContent);
        },
        createLabel() {
            let curId = 0;
            if (this.annotator === null) curId = 0;
            for (let [_, category] of this.annotator.store.labelCategoryRepo) {
                if(category.text == this.selectedLabelCategory){
                    curId = _ ;
                    break;
                }
            }
            // console.log(curId);
            this.annotator.applyAction(Action.Label.Create(curId, this.startIndex, this.endIndex));
            this.showLabelCategoriesDialog = false;
            this.isSelecting=false;
            this.getCode();
        },
        createConnection() {
            let curId = 0;
            if (this.annotator === null) curId = 0;
            for (let [_, category] of this.annotator.store.connectionCategoryRepo) {
                // console.log(category)
                if(category.text == this.selectedConnectionCategory){
                    curId = _ ;
                    break;
                }
            }
            this.annotator.applyAction(Action.Connection.Create(curId, this.first, this.second));
            this.showConnectionCategoriesDialog = false;
            this.isSelecting=false;
            this.getCode();
        },
        getCode() {
            if (this.annotator === null) {
                this.code = '';
            }
            this.$store.commit('updateIsChangeMarkData',false);
            this.$store.commit('updateContent',JSON.stringify(this.annotator.store.json," ",2));
        },
        changeDiv2Input(){
            let self = this;
            let dom = this.$refs.inputDiv; //获取当前节点元素
            let oldVal = dom.innerHTML;//获取当前值
            dom.innerHTML ="";//
            let inputHtml = document.createElement("input");//创建input标签
            inputHtml.setAttribute("type","text");
            inputHtml.setAttribute("value",oldVal);
            inputHtml.getAttribute("class");

            inputHtml.style.width="100%";
            inputHtml.style.height="80%";
            inputHtml.style.margin="0";
            inputHtml.style.padding="0 10px";
            inputHtml.style.outline="none";
            // inputHtml.style.border="none";
            inputHtml.style.boxSizing="border-box";
            
            dom.appendChild(inputHtml);
            inputHtml.focus();//使光标在输入框内
            inputHtml.select();//默认选择内容
            inputHtml.onblur = function(e){
        　　　　　　//绑定失去焦点事件
                let newVal = e.target.value;
                let tmpFilename = newVal;
                let tmpFilenameList = tmpFilename.split(".");
                let curFileName = tmpFilename.substr(tmpFilename.length - tmpFilenameList[tmpFilenameList.length - 1].length - 1,tmpFilename.length);
                // console.log(curFileName);
                if(curFileName == ".json"){
                    let reg = /[a-zA-Z0-9_\u4E00-\u9FA5]{0,19}$/;
                    if(reg.test(tmpFilename.substr(0,tmpFilename.length - tmpFilenameList[tmpFilenameList.length - 1].length - 1))){
                        if(self.$store.getters.getId != -1){
                            let info = {
                                "id": self.$store.getters.getId,
                                "newFilename":newVal
                            };
                            let url = "http://localhost:8079/file/updateDataFilename";
                            self.axios.put(url,info)
                            .then(function (response) {
                                self.$message({
                                    title: '成功',
                                    message: '文件名修改成功',
                                    type: 'success'
                                });
                                self.$store.commit('updateFilename',newVal);
                                // console.log(newVal);
                                dom.innerHTML = newVal;
                            })
                            .catch(function (error) {
                                self.$message({
                                    title: '失败',
                                    message: '文件名修改失败',
                                    type: 'error'
                                });
                                dom.innerHTML = oldVal;
                            })
                        }
                        else{
                            self.$message({
                                title: '成功',
                                message: '文件名修改成功',
                                type: 'success'
                            });
                            self.$store.commit('updateFilename',newVal);
                            dom.innerHTML = newVal;
                        }
                    }
                    else{
                        self.$message({
                            title: '失败',
                            message: '文件名不合法',
                            type: 'error'
                        });
                        dom.innerHTML = oldVal;
                    }
                }
                else{
                    self.$message({
                        title: '失败',
                        message: '文件名必须以".json"结尾',
                        type: 'error'
                    });
                    dom.innerHTML = oldVal;
                }
            }
        }
    },
    computed: {
        labelCategories() {
            if (this.annotator === null) return [];
            let result = [];
            for (let [_, category] of this.annotator.store.labelCategoryRepo) {
                result.push(category);
            }
            return result;
        },
        connectionCategories() {
            if (this.annotator === null) return [];
            let result = [];
            for (let [_, category] of this.annotator.store.connectionCategoryRepo) {
                result.push(category);
            }
            return result;
        },
        ...mapState([
            'content',
            'filename',
            'id'
        ])
    },
    watch: {
        content(newVal, oldVal) {
            if(this.$store.getters.getIsChangeMarkData == true){
                this.useData();
            }
            else {
                console.log("MarkData处操作，不进行本地修改");
            }
        },
        filename() {
            this.$refs.inputDiv.innerHTML = this.$store.getters.getFilename;
        }
    },
    mounted() {
        this.$refs.inputDiv.innerHTML = this.$store.getters.getFilename;
        this.useData();
    },
    destroyed() {

    }
}
</script>
<style scoped>

#mark-data {
    width: 70%;
    height: 100%;
    box-sizing: border-box;
    border: 1px solid lightgray;

}
#mark-data #mark-data-inner {
    color: black;
    overflow: scroll;
    width: 100%;
    height: calc(100% - 40px);
    padding: 10px;
    box-sizing: border-box;
    white-space: pre-wrap;
}
#mark-data-headtitle {
    width: 100%;
    height: 40px;
    /* background: red; */
    font-size: 20px;
    color: #000000;
    display: flex;
    align-items: center;
    padding: 0 10px;
    background: rgb(230, 228, 228);
    box-sizing: border-box;
    border-bottom: 1px solid lightgray;
}
#mark-data-headtitle-input-box {
    width: calc(100% - 80px);
    height: 100%;
    display: flex;
    align-items: center;
}

svg > text tspan {
	font-size: 20px;
}
#radio-part {
    width: 100vw;
    height: 100vh;
    background-color: lightgray;
    opacity: 0.5;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 999;
}
.radio-box {
    margin-bottom: 10px;
    padding: 30px;
    box-sizing: border-box;
    width: 300px;
    border-radius: 15px;
    background-color: white;
    position: absolute;
    top:calc(50vh - 200px);
    left:calc(50vw - 150px);
    z-index: 1000;
    border: 1px solid lightskyblue;
}
.radio-headline {
    color: #000000;
    padding: 5px;
    box-sizing: border-box;
    width: 100%;
    font-size: 20px;
}
.el-radio-group {
    width: 100%;
    max-height: 200px;
    overflow: scroll;
}
.radio-box-el-radio {
    width: 100%;
    color: black;
    display: flex;
    margin: 10px 0 10px 0;
    padding: 5px;
    box-sizing: border-box;
    border-radius: 8px;
}
.radio-box-el-radio:hover {
    background-color:lightskyblue;
}

.el-radio >>> .el-radio__inner {
    width: 20px;
    height: 20px;
}
.el-radio >>> .el-radio__label {
    font-size:20px; 
}
.radio-btn {
    width: 100%;
    height: 30px;
    margin: 15px 0 15px 0;
    border: 1px solid lightskyblue;
    box-sizing: border-box;
    border-radius: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 18px;
}
.radio-btn:hover {
    cursor: pointer;
    /* background-color: red; */
}
.okbtn {
    color:#43a047;
}
.okbtn:hover {
    background-color: #43a047;
    color: #ffffff;
}
.cancelbtn {
    color:#1e88e5;
}
.cancelbtn:hover {
    background-color: #1e88e5;
    color: #ffffff;
}

</style>

