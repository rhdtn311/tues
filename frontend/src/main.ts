import { createApp } from "vue";
import { createPinia } from "pinia";
import VueCookies from "vue-cookies";

import App from "./App.vue";
import router from "./router";
import axios from "axios";

// 쿠키 값 전송을 위한 withCredential를 true로 설정
axios.defaults.withCredentials = true;

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(VueCookies);

app.mount("#app");


// global
app.config.globalProperties.server = "http://localhost:8080"
