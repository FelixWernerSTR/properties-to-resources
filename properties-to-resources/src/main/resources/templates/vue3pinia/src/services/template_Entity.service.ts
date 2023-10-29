import axios from "axios";

import { I[=mavenproject.entityName] } from "@/model/[=mavenproject.entityName].model";

const baseApiUrl = __VITE_BASE_API_URL_[=mavenproject.entityName?upper_case]__;

export default class [=mavenproject.entityName]Service {

  public find(id: number): Promise<I[=mavenproject.entityName]> {
    return new Promise<I[=mavenproject.entityName]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then((res: any) => {
          resolve(res.data);
        })
        .catch((err: any) => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then((res: any) => {
          resolve(res);
        })
        .catch((err: any) => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then((res: any) => {
          resolve(res);
        })
        .catch((err: any) => {
          reject(err);
        });
    });
  }

  public save(entity: I[=mavenproject.entityName]): Promise<I[=mavenproject.entityName]> {
    return new Promise<I[=mavenproject.entityName]>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then((res: any) => {
          resolve(res.data);
        })
        .catch((err: any) => {
          reject(err);
        });
    });
  }

}
