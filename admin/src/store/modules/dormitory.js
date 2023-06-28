import { getDormitory } from '@/api/dormitory'

const state = {
  // token: getToken(),
  dormitory: []
}

const mutations = {
  SET_DORMITORY: (state, dormitory) => {
    state.dormitory = dormitory
  }
}

const actions = {
  getDormitoryList({ commit, state }) {
    return new Promise((resolve, reject) => {
      getDormitory().then(response => {
        const { data } = response
        commit('SET_DORMITORY', data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
