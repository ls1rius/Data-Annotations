<template>
    <div id="main-page">
        <top-part></top-part>
        <div id="main-mark-part">
            <mark-data></mark-data>
            <show-data></show-data>
        </div>
    </div>
</template>

<script>
import MarkData from '@/components/MarkData'
import ShowData from '@/components/ShowData'
import TopPart from '@/components/TopPart'

export default { 
    name: 'MainPage',
    components:{
        'mark-data': MarkData,
        'show-data': ShowData,
        'top-part':TopPart
    },
    data() {
        return {
            defaultJson: "{\"content\":\"\",\"labelCategories\":[{\"id\":\"0\",\"text\":\"时间实体\",\"color\":\"#7ebf50\",\"border-color\":\"#b5d7e4\"},{\"id\":\"1\",\"text\":\"事件实体\",\"color\":\"#579ef8\",\"border-color\":\"#b5d7e4\"}],\"labels\":[],\"connectionCategories\":[{\"id\":\"0\",\"text\":\"在...之前\"},{\"id\":\"1\",\"text\":\"在...之后\"},{\"id\":\"2\",\"text\":\"在...期间\"}],\"connections\":[]}"
        }
    },
    methods: {
        getDefaultData(data) {
            let self = this;
            let url = "http://localhost:8079/data/getDefaultData";
            this.axios.get(url,"")
            .then(function (response) {
                let dealedDataTmp = JSON.parse(self.defaultJson);
                dealedDataTmp.content = response.data.content;
                dealedDataTmp.labelCategories = response.data.labelCategories;
                dealedDataTmp.labels = response.data.labels;
                dealedDataTmp.connectionCategories = response.data.connectionCategories;
                dealedDataTmp.connections = response.data.connections;
                self.$store.commit('updateIsChangeMarkData',true);
                self.$store.commit('updateContent',JSON.stringify(dealedDataTmp," ",2));
                self.$message({
                    title: '成功',
                    message: '初始数据加载成功',
                    type: 'success'
                });
            })
            .catch(function (error) {
                self.$message({
                    title: '失败',
                    message: '初始数据加载失败',
                    type: 'error'
                });
            })
        }
    },
    mounted() {
        this.getDefaultData("");
    }
}
</script>
<style scoped>
#main-page {
    width: 100vw;
    height: 100vh;
    box-sizing: border-box;
}

#main-mark-part {
    width: 100vw;
    height: calc(100vh - 70px);
    display: flex;
    flex-wrap: nowrap;
    box-sizing: border-box;
}

</style>
