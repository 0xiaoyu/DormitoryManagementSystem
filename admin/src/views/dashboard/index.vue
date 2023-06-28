<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'
import repairDashboard from './repair'

export default {
  name: 'Dashboard',
  components: { adminDashboard, repairDashboard },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    this.$store.dispatch('dormitory/getDormitoryList')
    if (!this.roles.includes('admin')) {
      this.currentRole = 'repairDashboard'
    }
  }
}
</script>
