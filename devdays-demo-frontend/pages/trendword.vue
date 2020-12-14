<template>
  <section>
        <b-message title="Hello Nuxt.js with GraalVM" size="is-large" class="container  has-text-centered">
            Welcome to Oracle Developer Days
        </b-message>
        <div class="container  has-text-centered">
            <b-button tag="a"
                size="is-medium"
                type="is-info"
                href="/kohaku">
                紅白歌合戦出場歌手
            </b-button>
            <br>
            <br>
            <div class="notification is-danger">
              <font size="14">流行語2020</font>
            </div>
            <b-table :data="item" :columns="columns"></b-table>
        </div>
  </section>
</template>

<script>
import Logo from '~/components/Logo.vue'
import axios from 'axios'

export default {
  components: {
    Logo,
  },
  data: ()=> {
    return{
        columns: [
          { field: 'id', 
            label: 'id', 
            width: '40', 
            numeric: true,
            sortable:true
          },
          { label: 'ランキング', 
            field: 'rank',
            width: '45', 
            numeric: true,
            sortable:true
          },
          {
            label: '流行語',
            field: 'name',
            width: '120', 
            centered: true,
            sortable:true
          },
        ],
      item: []
    }  
  },
  asyncData(){
    return axios.get('http://backend-trendword-svc:8082/trendword', {mode: 'cors'}).then((res) => {
      return {
        item: res.data
      }
    })
  }
}
</script>