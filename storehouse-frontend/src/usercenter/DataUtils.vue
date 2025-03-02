<script>
  export default{
    name: 'DataUtils',

    data() {
      return {
        time: {
          hour: "",
          minute: "",
          second: "",
        },

        nowtime: "",

        week: [
          "星期天",
          "星期一",
          "星期二",
          "星期三",
          "星期四",
          "星期五",
          "星期六",
        ],

      }
    },

    methods: {
      dataTime(){
        this.timeFormate();
        setTimeout(()=>{
          this.dataTime();
        }, 1000);
      },

      timeFormate(){
        const newtime = new Date();
        this.time.hour = this.getIncrease(newtime.getHours(), 2);
        this.time.minute = this.getIncrease(newtime.getMinutes(), 2);
        this.time.second = this.getIncrease(newtime.getSeconds(), 2);
        this.nowtime =
            this.getIncrease(newtime.getFullYear(), 4) + "年" +
            this.getIncrease(newtime.getMonth()+1, 2) + "月" +
            this.getIncrease(newtime.getDate(), 2) + "日 " +
            this.week[newtime.getDay()] + " ";
      },

      getIncrease(num, digit){
        var increase = "";
        for (var i = 1; i <= digit; i++){
          increase += "0";
        }
        return (increase+num).slice(-digit);
      }
    },

    mounted() {
      this.dataTime();
    }
  }
</script>

<template>
  <div style="display: flex; align-items: center; justify-content: center; font-size: 23px; margin-top: 60px">
    <span>
      <span class="data">{{ nowtime }}</span>
      <span class="hour">{{ time.hour }}</span>
      <a class="split">:</a>
      <span class="minute">{{ time.minute }}</span>
      <a class="split">:</a>
      <span class="second">{{ time.second }}</span>
    </span>
  </div>
</template>

<style>
  .split{
    animation: shark 1s step-end infinite;
    vertical-align: center;
    margin-left: 2px;
    margin-right: 2px;
  }

  @keyframes shark{
    0%,
    100%{
      opacity: 1;
    }
    50%{
      opacity: 0;
    }
  }
</style>