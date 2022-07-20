<template>
  <body>
  <div v-if="isInputDate" class="container" id="create-daily-goal-modal">
    <div id="head">
      <h1 id="title">일간 목표 생성</h1>
      <button @click="openCreatedMonthlyGoals" id="check-monthly-goals-btn"> 월간 목표 확인 ▶</button>
    </div>
    <div v-if="isVerifyError.goalType" class="field-error hvr-wobble-top field-error-color" style="text-align: center"> &nbsp;&nbsp;{{verifyCode.goalType}}</div>
    <div v-if="isCreatedMonthlyGoals" id="check-monthly-goals" style= "background-color: #ffcf93">
      <div id="all-monthly-goals">
        <ul v-for="(goalName,type) in createdMonthlyGoals" style="padding-left: 27px; padding-right: 40px;" >
          <div style="display: flex">
            <img style="width: 20px" :src=goalTypeImage(type)>
            <li> {{goalName}} </li>
          </div>
        </ul>
      </div>
    </div>
    <form id="create-daily-goal-form">
      <div class="buttons">
        <ul>
          <li><img style="width: 20px" :src="goalTypeImage('A')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeA" value="A"><label for="goalTypeA"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('B')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeB" value="B"><label for="goalTypeB"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('C')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeC" value="C"><label for="goalTypeC"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('D')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeD" value="D"><label for="goalTypeD"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('E')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeE" value="E"><label for="goalTypeE"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('F')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeF" value="F"><label for="goalTypeF"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('G')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeG" value="G"><label for="goalTypeG"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('H')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeH" value="H"><label for="goalTypeH"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('I')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeI" value="I"><label for="goalTypeI"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('J')"><input v-model="dailyGoal.goalType" class="goals" type="radio" name="goalType" id="goalTypeJ" value="J"><label for="goalTypeJ"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('OTHER')"><input v-model="this.dailyGoal.goalType"  type="radio" name="goalType" id="goalTypeOTHER" value="OTHER"><label for="goalTypeOTHER"></label></li>
        </ul>
      </div>
      <br>
      <div id="addDiv"></div>
      <br>
      <div id="date">
        <span style="font-size: 18px; font-weight: bold">날짜</span>
        <span v-if="isVerifyError.year" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.year}}</span>
        <span v-if="isVerifyError.month" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.month}}</span>
        <span v-if="isVerifyError.day" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.day}}</span>
        <span v-if="isVerifyError.yearValue || isVerifyError.monthValue || isVerifyError.dayValue" class="field-error hvr-wobble-top field-error-color">&nbsp; 값을 입력해주세요.</span>
        <br>
        <label for="create-goal-year"></label>
        <input v-model="dailyGoal.year" type="text" id="create-goal-year" name="year" placeholder="YEAR">
        <label for="create-goal-month"></label>
        <input v-model="dailyGoal.month" type="text" id="create-goal-month" name="month" placeholder="MONTH">
        <label for="create-goal-day"></label>
        <input v-model="dailyGoal.day" type="text" id="create-goal-day" name="day" placeholder="DAY">
      </div>
      <div id="input-name">
        <span style="font-size: 18px; font-weight: bold">이름</span>
        <span v-if="isVerifyError.name" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.name}}</span>
        <label for="create-goal-name"></label>
        <input v-model="dailyGoal.name" class="form-input" type="text" id="create-goal-name" name="name">
      </div>
      <div id="input-content">
        <div style="font-size: 18px; font-weight: bold" >내용</div>
        <label for="create-goal-content"></label>
        <input v-model="dailyGoal.content" type="text" id="create-goal-content" name="content">
      </div>
      <div id="input-type">
        <span style="font-size: 18px; font-weight: bold">타입</span>
        <span v-if="isVerifyError.achieveType" class="field-error hvr-wobble-top field-error-color" style="text-align: center"> &nbsp;&nbsp;{{verifyCode.achieveType}}</span>
        <span v-if="isVerifyError.NoValue" class="field-error hvr-wobble-top field-error-color" style="text-align: center"> &nbsp;&nbsp;{{verifyCode.NoValue}}</span>
        <span v-if="isVerifyError.goalCountQuota" class="field-error hvr-wobble-top field-error-color" style="text-align: center"> &nbsp;&nbsp;{{verifyCode.goalCountQuota}}</span>
        <span v-if="isVerifyError.goalTimeQuota" class="field-error hvr-wobble-top field-error-color" style="text-align: center"> &nbsp;&nbsp;{{verifyCode.goalTimeQuota}}</span>
        <div>
          <div class="goal-types">
            <button type="button" @click="achieveTypeBASIC" class="goal-type-select btn-red" >기본</button>
            <button type="button" @click="achieveTypeCOUNT" class="goal-type-select btn-green">개수</button>
            <button type="button" @click="achieveTypeTIME" class="goal-type-select btn-yellow">시간</button>
          </div>
          <div v-if="this.dailyGoal.achieveType === 'COUNT'">
            <input class="count-input" type="number" v-model="dailyGoal.goalCountQuota"><span>개</span>
          </div>
          <div v-if="this.dailyGoal.achieveType === 'TIME'">
            <input class="count-input" type="number" v-model="dailyGoal.goalTimeQuota"><span>시간</span>
          </div>
        </div>
        <p id="input_out"></p>
      </div>
      <div id="create-buttons">
        <button @click="createDailyGoal" type="button" class="create-button hvr-fade-create" style="margin-right: 100px;">확인</button>
        <button @click="close" type="button" class="create-button hvr-fade-create">취소</button>
      </div>
    </form>
  </div>
  </body>

  <div v-if="isDateModal" id="date-modal">
    <form class="date-form">
      <span>년</span>
      <span v-if="isVerifyError.yearValue" class="field-error hvr-wobble-top field-error-color">&nbsp; 값을 입력해주세요.</span>
      <input class="date-input" type="number" placeholder="YEAR" v-model="this.dailyGoal.year">
      <span>월</span>
      <span v-if="isVerifyError.monthValue" class="field-error hvr-wobble-top field-error-color">&nbsp; 값을 입력해주세요.</span>
      <input class="date-input" type="number" placeholder="MONTH" v-model="this.dailyGoal.month">
      <span>일</span>
      <span v-if="isVerifyError.dayValue" class="field-error hvr-wobble-top field-error-color">&nbsp; 값을 입력해주세요.</span>
      <input class="date-input" type="number" placeholder="DAY" v-model="this.dailyGoal.day">
      <div id="date-created-buttons">
        <button @click="getCreatedMonthlyGoals" class="create-button">확인</button>
        <button @click="close" class="create-button">취소</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios"

export default {
  name: "DailyGoalCreateModal",
  data() {
    return {
      dailyGoal : {
        name:"",
        content:"",
        goalType: null,
        achieveType: null,
        goalCountQuota:"",
        goalCount:"",
        goalTimeQuota:"",
        goalTime:"",
        year : new Date().getFullYear(),
        month: new Date().getMonth() + 1,
        day: new Date().getDate(),
      },
      createdMonthlyGoals : [],
      isDateModal : true,
      isInputDate : false,
      isCreatedMonthlyGoals: false,
      isVerifyError : {name: false, goalType: false, achieveType:false, NoValue:false, month: false, year: false, day:false,
      yearValue: false, monthValue: false, dayValue:false, goalCountQuota : false, goalTimeQuota : false},
      verifyCode: [],
    }
  },
  methods : {
    createDailyGoal: function(dailyGoal) {
      if (this.dailyGoal.year === '') {
        this.isVerifyError.yearValue = true;
        return;
      } else this.isVerifyError.yearValue = false;
      if (this.dailyGoal.month === '') {
        this.isVerifyError.monthValue = true
        return;
      } else this.isVerifyError.monthValue = false;
      if (this.dailyGoal.day === '') {
        this.isVerifyError.dayValue = true
        return
      } else this.isVerifyError.dayValue = false;
      axios.post(this.server + "/api/main/daily", this.dailyGoal)
          .then((response) => {this.$router.go()})
          .catch((error) => {
            this.errorCode = []
            this.isVerifyError = {name: false, goalType: false, achieveType: false, NoValue:false, year: false}
            if (Array.isArray(error.response.data)) {
              this.isError = false;
              for (var field of error.response.data) {
                this.verifyCode[field.code] = field.message;
                if (field.code === "name") this.isVerifyError.name = true;
                if (field.code === "goalType") this.isVerifyError.goalType = true;
                if (field.code === "achieveType") this.isVerifyError.achieveType = true;
                if (field.code === "NoValue") this.isVerifyError.NoValue = true;
                if (field.code === "month") this.isVerifyError.month = true;
                if (field.code === "year") this.isVerifyError.year = true;
                if (field.code === "day") this.isVerifyError.day = true;
                if (field.code === "goalCountQuota") this.isVerifyError.goalCountQuota = true;
                if (field.code === "goalTimeQuota") this.isVerifyError.goalTimeQuota = true;
              }
            } else {
              this.isError = true;
              this.errorMessage = error.response.data.message;
            }
          });
    },
    goalTypeImage(goalType) {
      return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-" + goalType + ".png";
    },
    achieveTypeBASIC() {
      this.dailyGoal.achieveType = "BASIC"
      this.dailyGoal.goalTimeQuota = 0;
      this.dailyGoal.goalCountQuota = 0;
    },
    achieveTypeCOUNT() {
      this.dailyGoal.achieveType = "COUNT"
      this.dailyGoal.goalTimeQuota = 0;
    },
    achieveTypeTIME() {
      this.dailyGoal.achieveType = "TIME"
      this.dailyGoal.goalCountQuota = 0;
    },
    openCreatedMonthlyGoals() {
      this.isCreatedMonthlyGoals = !this.isCreatedMonthlyGoals},
    getCreatedMonthlyGoals : function(e) {
      e.preventDefault();
      if (this.dailyGoal.year === '') {
        this.isVerifyError.yearValue = true;
        return;
      } else this.isVerifyError.yearValue = false;
      if (this.dailyGoal.month === '') {
        this.isVerifyError.monthValue = true
        return;
      } else this.isVerifyError.monthValue = false;
      if (this.dailyGoal.day === '') {
        this.isVerifyError.dayValue = true
        return
      } else this.isVerifyError.dayValue = false;
      axios.get(this.server + "/api/main/daily/view/" + this.dailyGoal.year + "/" + this.dailyGoal.month)
          .then((response) => {
            this.createdMonthlyGoals = response.data.data;
            this.isDateModal = false;
            this.isInputDate = true;
          })
    },
    close : function() {
      this.$emit("close")
    }
  }

}
</script>

<style scoped>
@import "../../../assets/DailyGoalCreateModal-css.css";
</style>