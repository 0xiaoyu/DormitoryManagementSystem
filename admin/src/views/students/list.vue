<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" :placeholder="$t('students.name')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.gender" :placeholder="$t('students.gender')" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in genders" :key="item.value" :label="item.value" :value="item.value" />
      </el-select>
      <el-select v-model="listQuery.dormitoryid" :disabled="isadmin" class="filter-item" :placeholder="$t('students.dormitory')" clearable>
        <el-option v-for="item in this.$store.getters.dormitorys" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        {{ $t('table.search') }}
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        {{ $t('table.add') }}
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        {{ $t('table.export') }}
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column :label="$t('students.id')" prop="id" sortable="custom" align="center" width="120" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('students.name')" align="center" width="100">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('students.gender')" width="100px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.gender }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('students.password')" width="140px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.password }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('students.age')" width="140px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.age }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('students.phone')" width="140px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('students.dormitory')" align="center" width="220px">
        <template slot-scope="{row}">
          <span> {{ row | dormitoryFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('students.bedid')" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.bedid }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item v-if="dialogStatus==='update'" :label="$t('students.id')" prop="id">
          <el-input v-model="temp.id" disabled />
        </el-form-item>
        <el-form-item :label="$t('students.name')">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item :label="$t('students.age')">
          <el-input v-model="temp.age" />
        </el-form-item>
        <el-form-item :label="$t('students.gender')" prop="gender">
          <el-select v-model="temp.gender" class="filter-item" placeholder="Please select">
            <el-option v-for="item in genders" :key="item.value" :label="item.value" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('students.password')">
          <el-input v-model="temp.password" />
        </el-form-item>
        <el-form-item :label="$t('students.phone')">
          <el-input v-model="temp.phone" />
        </el-form-item>
        <el-form-item :label="$t('students.dormitory')">
          <el-select v-model="temp.dormitoryid" class="filter-item" placeholder="Please select" style="width: 330px;">
            <el-option v-for="item in this.$store.getters.dormitorys" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('students.floorID')">
          <el-input v-model="temp.floorid" />
        </el-form-item>
        <el-form-item :label="$t('students.roomID')">
          <el-input v-model="temp.roomid" />
        </el-form-item>
        <el-form-item :label="$t('students.bedid')">
          <el-input v-model="temp.bedid" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getStudentList, addStudent, updateStudent, deleteStudent } from '@/api/student'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { getToken } from '@/utils/auth'
import { getInfo } from '@/api/user'
var that
export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    dormitoryFilter(row) {
      return row.dname + '栋-' + row.floorid + '楼-' + row.roomid % 100 + '号'
    }
  },

  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      genders: [{ value: '男' }, { value: '女' }],
      listQuery: {
        page: 1,
        limit: 20,
        name: '',
        gender: '',
        dormitoryid: ''
      },
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        id: undefined,
        type: '',
        password: '123456789'
      },
      dialogVisible: false,
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改学生',
        create: '添加学生'
      },
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false,
      isadmin: true
    }
  },
  created() {
    that = this
    this.$store.dispatch('dormitory/getDormitoryList')
    this.getList()
  },
  methods: {
    // 条件查询
    async getList() {
      const role = this.$store.getters.roles
      if (role !== 'housem') {
        that.listQuery.dormitoryid = await this.getDormitoryid()
      } else {
        this.isadmin = true
      }
      this.listLoading = true
      console.log(this.listQuery)
      getStudentList(this.listQuery).then(response => {
        const dormitorys = that.$store.getters.dormitorys
        response.data.items.forEach(item => {
          item.floorid = parseInt(item.roomid / 100)
          item.roomid = item.roomid % 100
          for (var { id, name } of dormitorys) {
            if (id === item.dormitoryid) {
              item.dname = name
            }
          }
        })
        this.list = response.data.items
        this.total = response.data.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 500)
      })
    },
    getDormitoryid() {
      return new Promise(resolve => {
        getInfo(getToken()).then(response => {
          resolve(response.data.dormitoryid)
        })
      })
    },
    // 条件查询
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    // 重置编辑框
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        title: '',
        password: '123456789',
        status: 'published',
        type: ''
      }
    },
    // 启动新建框
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 新建学生
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.roomid = tempData.floorid * 100 + tempData.roomid
          addStudent(tempData).then((res) => {
            this.getList()
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '添加成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 启动修改框
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 修改学生
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.roomid = tempData.floorid * 100 + tempData.roomid
          updateStudent(tempData).then((res) => {
            this.$notify({
              title: '成功',
              message: '修改成功',
              type: 'success',
              duration: 2000
            })
          })
          that.dialogFormVisible = false
          that.getList()
        }
      })
    },
    // 删除学生
    handleDelete(row, index) {
      this.$confirm('此操作将永久删除该学生, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteStudent(row.id).then((res) => {
          console.log(res)
          this.list.splice(index, 1)
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
        })
      }).catch(() => {
        this.$notify({
          type: 'info',
          message: '已取消删除',
          duration: 2000
        })
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['学号', '姓名', '性别', '密码', '电话', '宿舍楼', '楼层', '房间号', '年龄', '床号']
        const filterVal = ['id', 'name', 'gender', 'password', 'phone', 'dname', 'floorid', 'roomid', 'age', 'bedid']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>
