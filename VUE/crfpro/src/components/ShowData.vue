<template>
    <div id="show-data">
        <el-menu id="select-menu" :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="0">标注列表</el-menu-item>
            <el-menu-item index="1">代码</el-menu-item>
            <el-menu-item index="2">文档列表</el-menu-item>
        </el-menu>
        <div id="show-data-inner-box">
            <div id="show-data-mark" class="show-data-inner" v-show="activeIndex==0" style="padding:10px;">
                <mark-lists></mark-lists>
            </div>
            <pre id="show-data-code" class="show-data-inner" v-show="activeIndex==1" ref="code" style="padding:10px;"></pre>
            <div id="show-data-datatable" class="show-data-inner" v-if="activeIndex==2">
                <datatable-part></datatable-part>  
            </div>
        </div>
    </div>
</template>

<script>
import hljs from 'highlight.js'
import DataTablePart from '@/components/DataTablePart'
import MarkLists from '@/components/MarkLists'
import { mapState } from 'vuex';
export default {
    name: 'ShowData',
    data() {
        return {
            activeIndex: '0',
        }
    },
    components: {
        'datatable-part': DataTablePart,
        'mark-lists': MarkLists
    },
    computed: {
        ...mapState([
            'content'
        ])
    },
    watch: {
        content(newValue, oldValue) {
            // console.log(newValue);
            this.$refs.code.innerHTML = newValue;
            hljs.highlightBlock(this.$refs.code);
        }
    },
    methods: {
        handleSelect(key, keyPath) {
            this.activeIndex = key;
        }
    },
    mounted() {
        // console.log(this.$store.getters.getContent);
        this.$refs.code.innerHTML = this.$store.getters.getContent;
        hljs.highlightBlock(this.$refs.code);
    }
}
</script>
<style scoped>
#show-data {
    width: 30%;
    height: 100%;
    box-sizing: border-box;
    border: 1px solid lightgray;

}
#show-data-inner-box {
    width: 100%;
    height: calc(100% - 42px);
    box-sizing: border-box;
}
#show-data .show-data-inner{
    overflow: scroll;
    width: 100%;
    height: 100%;
    padding: 0 10px;
    box-sizing: border-box;
}
pre {
    margin: 0;
    white-space: pre-wrap;
}
#select-menu {
    width: 100%;
    height: 40px;
    border-bottom: 1px solid lightgray;
}
#select-menu >>> li {
    width: 33%;
    height: 100%;
    display: flex;
    align-content: center;
    justify-content: center;
    align-items: center;
    font-size: 16px;
}
#show-data #show-data-code {
    color:#ff619d;
}
</style>

