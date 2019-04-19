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
            
        }
    },
    methods: {

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
