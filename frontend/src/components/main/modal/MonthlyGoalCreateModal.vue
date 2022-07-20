<template>
  <body>
  <div v-if="isInputDate" id="create-monthly-goal-modal">
    <div id="head">
      <h1 id="create-monthly-goal-title">월간 목표 생성</h1>
    </div>
    <div v-if="isVerifyError.goalType" class="field-error hvr-wobble-top field-error-color" style="text-align: center"> &nbsp;&nbsp;{{verifyCode.goalType}}</div>
    <form id="create-monthly-goal-form">
      <div class="buttons">
        <ul style = "margin-left: 18px; padding-bottom: 10px">
          <li><img style="width: 20px" :src="goalTypeImage('A')"><input v-model="this.monthlyGoal.goalType" value="A"  class="goalType" :disabled="isDisabled('A')" type="radio" name="goalType" id="goalTypeA"><label for="goalTypeA"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('B')"><input v-model="this.monthlyGoal.goalType"  value="B" class="goalType" :disabled="isDisabled('B')" type="radio" name="goalType" id="goalTypeB"><label for="goalTypeB"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('C')"><input v-model="this.monthlyGoal.goalType" value="C" class="goalType" :disabled="isDisabled('C')" type="radio" name="goalType" id="goalTypeC"><label for="goalTypeC"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('D')"><input v-model="this.monthlyGoal.goalType" value="D" class="goalType" :disabled="isDisabled('D')" type="radio" name="goalType" id="goalTypeD"><label for="goalTypeD"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('E')"><input v-model="this.monthlyGoal.goalType" value="E" class="goalType" :disabled="isDisabled('E')" type="radio" name="goalType" id="goalTypeE"><label for="goalTypeE"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('F')"><input v-model="this.monthlyGoal.goalType" value="F" class="goalType" :disabled="isDisabled('F')" type="radio" name="goalType" id="goalTypeF"><label for="goalTypeF"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('G')"><input v-model="this.monthlyGoal.goalType" value="G" class="goalType" :disabled="isDisabled('G')" type="radio" name="goalType" id="goalTypeG"><label for="goalTypeG"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('H')"><input v-model="this.monthlyGoal.goalType" value="H" class="goalType" :disabled="isDisabled('H')" type="radio" name="goalType" id="goalTypeH"><label for="goalTypeH"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('I')"><input v-model="this.monthlyGoal.goalType" value="I" class="goalType" :disabled="isDisabled('I')" type="radio" name="goalType" id="goalTypeI"><label for="goalTypeI"></label></li>
          <li><img style="width: 20px" :src="goalTypeImage('J')"><input v-model="this.monthlyGoal.goalType" value="J" class="goalType" :disabled="isDisabled('J')" type="radio" name="goalType" id="goalTypeJ"><label for="goalTypeJ"></label></li>
        </ul>
      </div>
      <br>
      <div id="date">
        <span style="font-size: 18px; font-weight: bold;" >날짜</span>
        <span v-if="isVerifyError.year" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.year}}</span>
        <span v-if="isVerifyError.month" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.month}}</span>
        <span v-if="isVerifyError.yearValue || isVerifyError.monthValue" class="field-error hvr-wobble-top field-error-color">&nbsp; 값을 입력해주세요.</span>
        <br>
        <label for="create-goal-year"></label>
        <input v-model="this.monthlyGoal.year" type="text" id="create-goal-year" name="year" placeholder="YEAR">
        <label for="create-goal-month"></label>
        <input v-model="this.monthlyGoal.month" type="text" min="1" max="12" id="create-goal-month" name="month" th:value="${month}" placeholder="MONTH">
      </div>
      <div id="input-name">
        <span style="font-size: 18px; font-weight: bold;">이름</span>
        <span v-if="isVerifyError.name" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.name}}</span>
        <label for="create-goal-name"></label>
        <input v-model="this.monthlyGoal.name" class="form-input" type="text" name="name" id="create-goal-name">
      </div>
      <div id="input-content">
        <div style="font-size: 18px; font-weight: bold;">내용</div>
        <label for="create-goal-content"></label>
        <input v-model="this.monthlyGoal.content" class="form-input" type="text" name="content" id="create-goal-content">
      </div>
      <div id="input-type">
        <span style="font-size: 18px; font-weight: bold;">타입</span>
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
          <div v-if="this.monthlyGoal.achieveType === 'COUNT'">
            <input class="count-input" type="number" v-model="monthlyGoal.goalCountQuota"><span>개</span>
          </div>
          <div v-if="this.monthlyGoal.achieveType === 'TIME'">
            <input class="count-input" type="number" v-model="monthlyGoal.goalTimeQuota"><span>시간</span>
          </div>
        </div>
        <p id="input_out"></p>
      </div>
      <div id="create-buttons">
        <button @click="createMonthlyGoal(monthlyGoal)" type="button" class="create-button hvr-fade-create" style="margin-right: 100px;">확인</button>
        <button @click="close" type="button" id="create-monthly-goal-cancel" class="create-button hvr-fade-create">취소</button>
      </div>
    </form>
  </div>
  </body>

  <div v-if="isDateModal" id="date-modal">
    <form class="date-form">
      <span>년</span>
      <span v-if="isVerifyError.yearValue" class="field-error hvr-wobble-top field-error-color">&nbsp; 값을 입력해주세요.</span>
      <input class="date-input" type="number" placeholder="YEAR" v-model="this.monthlyGoal.year">
      <span>월</span>
      <span v-if="isVerifyError.monthValue" class="field-error hvr-wobble-top field-error-color">&nbsp; 값을 입력해주세요.</span>
      <input class="date-input" type="number" placeholder="MONTH" v-model="this.monthlyGoal.month">
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
  name: "MonthlyGoalCreateModal",
  data() {
    return {
      createdMonthlyGoals: [],
      isInputDate: false,
      isDateModal: true,
      monthlyGoal : {
        name:"",
        content:"",
        goalType:null,
        achieveType:null,
        goalCountQuota:"",
        goalCount:"",
        goalTimeQuota:"",
        goalTime:"",
        year: new Date().getFullYear(),
        month: new Date().getMonth() + 1
      },
      error : "",
      isError:false,
      isVerifyError : {name: false, goalType: false, achieveType:false, NoValue:false, month: false, year: false, goalTimeQuota: false, goalCountQuota: false,},
      verifyCode: [],
    }
  },
  props: ["createdGoalTypes"],
  methods: {
    createMonthlyGoal: function(monthlyGoal) {
      if (this.monthlyGoal.year === '') {
        this.isVerifyError.yearValue = true;
        return;
      } else this.isVerifyError.yearValue = false;
      if (this.monthlyGoal.month === '') {
        this.isVerifyError.monthValue = true
        return;
      } else this.isVerifyError.monthValue = false;
      axios.post(this.server + "/api/main/monthly", monthlyGoal)
          .then((response) => this.$router.go())
          .catch((error) => {
            this.errorCode = []
            this.isVerifyError = {name: false, goalType: false, achieveType: false, NoValue:false, year: false, yearValue: false,
              monthValue: false, goalTimeQuota: false, goalCountQuota: false}
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
                if (field.code === "goalCountQuota") this.isVerifyError.goalCountQuota = true;
                if (field.code === "goalTimeQuota") this.isVerifyError.goalTimeQuota = true;
              }
            } else {
              this.isError = true;
              this.errorMessage = error.response.data.message;
            }
          })
    },
    getCreatedMonthlyGoals : function(e) {
      e.preventDefault();
      if (this.monthlyGoal.year === '') {
        this.isVerifyError.yearValue = true;
        return;
      } else this.isVerifyError.yearValue = false;
      if (this.monthlyGoal.month === '') {
        this.isVerifyError.monthValue = true
        return;
      } else this.isVerifyError.monthValue = false;
      axios.get(this.server + "/api/main/monthly/view/" + this.monthlyGoal.year + "/" + this.monthlyGoal.month)
          .then((response) => {
            this.isInputDate = true;
            this.isDateModal = false;
            this.createdMonthlyGoals=response.data.data;
          })
    },
    isDisabled: function(goalType) {
      return this.createdMonthlyGoals.includes(goalType)
    },
    goalTypeImage: function (goalType) {
      return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-"+ goalType + ".png"
    },
    achieveTypeBASIC() {
      this.monthlyGoal.achieveType = "BASIC"
      this.monthlyGoal.goalTimeQuota = 0;
      this.monthlyGoal.goalCountQuota = 0;
    },
    achieveTypeCOUNT() {
      this.monthlyGoal.achieveType = "COUNT"
      this.monthlyGoal.goalTimeQuota = 0;
    },
    achieveTypeTIME() {
      this.monthlyGoal.achieveType = "TIME"
      this.monthlyGoal.goalCountQuota = 0;
    },
    close : function () {
      this.$emit("close")
    },
  },
}
</script>

<style scoped>
@import "../../../assets/MonthlyGoalCreateModal-css.css";
</style>