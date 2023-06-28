<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.repairname" :disabled="isadmin" :placeholder="$t('repair.repairName')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.status" class="filter-item" :placeholder="$t('repair.status')" clearable>
        <el-option v-for="(value, key) in statusMap" :key="key" :label="value" :value="key" />
      </el-select>
      <el-select v-model="listQuery.type" class="filter-item" :placeholder="$t('repair.type')" clearable>
        <el-option v-for="(value, key) in typeMap" :key="key" :label="value" :value="key" />
      </el-select>
      <el-date-picker
        v-model="listQuery.time"
        class="filter-item"
        type="datetimerange"
        range-separator="To"
        start-placeholder="开始时间"
        end-placeholder="结束时间"
      />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        {{ $t('table.search') }}
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
      <el-table-column :label="$t('repair.id')" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('repair.roomName')" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row | roomNameFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('repair.detail')" min-width="140px">
        <template slot-scope="{row}">
          <span class="link-type">{{ row.detail }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('repair.type')" width="110px" align="center">
        <template slot-scope="{row}">
          <el-tag>{{ typeMap[row.type] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('repair.repairName')" width="100px">
        <template slot-scope="{row}">
          <span v-if="row.repairname != null">{{ row.repairname }}</span>
          <span v-else>
            <i class="el-icon-setting" />
            {{ '无人处理' }}
          </span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('repair.updateTime')" align="center" width="200">
        <template slot-scope="{row}">
          <span>{{ row.updatedate | updatedateFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('repair.status')" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            {{ statusMap[row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="100" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button size="mini" type="success" @click="handleModifyStatus(row)">
            {{ row.status | tFilter }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">{{ $t('table.confirm') }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getRepair, saveorupdate } from '@/api/repair'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

var that
export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    tFilter(status) {
      const tMap = {
        2: '重置',
        1: '完成',
        0: '处理'
      }
      return tMap[status]
    },
    statusFilter(status) {
      const sMap = {
        2: 'success',
        1: 'info',
        0: 'danger'
      }
      return sMap[status]
    },
    roomNameFilter(row) {
      return row.dname + '栋-' + row.floorid + '楼-' + row.roomid % 100 + '号'
    },

    updatedateFilter(time) {
      return new Date(time).toLocaleString('zh-CN', { timeZone: 'Asia/Shanghai' })
    }
  },

  data() {
    return {
      statusMap: {
        2: '处理完',
        1: '处理中',
        0: '未处理'
      },
      typeMap: {
        0: '普通修理',
        1: '紧急修理',
        2: '付费修理',
        3: '其他'
      },
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      downloadLoading: false,
      isadmin: true
    }
  },
  created() {
    this.$store.dispatch('dormitory/getDormitoryList')
    this.getList()
    that = this
  },
  methods: {
    getList() {
      this.listLoading = true
      if (this.listQuery.time != null) {
        this.listQuery.startTime = this.listQuery.time[0]
        this.listQuery.endTime = this.listQuery.time[1]
      }
      if (this.$store.getters.roles.indexOf('admin') < 0) {
        this.listQuery.repairname = this.$store.getters.name
      } else {
        this.isadmin = false
      }
      getRepair(this.listQuery).then(response => {
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
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row) {
      saveorupdate(row).then(res => {
        this.$message({
          message: '操作成功',
          type: 'success'
        })
        this.getList()
      })
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
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['id', '宿舍', '维修类型', '维修人', '详细', '更新时间', '维修状态']
        const filterVal = ['id', 'roomName', 'type', 'repairname', 'detail', 'updatedate', 'status']
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
