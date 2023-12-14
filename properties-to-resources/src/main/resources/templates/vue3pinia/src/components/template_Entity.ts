import {defineComponent, ref, watch} from "vue";
import { useRouter} from "vue-router";
import {storeToRefs} from "pinia";
import {use[=mavenproject.entityName]} from "@/stores/[=mavenproject.entityName]Store";
import {useVuelidate} from '@vuelidate/core'
import {maxLength} from '@vuelidate/validators'
import { I[=mavenproject.entityName] } from "@/model/[=mavenproject.entityName].model";
import { useI18n } from 'vue-i18n';
import { useValidation } from '@/config/validation';

export default defineComponent({
  name: "[=mavenproject.entityName]",
  components: {
  },
  setup() { // do not use the keyword 'this' in the setup method -> 'this' is undefined in the setup method
    const [=mavenproject.entityName?lower_case]Store = use[=mavenproject.entityName]();
    const { [=mavenproject.entityName?lower_case], isRequestLoading } = storeToRefs([=mavenproject.entityName?lower_case]Store);
    
    function save() {
      console.debug("[=mavenproject.entityName?lower_case]:", [=mavenproject.entityName?lower_case].value);
      [=mavenproject.entityName?lower_case]Store.save();
    }
    
    function get[=mavenproject.entityName]ForId() {
      console.log("get[=mavenproject.entityName]ForId:", [=mavenproject.entityName?lower_case].id);
      //TODO
    }

    //https://runthatline.com/pinia-watch-state-getters-inside-vue-components/
    watch([=mavenproject.entityName?lower_case], () => {
      console.debug('[=mavenproject.entityName] ref changed, do something!')
    })

    
    //https://vuelidate-next.netlify.app/
    //https://lokalise.com/blog/vue-i18n/
    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
        mustBeCool: validations.mustBeCool(t$('entity.validation.mustBeCool').toString()),
        customContains: validations.customContains(t$('entity.validation.customContains', { text: 'cool' }).toString(), 'cool'),
        //pattern: validations.pattern(t$('entity.validation.pattern', { pattern: '^[a-zA-Z]*' }).toString(), '^[a-zA-Z]*'),
      },
      // versicherungsbeginn: {
      //   between: validations.between(t$('entity.validation.between', { min: HelperService.getDateFormated(new Date(2023,9,9)), max: HelperService.getDateFormated(new Date(2025,11,9)) }).toString(), HelperService.getDateFormated(new Date(2023,9,9)), HelperService.getDateFormated(new Date(2025,11,9))),
      // }
    }

    let v$ = useVuelidate(validationRules, [=mavenproject.entityName?lower_case].value)
    v$.value.$validate();

    watch(v$, () => {
      console.debug('v$ changed, do something!', v$)
      if(v$.value.$errors.length>0){
        console.debug('v$.$errors', v$.value.$errors);
         [=mavenproject.entityName?lower_case]Store.setIs[=mavenproject.entityName]Valid(false);
      }else{
         [=mavenproject.entityName?lower_case]Store.setIs[=mavenproject.entityName]Valid(true);
      }
    });

    return {
      [=mavenproject.entityName?lower_case],
      save,
      get[=mavenproject.entityName]ForId,
      v$,
      t$,
    };
  },
});
