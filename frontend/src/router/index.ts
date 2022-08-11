import { createRouter, createWebHistory } from "vue-router";
import Find from "../components/home/Find.vue";
import Login from "../components/home/Login.vue";
import Join from "../components/home/Join.vue";
import Main from "../components/main/Main.vue";
import GoalList from "../components/main/view/GoalList.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      redirect:"/main",
    },
    {
      path: "/login",
      name: "login",
      component: Login
    },
    {
      path: "/find",
      name: "Find",
      component: Find
    },
    {
      path:"/join",
      name:"Join",
      component: Join
    },
    {
      path: "/main",
      name: "Main",
      component: Main
    },
    {
      path: "/goalList",
      name: "goalList",
      component: GoalList
    }
  ]
});

export default router;
