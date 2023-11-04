import http from "../http-common";
import ICategoryData from "../types/category-type"

class CategoryDataService {
  getAll() {
    return http.get<Array<ICategoryData>>("/api/categories");
  }

  get(id: string) {
    return http.get<ICategoryData>(`/api/Categoryies/${id}`);
  }

  create(data: ICategoryData) {
    return http.post<ICategoryData>("/api/Categories", data);
  }

  update(data: ICategoryData, id: any) {
    return http.put<any>(`/api/Categories/${id}`, data);
  }

  delete(id: any) {
    return http.delete<any>(`/api/Categories/${id}`);
  }

  deleteAll() {
    return http.delete<any>(`/api/Category`);
  }

  findByTitle(title: string) {
    return http.get<Array<ICategoryData>>(`/api/Categories?title=${title}`);
  }
}

export default new CategoryDataService();