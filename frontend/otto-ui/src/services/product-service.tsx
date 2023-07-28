import { ITEM_PER_PAGE } from "../constants";
import http from "../http-common";
import { IProductData } from "../types/product-type"


class ProductDataService {
  
  getAll(start: number) {
    return http.get<IProductData[]>(`/products/?_start=${start}&_end=${start + ITEM_PER_PAGE}`);
  }

  getByCategory(id: number, _start: number) {
    const _end: number = _start + ITEM_PER_PAGE;
    return http.get<IProductData[]>(`/products/category/${id}/${_start}/${_end}`)
  }

  getByProductId(id: Number) {
    return http.get<IProductData>(`/products/${id}`)
  }

}

export default new ProductDataService();