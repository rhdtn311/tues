<template>
  <body>
  <div>
    <img @click="goBefore" id="back-button" src="https://tues-images.s3.ap-northeast-2.amazonaws.com/images/back-btn.png" th:onclick="|location.href='@{/member/home}'|">
  </div>
  <div class="container">
    <div class="top">
      <div class="year_and_month" th:text="${year} + '.' + ${month}">{{year}}.{{month}}</div>
      <form class="top_check_box">
        <select v-model="year" name="year" id="year" >
          <option v-for="y in years">{{y}}</option>
        </select>
        <select v-model="month" name="month" id="month">
          <option v-for="m in months">{{m}}</option>
        </select>
        <button type="button" @click="getGoalList(year, month)"> 확인 </button>
      </form>
    </div>
    <div class="calendar">
      <div class="calendar-item" v-for="(week,weekIdx) in this.dailyGoals">
        <div v-for="(dailyGoals, num) in week">
          <div class="day_box day_box_nothing" v-if="dailyGoals === null"></div>
          <div class="day_box" v-if="dailyGoals != null">
            <div class="day_and_day_of_week">
            </div>
            <div class="day-info">
              <div class="day-of-week">{{DOW[num]}}</div>
              <div class="day">{{day[7 * weekIdx + num]}}</div>
            </div>
            <div v-for="goal in dailyGoals">
              <div class="one-goal">
                <div class="goal-name" :class="goal.success === false ? 'fail-goal' : ''"> {{goal.goalName}}&nbsp;&nbsp;</div>
                <div class="goal-time-or-count" :class="goal.success === false ? 'fail-goal' : ''">
                  <span v-if="goal.achieveType === 'TIME'">{{goal.goalTime}} / {{goal.goalTimeQuota}}</span> <span v-if="goal.achieveType === 'TIME'" class="type-text">개</span>
                  <span v-if="goal.achieveType === 'COUNT'">{{goal.goalCount}} / {{goal.goalCountQuota}}</span> <span v-if="goal.achieveType === 'COUNT'" class="type-text">시간</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div>
      <div id="records-of-the-month-title"> 📜 RECORDS OF THE MONTH </div>
      <div class="month-records">
        <div class="one-monthly-goal" v-for="monthlyGoal in monthlyGoals">
          <div class="monthly-goal-name-and-count">
            <div id="monthly-goal-name">{{monthlyGoal.name}}</div>
            <div class="monthly-goal-achieve-rate" v-if="monthlyGoal.achieveType === 'TIME' ">
              {{monthlyGoal.goalTime}} / {{monthlyGoal.goalTimeQuota}} (달성률 : {{(monthlyGoal.goalTime * 100 / monthlyGoal.goalTimeQuota).toFixed(0)}}%)
            </div>
            <div class="monthly-goal-achieve-rate" v-if="monthlyGoal.achieveType === 'COUNT' ">
              {{monthlyGoal.goalCount}} / {{monthlyGoal.goalCountQuota}} (달성률 : {{(monthlyGoal.goalCount * 100 / monthlyGoal.goalCountQuota).toFixed(0)}}%)
            </div>
            <div class="monthly-goal-achieve-rate" v-if="monthlyGoal.achieveType === 'BASIC'">{{monthlyGoal.success === true ? '달성' : '미달성'}}</div>
          </div>
          <progress v-if="monthlyGoal.achieveType === 'COUNT'" :value="monthlyGoal.goalCount * 100 / monthlyGoal.goalCountQuota" min = "0" max="100"></progress>
          <progress v-if="monthlyGoal.achieveType === 'TIME'" :value="monthlyGoal.goalTime * 100 / monthlyGoal.goalTimeQuota" min = "0" max="100"></progress>
        </div>
      </div>
    </div>
  </div>
  </body>
</template>

<script>
import axios from "axios";

export default {
  name: "GoalList",
  data() {
    return {
      year: new Date().getFullYear(),
      month : new Date().getMonth() + 1,
      dailyGoals : {},
      DOW: ["MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"],
      day: [],
      months: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      years : [],
      monthlyGoals: []
    }
  },
  mounted() {
    this.getGoalList()
    this.getYears();
  },
  methods: {
    getGoalList: function() {
      axios.get(this.server + "/api/main/list/" + this.year + "/" + this.month)
          .then((response) => {
              console.log(JSON.stringify(response, null, 2))
              this.dailyGoals = response.data.data.goalList;
              this.day = response.data.data.days;
              this.monthlyGoals = response.data.data.monthlyGoals;
          }
        )
    },
    getYears: function() {
      for (let y = 2022; y <= 2100; y++) {
        this.years.push(y);
      }
    },
    goBefore: function() {
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>
/*스크롤바*/
/* width */
::-webkit-scrollbar {
  width: 5px;
}
/* Handle */
::-webkit-scrollbar-thumb {
  background: #CDDEFF;
  border-radius: 10px;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #2C2891;
}

.day-info {
  display: flex;
  justify-content: space-between;
  margin-right: 10px;
  font-style: italic;
}

.type-text {
  font-size: 13px;
}

select {
  width: 57px;
  text-align: center;
  padding: 0.15em;
  font-family: inherit;
  font-size: 17px;
  border: 2px solid #292C6D;
  border-radius: 5px;
  background: white;
  color: #292C6D;
  appearance: none;
}

select::-ms-expand {
  display: none;
}

button {
  border-radius: 5px;
  border: solid 0px #292C6D;
  background-color: #292C6D;
  width: 62px;
  height: 32px;
  font-weight: bold;
  color: #FFF8F3;
}

button:hover {
  cursor: pointer;
}

body {
  background-color: #FFF8F3;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.top {
  display: flex;
  width: 76%;
  justify-content: space-between;
  align-items: center;
}

.year_and_month {
  font-style: italic;
  font-size: 40px;
}

.calendar {
  border-right: 2px solid black;
  border-bottom: 2px solid black;
  width: fit-content;
  margin-bottom: 50px;
}

.calendar-item {
  display: flex;
}

.day_box {
  width : 200px;
  height : 150px;
  border: 2px solid black;
  overflow: auto;
  overflow-y: auto;
  border-bottom: none;
  border-right: none;
  padding-left: 5px;
}

.day_box_nothing {
  background-color: #cccccc;
}

.one-goal {
  display: flex;
  flex-wrap: wrap;
}

.day_and_day_of_week {
  display: flex;
  font-weight: lighter;
  margin-bottom: 5px;
}

.fail-goal {
  color: #E04555;
}

.month-records {
  border: black solid 2px;
  width: 1417px;
  padding: 16px;
  margin-bottom: 30px;
}

.one-monthly-goal {
  padding-bottom: 10px;
  display: flex;
  flex-direction: column;
}

.monthly-goal-name-and-count {
  display: flex;
  align-items: center;
}

#monthly-goal-name {
  margin-right: 20px;
}

.monthly-goal-achieve-rate {
  font-size: 14px;
  font-style: italic;
}

progress {
  height: 10px;
  width: 270px;
}

#records-of-the-month-title {
  font-size: 30px;
  font-weight: bold;
  font-style: italic;
}

#back-button {
  width: 30px;
  height: 30px;
  position: absolute;
  top: 30px;
  left: 30px;
}

#back-button:hover {
  cursor:pointer;
}

</style>