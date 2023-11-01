import { createRouter, createWebHistory } from "vue-router";
import { createPinia, storeToRefs } from "pinia";
import { use[=mavenproject.entityName] } from "@/stores/[=mavenproject.entityName]Store";
import { [=mavenproject.entityName] } from "../model/[=mavenproject.entityName].model";

      
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "[=mavenproject.entityName?lower_case]",
      component: () => import("../components/[=mavenproject.entityName].vue"),
    },
    {
      path: "/about",
      name: "about",
      component: () => import("../views/AboutView.vue"),
    },
  ],
});

export default router;
