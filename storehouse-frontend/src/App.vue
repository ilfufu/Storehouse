<template>
  <div id="app" style="height: 100%">
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',

  components: {},

  data(){
    return {
      user: JSON.parse(sessionStorage.getItem('curUser')),
    }
  },

  watch: {
    '$store.state.menu':{
      handler(val, old){
        if(!old && this.user && this.user.account){
          this.$store.commit('setMenu', val)
        }
      },
      immediate: true
    }
  }
}
</script>

<style>
</style>
