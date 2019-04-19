import Vue from 'vue'
import Router from 'vue-router'
import MainPage from '@/views/mainPage'
import DataTablePart from '@/components/DataTablePart'
import HelloWorld from '@/components/HelloWorld'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'mainPage',
      component: MainPage
    },
    {
      path: '/mainPage',
      name: 'mainPage',
      component: MainPage
    },
    {
      path: '/dataTablePart',
      name: 'dataTablePart',
      component: DataTablePart
    },
    {
      path: '/helloWorld',
      name: 'helloWorld',
      component: HelloWorld
    }
  ]
})
