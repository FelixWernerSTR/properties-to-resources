import { defineComponent, provide } from "vue";
//import { useAlertService } from '@/services/alert.service';
import { RouterLink, RouterView } from "vue-router";

export default defineComponent({
  name: "App",
  components: {
    RouterLink,
    RouterView,
  },
  setup() {
    //provide('alertService', useAlertService());
    return {};
  },
});
