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
        <div th:errors="*{goalType}"></div>
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
      <input class="date-input" type="number" placeholder="YEAR" v-model="this.dailyGoal.year">
      <span>월</span>
      <input class="date-input" type="number" placeholder="MONTH" v-model="this.dailyGoal.month">
      <span>일</span>
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
      isVerifyError : {name: false, goalType: false, achieveType:false, NoValue:false, month: false, year: false, day:false},
      verifyCode: [],
    }
  },
  methods : {
    createDailyGoal: function(dailyGoal) {
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
              }
            } else {
              this.isError = true;
              this.errorMessage = error.response.data.message;
            }
          })
    },
    goalTypeImage(goalType) {
      return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-" + goalType + ".png";
    },
    achieveTypeBASIC() {this.dailyGoal.achieveType = "BASIC"},
    achieveTypeCOUNT() {this.dailyGoal.achieveType = "COUNT"},
    achieveTypeTIME() {this.dailyGoal.achieveType = "TIME"},
    openCreatedMonthlyGoals() {
      this.isCreatedMonthlyGoals = !this.isCreatedMonthlyGoals},
    getCreatedMonthlyGoals : function(e) {
      e.preventDefault();
      axios.get(this.server + "/api/main/daily/view/" + this.dailyGoal.year + "/" + this.dailyGoal.month)
          .then((response) => {
            this.createdMonthlyGoals = response.data.data;
            this.isDateModal = false;
            this.isInputDate = true;
          })
    },
    create: function() {
      this.$emit("create", this.dailyGoal);
    },
    close : function() {
      this.$emit("close")
    }
  }

}
</script>

<style scoped>

.form-input {
  display: block;
}

#date-modal {
  position: absolute;
  top: 28%;
  left: 45%;
  border: solid 2px #325288;
  width: 200px;
  height: 300px;
  background-color: #F5F5F5;
  border-radius: 20px;
}

.date-input {
  width: 128px;
  height: 32px;
  font-size: 15px;
  border: 0;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(233, 233, 233);
  display: block;
  margin-bottom: 15px;
}

.date-form {
  margin-top: 25px;
  margin-left: 25px;
}

#date-created-buttons {
  display: flex;
  justify-content: space-around;
  margin-right: 30px;
}

.goal-type-select:hover {
  cursor: pointer;
}

#date, #input-name, #input-content, #input-type {
  margin-left: 30px;
  margin-top: 20px;
}

#head {
  text-align: center;
}

#all-monthly-goals {
  left: 104%;
  position: fixed;
  top: 7%;
  width: fit-content;
  display: flex;
  flex-direction: column;
  background-color: #F5F5F5;
  border-radius: 20px;
  border: solid 4px #325288;
}

#create-goal-year, #create-goal-month, #create-goal-day {
  height: 30px;
  background-color: #F5F5F5;
  border: none;
  border-bottom: solid 2px #DDDDDD;
  width: 32px;
}

input:focus {
  outline: none;
}

#create-goal-month {
  width: 15px;
  text-align: center;
}

#create-goal-year:focus {
  color: #325288;
}

#create-goal-name, #create-goal-content {
  height: 30px;
  background-color: #F5F5F5;
  border: none;
  border-bottom: solid 2px #DDDDDD;
  width: 570px;
}

#create-goal-name:focus {
  border-bottom: solid 2px #325288;
}

#create-goal-content:focus {
  border-bottom: solid 2px #325288;
}

#input_out {
  margin-left: 50px;
}

#create-daily-goal-select option {
  background: #325288;
  color: #FFF8F3;
}

#create-monthly-goal-select option {
  background: #325288;
  color: #FFF8F3;
}

.create-button {
  border-radius: 5px;
  border: solid 0px #292C6D;
  background-color: #292C6D;
  width: 62px;
  height: 32px;
  font-weight: bold;
  color: #FFF8F3;
  margin-bottom: 15px;
}

#create-buttons {
  margin-top: 38px;
  text-align: center;
}

.hvr-fade-create {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  overflow: hidden;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: color, background-color;
  transition-property: color, background-color;
}
.hvr-fade-create:hover {
  border: solid 2px #292C6D;
  background-color: #FFF8F3;
  color: #292C6D;
}

/*==========================================*/
/*월간 목표 생성창 년도, 월 보내기 모달창*/

#create-daily-goal-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  background-color: #F5F5F5;
  height: fit-content;
  width: 650px;
  border-radius: 20px;
  border: solid 4px #325288;
}

#monthly-modal  {
  background: #FFF8F3;
  box-shadow: 0 8px 32px 0 rgb(31 38 135 / 37%);
  backdrop-filter: blur( 13.5px );
  -webkit-backdrop-filter: blur( 13.5px );
  border-radius: 10px;
  border: solid 4px #325288;
  width: 220px;
  height: fit-content;
  position: relative;
  top: -100px;
  padding: 10px;
}
#monthly-modal .title {
  padding-left: 10px;
  display: inline;
  color: black;
}
#monthly-modal .title h2 {
  display: inline;
}
#monthly-modal {
  display: inline;
  float: right;
  padding-right: 10px;
  cursor: pointer;
  text-shadow: 1px 1px 2px grey;
  color: black;
  font-size: 20px;
  font-weight: bold;
}

#monthly-modal .content {
  margin-top: 20px;
  padding: 0px 10px;
  text-shadow: 1px 1px 2px grey;
  color: black;
}

/*월간 목표 생성 모달창*/
ul li {
  list-style-type: none;
  float: left;
  margin-left: 10px;
}

.goal-type-img {
  width : 20px;
}

.buttons {
  box-sizing: border-box;
  padding: 4px 12px;
  font-size: 12px;
  height: 5px;

}

.goal-type-select {
  position: relative;
  border: 0;
  display: inline-block;
  text-align: center;
  color: white;
  margin-top: 12px;
  margin-right: 15px;
  margin-bottom: 15px;
}

.goal-type-select:active {
  top: 4px;
}

.btn-green {
  background: #82c8a0;
  box-shadow: 0px 4px 0px #87a86f;
}

.btn-green:active {
  box-shadow: 0 0 #87a86f;
  background-color: #87a86f;
}

.btn-yellow {
  background: #f0d264;
  box-shadow:0px 4px 0px #D1B757;
}

.btn-yellow:active {
  box-shadow: 0 0 #ff4c4b;
  background-color: #D6BB59;
}

.btn-red {
  background: #fa5a5a;
  box-shadow:0px 4px 0px #E04342;
}

.btn-red:active {
  box-shadow: 0 0 #ff4c4b;
  background-color: #ff4c4b;
}

.count-input {
  width: 128px;
  height: 32px;
  font-size: 15px;
  border: 0;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(233, 233, 233);
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* 일간 목표 생성- 년도, 월, 일 보내기 모달창 */
#check-monthly-goals-btn {
  border: solid 3px #FEF9EF;
  height: 27px;
  background-color: #172774;
  border-radius: 15px;
  font-weight: bold;
  font-style: oblique;
  color: #FEF9EF;
  display: inline;
  position: relative;
  left: 250px;
}


#daily-modal .modal-window {
  background: #FFF8F3;
  box-shadow: 0 8px 32px 0 rgb(31 38 135 / 37%);
  backdrop-filter: blur( 13.5px );
  -webkit-backdrop-filter: blur( 13.5px );
  border-radius: 10px;
  border: solid 4px #325288;
  width: 220px;
  height: fit-content;
  position: relative;
  top: -100px;
  padding: 10px;
}
#daily-modal .title {
  padding-left: 10px;
  display: inline;
  color: black;

}
#daily-modal .title h2 {
  display: inline;
}
#daily-modal .close-area {
  display: inline;
  float: right;
  padding-right: 10px;
  cursor: pointer;
  text-shadow: 1px 1px 2px grey;
  color: black;
  font-size: 20px;
  font-weight: bold;
}

#daily-modal .content {
  margin-top: 20px;
  padding: 0px 10px;
  text-shadow: 1px 1px 2px grey;
  color: black;
}
</style>