import axios from '@/utils/request'

export function login(data) {
  return axios({
    url: '/housemaster/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return axios({
    url: '/housemaster/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return axios({
    url: '/housemaster/logout',
    method: 'post'
  })
}
