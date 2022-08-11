<template>
  <!DOCTYPE html>
  <html>
  <head>
    <meta charset="UTF-8">
    <title>Title</title>
  </head>
  <body>
  <div class="container">
    <div><img id="main-logo" src="https://tues-images.s3.ap-northeast-2.amazonaws.com/images/tues-logo.png" alt="tues-logo.png"></div>
    <div class="hvr-wobble-top field-error-color" v-if="isError"> {{errorData}} </div>
    <div class="inputs-and-button">
      <form>
        <label for="loginId"></label>
        <input v-model= "requestData.loginId" placeholder="ID" class="login-input" type="text" id="loginId" name="loginId">
        <br>
        <label for="password"></label>
        <input v-model="requestData.password" placeholder="PASSWORD" class="login-input" type="password" id="password" name="password">
        <span>
            <button id="loginButton" type="submit" @click="login">LOGIN</button>
        </span>
      </form>
    </div>

    <div class="bottom-buttons">
      <button class="join-find-button hvr-float" @click="goToJoin"> JOIN </button>
      <button class="join-find-button hvr-float" @click="goToFind"> FIND </button>
    </div>
  </div>
  </body>
  </html>
</template>

<script>
import axios from "axios"
export default {
  data() {
    return {
      isError: false,
      errorData : "",
      requestData : {loginId: "", password: ""},
    }
  },
  methods: {
    login : async function(e) {
      e.preventDefault();
      const res = await axios.post(this.server + "/api/home/login", this.requestData)
          .then((response) => {
            this.goToMain();
          }).catch((error) => {
            this.isError = true;
            this.errorData = error.response.data.message;
            console.log(error)
          })
    },

    goToFind() {
      this.$router.push("/find")
    },

    goToJoin() {
      this.$router.push("/join")
    },

    goToMain() {
      this.$router.replace("/main");
    }
  },

  mounted() {
    isLogin : axios.post(this.server + "/api/validate/member", null, {withCredentials: true})
        .then((response) => {
          this.$router.replace("/main")
        })
  }
}

</script>

<style scoped>
@import "../../assets/Login-css.css";
</style>