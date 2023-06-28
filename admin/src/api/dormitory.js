import axios from '@/utils/request'
export function getDormitory() {
  return axios({
    url: '/dormitory/list',
    method: 'get'
  })
}
