<template>
  <!DOCTYPE html>
  <html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta charset="UTF-8">
    <title>Title</title>
  </head>
  <body class="bg-pan-left">
  <div>
    <img @click="goBack" id="back-button" src="https://tues-images.s3.ap-northeast-2.amazonaws.com/images/back-btn.png" >
  </div>
  <div id="container">
    <div id="flex">
      <div id="title">JOIN</div>
      <form>
        <div class="center">
            <span class="hvr-wobble-top field-error-color"></span>
            <span>
              <p v-if="isError" class = "field-error hvr-wobble-top field-error-color">{{errorMessage}}</p>
            </span>
        </div>
        <div>
          <label for="loginId"></label>
          <input placeholder="ID" type="text" id="loginId" v-model="joinRequest.loginId" class="join-input">
          <div class="field-error hvr-wobble-top field-error-color" v-if="isVerifyError.loginId"> {{verifyCode.loginId}}</div>
        </div>
        <div>
          <label for="password"></label>
          <input placeholder="PASSWORD" type="password" id="password" v-model="joinRequest.password" class="join-input">
          <div class="field-error hvr-wobble-top field-error-color" v-if="isVerifyError.password">{{verifyCode.password}}</div>
        </div>
        <div>
          <label for="verifyPassword"></label>
          <input placeholder="VERIFY PASSWORD" type="password" id="verifyPassword" v-model="joinRequest.verifyPassword" class="join-input">
          <div class="field-error hvr-wobble-top field-error-color" v-if="isVerifyError.passwordVerifyError">{{verifyCode.passwordVerifyError}}</div>
        </div>
        <div>
          <label for="mail"></label>
          <input placeholder="EMAIL" type="email" id="mail" v-model="joinRequest.mail" class="join-input">
          <div class="field-error hvr-wobble-top field-error-color" v-if="isVerifyError.mail">{{verifyCode.mail}}</div>
        </div>
        <div>
          <button type="submit" class="join-button hvr-float" @click="join" >CONFIRM</button>
        </div>
      </form>
    </div>
  </div>
  </body>
  </html>
</template>

<script>
import axios from "axios";
export default {
  name: "Join",
  data() {
    return {
      joinRequest : {loginId: "", password: "", verifyPassword: "", mail:""},
      verifyCode: [],
      isVerifyError: {loginId : false, password : false, mail : false, passwordVerifyError : false},
      isError: false,
      errorMessage : ""
    }
  },
  methods: {
    join: async function(e) {
      e.preventDefault();
      const res = await axios.post(this.server + "/api/home/join", this.joinRequest)
          .then((response) => {
            this.goBack();
            alert("회원가입이 완료되었습니다.")
          }).catch((error) => {
            this.errorCode = []
            this.isVerifyError = {loginId : false, password : false, mail : false, passwordVerifyError : false}
            if (Array.isArray(error.response.data)) {
              this.isError = false;
              for (var field of error.response.data) {
                this.verifyCode[field.code] = field.message;
                if (field.code === "loginId") this.isVerifyError.loginId = true;
                else if (field.code === "password") this.isVerifyError.password = true;
                else if (field.code === "mail") this.isVerifyError.mail = true;
                else if (field.code === "passwordVerifyError") this.isVerifyError.passwordVerifyError = true;
              }
            } else {
              this.isError = true;
              this.errorMessage = error.response.data.message;
            }
          })
    },
    goBack() {
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>
@import "../../assets/Join-css.css";
</style>