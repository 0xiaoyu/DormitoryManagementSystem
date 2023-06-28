import axios from '@/utils/request'

export function getRepair(data) {
  return axios({
    url: '/repair/page',
    method: 'post',
    data
  })
}

export function saveorupdate(data) {
  return axios({
    url: '/repair/addorupdate',
    method: 'post',
    data
  })
}

export function getRepairNumber() {
  return axios({
    url: '/repair/getNumber',
    method: 'get'
  })
}

export function getRepairNumberByid(name) {
  return axios({
    url: `/repair/getNumber/${name}`,
    method: 'get'
  })
}
