<template>
    <div id="top-part">
        <div class="loading-box"  v-loading="isLoading" v-if="isLoading">
        </div>
        <h1 style="color:#000000;width:70%;">病历文本数据结构化处理系统
            <p style="font-size:20px;">（当前模型F-score:88.63%）</p>
        </h1>
        <div id="top-btn-part">
            <el-button size="mini" id="upload-btn" class="top-btn" type="primary" >
                <p>上传</p>
                <input type="file" id="upload-input" accept="text/plain" v-on:change="handleUpload" value="上传">
            </el-button>
            <el-button size="mini" id="recognize-btn" type="primary" class="top-btn" v-on:click="handleRecognize">识别</el-button>
            <el-button size="mini" id="savebtn" type="primary" class="top-btn" v-on:click="handleSave">保存</el-button>
            <el-button size="mini" id="download-btn" type="success" class="top-btn" v-on:click="handleDownload">下载</el-button>
        </div>
    </div>
</template>
<script>
import axios from 'axios'

export default {
    name:'TopPart',
    data() {
        return{
            isLoading: false,
            defaultJson: "{\"content\":\"\",\"labelCategories\":[{\"id\":\"0\",\"text\":\"时间实体\",\"color\":\"#7ebf50\",\"borderColor\":\"#b5d7e4\"},{\"id\":\"1\",\"text\":\"事件实体\",\"color\":\"#579ef8\",\"borderColor\":\"#b5d7e4\"}],\"labels\":[],\"connectionCategories\":[{\"id\":\"0\",\"text\":\"在...之前\"},{\"id\":\"1\",\"text\":\"在...之后\"},{\"id\":\"2\",\"text\":\"在...期间\"}],\"connections\":[]}"
        }
    },
    methods: {
        handleDownload() {
            let eleLink = document.createElement('a');
            eleLink.download = this.$store.getters.getFilename;
            // eleLink.download = 'data.json';
            eleLink.style.display = 'none';
            let blob = new Blob([JSON.stringify(this.$store.getters.getContent," ",2)]);
            // let blob = new Blob([this.waitForDownloadData]);
            eleLink.href = URL.createObjectURL(blob);
            document.body.appendChild(eleLink);
            eleLink.click();
            document.body.removeChild(eleLink);
        },
        handleUpload(e) {
            this.isLoading = true;
            let reader = new FileReader();
            let tmpFilename = e.target.files[0].name;
            let uploadTmpData = JSON.parse(this.defaultJson);
            reader.readAsText(e.target.files[0]);
            reader.onload = (event) => {
                let tmpFilenameList = tmpFilename.split(".");
                this.$store.commit('updateFilename',tmpFilename.substr(0,tmpFilename.length - tmpFilenameList[tmpFilenameList.length - 1].length - 1) + ".json");
                this.$store.commit('updateId',-1);
                uploadTmpData.content = event.target.result;
                this.$store.commit('updateContent',JSON.stringify(uploadTmpData," ",2));
                this.$store.commit('updateIsChangeMarkData',true);
                this.$message({
                    title: '成功',
                    message: '文件上传成功',
                    type: 'success'
                });
                this.isLoading = false;
            }
        },
        handleRecognize() {
            this.isLoading = true;
            this.requestForRecognize(JSON.parse(this.$store.getters.getContent).content);
        },
        handleSave() {
            let info = {
                "content": JSON.stringify(this.$store.getters.getContent," ",2),
                "filename": this.$store.getters.getFilename
            };
            let self = this;
            self.isLoading = true;
            let url = "http://localhost:8079/file/saveText";
            this.axios.post(url,info)
            .then(function (response) {
                self.$message({
                    title: '成功',
                    message: '文件已保存',
                    type: 'success'
                });
            })
            .catch(function (error) {
                // console.log(error);
                self.$message({
                    title: '失败',
                    message: '文件保存失败',
                    type: 'error'
                });
            })
            self.isLoading = false;
        },
        requestForRecognize(data) {
            let info = {
                "content": data
            };
            let self = this;
            let url = "http://localhost:8079/data/dealData";
            // this.axios.get(url,{params:info})
            // .then(function (response) {
            //     console.log(response);
            // })
            // .catch(function (error) {
            //     console.log(error);
            // })
            this.axios.post(url,info)
            .then(function (response) {
                let dealedDataTmp = JSON.parse(self.defaultJson);
                dealedDataTmp.content = response.data.content;
                dealedDataTmp.labelCategories = response.data.labelCategories;
                dealedDataTmp.labels = response.data.labels;
                dealedDataTmp.connectionCategories = response.data.connectionCategories;
                dealedDataTmp.connections = response.data.connections;
                self.$store.commit('updateContent',JSON.stringify(dealedDataTmp," ",2));
                self.$store.commit('updateIsChangeMarkData',true);
                self.$message({
                    title: '成功',
                    message: '识别成功',
                    type: 'success'
                });
                self.isLoading = false;
            })
            .catch(function (error) {
                self.$message({
                    title: '失败',
                    message: '识别失败',
                    type: 'error'
                });
                self.isLoading = false;
            })
        }
    }
}
</script>


<style scoped>
#top-part {
    width: 100vw;
    display: flex;
    height: 70px;
    justify-content: space-between;
    align-items: center;
    /* background-color: #1e88e5; */
    box-sizing: border-box;
    padding: 0 10px;
    border: 1px solid lightgray;
}
.loading-box{
    width: 100vw;
    height: 100vh;
    /* background-color: lightgray; */
    /* opacity: 0.5; */
    position: absolute;
    top: 0;
    left: 0;
    z-index: 999;
}
#top-btn-part {
    width: 30%;
    height: 100%;
    display: flex;
    justify-content: space-around;
    align-content: center;
    align-items: center;
}
.top-btn {
    justify-content: center;
    align-items: center;
    border-radius: 5px;
    border: 1px solid lightblue;
    font-size: 16px;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    display: flex;
    width: 80px;
    /* margin: 0 10px 0 10px; */
    height: 35px;
}
.top-btn:hover {
    cursor: pointer;
}
#upload-btn {
    position: relative;
}
#upload-btn >>> span {
    width: 100%;
    height: 35px;
}
#upload-input {
    position: absolute;
    width: 100%;
    height: 100%;
    opacity: 0;
    cursor: pointer;
    z-index: 1002;
    display: flex;
}
#upload-btn p {
    padding: 0;
    margin: 0;
    position: absolute;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1001;
    cursor: pointer;
}
</style>
