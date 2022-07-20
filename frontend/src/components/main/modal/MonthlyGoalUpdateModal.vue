<template>
  <body>
  <div id="update-monthly-goal-modal">
    <div id="head">
      <h1 id="create-monthly-goal-title">월간 목표 수정</h1>
    </div>
    <div v-if="isVerifyError.goalType" class="field-error hvr-wobble-top field-error-color" style="text-align: center"> &nbsp;&nbsp;{{verifyCode.goalType}}</div>
    <form id="create-monthly-goal-form">
      <input id="monthly-goal-id" hidden>
      <div class="buttons">
        <ul class="goal-types" style = "margin-left: 18px; padding-bottom: 10px">
          <li><img class="goal-type-img" :src="goalTypeImage('A')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('A')" :checked="isChecked('A')" type="radio" name="goalType" id="goalTypeA" value="A"><label for="goalTypeA"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('B')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('B')" :checked="isChecked('B')" type="radio" name="goalType" id="goalTypeB" value="B"><label for="goalTypeB"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('C')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('C')" :checked="isChecked('C')" type="radio" name="goalType" id="goalTypeC" value="C"><label for="goalTypeC"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('D')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('D')" :checked="isChecked('D')" type="radio" name="goalType" id="goalTypeD" value="D"><label for="goalTypeD"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('E')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('E')" :checked="isChecked('E')" type="radio" name="goalType" id="goalTypeE" value="E"><label for="goalTypeE"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('F')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('F')" :checked="isChecked('F')" type="radio" name="goalType" id="goalTypeF" value="F"><label for="goalTypeF"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('G')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('G')" :checked="isChecked('G')" type="radio" name="goalType" id="goalTypeG" value="G"><label for="goalTypeG"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('H')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('H')" :checked="isChecked('H')" type="radio" name="goalType" id="goalTypeH" value="H"><label for="goalTypeH"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('I')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('I')" :checked="isChecked('I')" type="radio" name="goalType" id="goalTypeI" value="I"><label for="goalTypeI"></label></li>
          <li><img class="goal-type-img" :src="goalTypeImage('J')"><input class="goal-type" v-model="monthlyGoal.goalType" :disabled="isDisabled('J')" :checked="isChecked('J')" type="radio" name="goalType" id="goalTypeJ" value="J"><label for="goalTypeJ"></label></li>
        </ul>
      </div>
      <br>
      <div id="date">
        <span style="font-size: 18px; font-weight: bold;" >날짜</span>
        <span v-if="isVerifyError.yearValue || isVerifyError.monthValue" class="field-error hvr-wobble-top field-error-color">&nbsp; 값을 입력해주세요.</span>
        <span v-if="isVerifyError.year" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.year}}</span>
        <span v-if="isVerifyError.month" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.month}}</span>
        <br>
        <label for="create-goal-year"></label>
        <input v-model="monthlyGoal.year" type="text" id="create-goal-year" name="year" placeholder="YEAR">
        <label for="create-goal-month"></label>
        <input v-model="monthlyGoal.month" type="text" min="1" max="12" id="create-goal-month" name="month" placeholder="MONTH">
      </div>
      <div id="input-name">
        <span style="font-size: 18px; font-weight: bold;">이름</span>
        <span v-if="isVerifyError.name" class="field-error hvr-wobble-top field-error-color"> &nbsp;&nbsp;{{verifyCode.name}}</span>
        <label for="create-goal-name"></label>
        <input v-model="monthlyGoal.name" class="form-input" type="text" id="create-goal-name" name="name">
      </div>
      <div id="input-content">
        <div style="font-size: 18px; font-weight: bold;">내용</div>
        <label for="create-goal-content"></label>
        <input v-model="monthlyGoal.content" class="form-input" type="text" name="content" id="create-goal-content">
      </div>
      <div id="input-type">
        <div style="font-size: 18px; font-weight: bold;">타입</div>
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
        <button @click="modifyMonthlyGoal" type="button" class="create-button hvr-fade-create" style="margin-right: 100px;">확인</button>
        <button @click="close" type="button" id="create-monthly-goal-cancel" class="create-button hvr-fade-create">취소</button>
      </div>
    </form>
  </div>
  </body>
</template>

<script>
import axios from "axios";

export default {
  name: "MonthlyGoalUpdateModal",
  data() {
    return {
      monthlyGoal : {},
      fistGoalType:"",
      error : "",
      isError:false,
      isVerifyError : {name: false, goalType: false, achieveType:false, NoValue:false, month: false, year: false, goalTimeQuota: false, goalCountQuota: false,},
      verifyCode: [],
    }
  },
  mounted() {
    this.init()
  },
  methods : {
    modifyMonthlyGoal : function () {
        if (this.monthlyGoal.year === '') {
          this.isVerifyError.yearValue = true;
          return;
        } else this.isVerifyError.yearValue = false;
        if (this.monthlyGoal.month === '') {
          this.isVerifyError.monthValue = true
          return;
        } else this.isVerifyError.monthValue = false;
      axios.post(this.server + "/api/main/monthly/update", this.monthlyGoal)
          .then((response) => {
            this.$router.go();
          }).catch((error) => {
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
    goalTypeImage(goalType) {return "https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-goal-type-" + goalType + ".png"},
    isDisabled(goalType) {
      return this.createdGoalTypes.includes(goalType) && this.firstGoalType !== goalType},
    isChecked(goalType) {return this.updateMonthlyGoal.goalType === goalType},
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
    init() {
      this.monthlyGoal = this.updateMonthlyGoal;
      this.firstGoalType = this.updateMonthlyGoal.goalType;
    },
    update: function() {
      this.$emit("update", this.monthlyGoal)
    },
    close : function() {
      this.$emit("close")
    }
  }
  ,
  props: ["createdGoalTypes", "updateMonthlyGoal"],
}
</script>

<style scoped>
@import "../../../assets/MonthlyGoalUpdatemodal-css.css";
</style>