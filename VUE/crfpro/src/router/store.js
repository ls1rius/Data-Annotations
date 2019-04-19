import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const state = {
  // eslint-disable-next-line no-useless-escape
  content: '{\n  \"content\": \"\",\n  \"labelCategories\": [\n    {\n      \"id\": 0,\n      \"text\": \"时间实体\",\n      \"color\": \"#7ebf50\",\n      \"borderColor\": \"#b5d7e4\"\n    },\n    {\n      \"id\": 1,\n      \"text\": \"事件实体\",\n      \"color\": \"#579ef8\",\n      \"borderColor\": \"#b5d7e4\"\n    }\n  ],\n  \"labels\": [],\n  \"connectionCategories\": [\n    {\n      \"id\": 0,\n      \"text\": \"在...之前\"\n    },\n    {\n      \"id\": 1,\n      \"text\": \"在...之后\"\n    },\n    {\n      \"id\": 2,\n      \"text\": \"在...期间\"\n    }\n  ],\n  \"connections\": []\n}',
  filename: 'data.json',
  id: -1,
  isChangeMarkData: true
}
const getters = {
  getContent () {
    return state.content
  },
  getFilename () {
    return state.filename
  },
  getId () {
    return state.id
  },
  getIsChangeMarkData () {
    return state.isChangeMarkData
  }
}
const mutations = {
  updateContent (data, newContent) {
    data.content = newContent
  },
  updateFilename (data, newFilename) {
    data.filename = newFilename
  },
  updateId (data, newId) {
    data.id = newId
  },
  updateIsChangeMarkData (data, newIsChangeMarkData) {
    data.isChangeMarkData = newIsChangeMarkData
  }
}
const actions = {

}
const store = new Vuex.Store({
  state,
  getters,
  mutations,
  actions
})
export default store
