import request from '@/utils/request'

export function getMasterList(query) {
  return request({
    url: '/housemaster/list',
    method: 'post',
    data: query
  })
}

export function addorupdate(data) {
  return request({
    url: '/housemaster',
    method: 'post',
    data
  })
}
export function deleteM(id) {
  return request({
    url: `/housemaster/${id}`,
    method: 'delete'
  })
}

