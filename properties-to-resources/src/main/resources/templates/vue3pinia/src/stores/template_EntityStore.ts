import {defineStore} from "pinia";
import [=mavenproject.entityName]Service from "@/services/[=mavenproject.entityName].service";
import {type I[=mavenproject.entityName], [=mavenproject.entityName]} from "@/model/[=mavenproject.entityName].model";


//gutes Beispiel wie man Pinia mit Typescript verwendet:
//https://runthatline.com/pinia-typescript-type-state-actions-getters/
type State = {
  [=mavenproject.entityName?lower_case]: I[=mavenproject.entityName];
  isRequestLoading: boolean;
};
const [=mavenproject.entityName?lower_case]Service = new [=mavenproject.entityName]Service();

export const use[=mavenproject.entityName] = defineStore("[=mavenproject.entityName]", {
  state: (): State => ({
    [=mavenproject.entityName?lower_case]: new [=mavenproject.entityName](),
    isDebug: false,
  }),

  getters: {
    requestLoading() {
      return this.isRequestLoading;
    },
  },

  actions: {
    get[=mavenproject.entityName]forId(id: number): Promise<void> {
      console.log("retrieve[=mavenproject.entityName] by pinia action!");
      [=mavenproject.entityName?lower_case]Service
        .find(id)
        .then((res) => {
          console.log("retrieve[=mavenproject.entityName] res:", res.id);
          this.[=mavenproject.entityName?lower_case] = res;
        })
        .catch((error) => {
          //alertService().showHttpError(this, error.response);
          console.log("retrieve[=mavenproject.entityName] fehlgeschlagen!", error);
        });
    },
    init(): Promise<void> {
      console.log("init: create empty [=mavenproject.entityName]!");
      this.[=mavenproject.entityName?lower_case] = new [=mavenproject.entityName]();
    },

    async save[=mavenproject.entityName](): Promise<void> {
      await this.setIsRequestLoading(true);
      console.log("save[=mavenproject.entityName] by pinia action!",  this.[=mavenproject.entityName?lower_case].value);
      console.log("save[=mavenproject.entityName] this.isRequestLoading: ", this.isRequestLoading);
            await [=mavenproject.entityName?lower_case]Service
            .save(this.[=mavenproject.entityName?lower_case])
            .then((res) => {
              console.log("save[=mavenproject.entityName] res:", res.id);
              this.[=mavenproject.entityName?lower_case] = res;
            })
            .catch((error) => {
              //alertService().showHttpError(this, error.response);
              console.log("save[=mavenproject.entityName] fehlgeschlagen!", error);
            });
        await this.setIsRequestLoading(false);
    },
    async save() {
      await this.getIsRequestLoading();
      if(this.isRequestLoading===true){
        alert(
          "warten Sie ein Agugenblick! Der Server antwortet noch!"
        );
      }else{
      await this.save[=mavenproject.entityName]();
      }
    },
  },
});
