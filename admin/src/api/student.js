import axios from '@/utils/request'

// 获取学生列表
export function getStudentList(params) {
  return axios.request({
    url: '/students/page',
    method: 'post',
    data: params
  })
}
export function addStudent(params) {
  return axios.request({
    url: '/students/add',
    method: 'post',
    data: params
  })
}

export function updateStudent(params) {
  return axios.request({
    url: '/students/update',
    method: 'post',
    data: params
  })
}

export function deleteStudent(params) {
  return axios.request({
    url: `/students/${params}`,
    method: 'delete'
  })
}

export function getNumber() {
  return axios.request({
    url: '/students/count',
    method: 'get'
  })
}
