import {defineComponent, ref, watch} from "vue";
import { useRouter} from "vue-router";
import {storeToRefs} from "pinia";
import {use[=mavenproject.entityName]} from "@/stores/[=mavenproject.entityName]Store";
import {useVuelidate} from '@vuelidate/core'
import {maxLength} from '@vuelidate/validators'
import { I[=mavenproject.entityName] } from "@/model/[=mavenproject.entityName].model";

export default defineComponent({
  name: "[=mavenproject.entityName]",
  components: {
  },
  setup() { // do not use the keyword 'this' in the setup method -> 'this' is undefined in the setup method
    const [=mavenproject.entityName?lower_case]Store = use[=mavenproject.entityName]();
    const { [=mavenproject.entityName?lower_case], isRequestLoading } = storeToRefs([=mavenproject.entityName?lower_case]Store);
    
    function save() {
      console.log("[=mavenproject.entityName?lower_case]:", [=mavenproject.entityName?lower_case].value);
      [=mavenproject.entityName?lower_case]Store.save();
    }

    //https://runthatline.com/pinia-watch-state-getters-inside-vue-components/
    watch([=mavenproject.entityName?lower_case], () => {
      console.log('[=mavenproject.entityName] ref changed, do something!')
      [=mavenproject.entityName?lower_case].schlagwort = [=mavenproject.entityName?lower_case].value.schlagwort;
    })

    //Validierung fuer schlagwort https://vuelidate-next.netlify.app/
    const rules = {
      schlagwort: {
        maxLength: maxLength(10), // siehe auch import { required, minLength, maxLength, decimal, helpers, required, sameAs, minLength, maxLength, minValue, maxValue, numeric } from '@vuelidate/validators'
      }
    }

    const v$ = useVuelidate(rules, [=mavenproject.entityName?lower_case].value)
    v$.value.$validate();

    watch(v$, () => {
      console.debug('v$ changed, do something!')
      console.debug('v$', v$);
      console.debug('v$.value',  v$.value);
      
      if(v$.schlagwort){
        console.debug('v$.schlagwort.$errors', v$.schlagwort.$errors);
      }
    })

    return {
      [=mavenproject.entityName?lower_case],
      save,
      v$,
    };
  },
});
