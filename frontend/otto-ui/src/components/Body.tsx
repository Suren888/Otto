import React, { useEffect, useState } from 'react';
import productService from '../services/product-service';
import ProductItem from "./ProductItem";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../store";
import productSlice from '../store/productSlice';
import { AxiosResponse } from 'axios';
import PaginationExample from './Pagination';
import { useLocation, Location } from 'react-router-dom';
import { ITEM_PER_PAGE } from '../constants';


const getCategory = (location: Location) => {
  if (location.pathname.includes("otto/products/category")) {
    let arr = location.pathname.split("/");
    return +arr[arr.length - 1]
  }
  return 0
}



const Body = () => {
  const location = useLocation()
  const products = useSelector((state: RootState) => state.product.products);
  const dispatch = useDispatch();
  const [totalCount, setTotalCount] = useState(0);
  const selectedPage = useSelector((state: RootState) => state.product.selectedPage);


  useEffect(() => {
    const categoryIdFromUrl = getCategory(location)
    console.log(categoryIdFromUrl);
    dispatch(productSlice.actions.setSelectedCategoryId(categoryIdFromUrl))
    const promise: Promise<AxiosResponse<any, any>> = categoryIdFromUrl > 0 ? 
      productService.getByCategory(categoryIdFromUrl, (selectedPage-1) * ITEM_PER_PAGE) : 
      productService.getAll((selectedPage-1) * ITEM_PER_PAGE);
      promise.then((response: any) => {
        setTotalCount(response.headers['x-total-count'])
        dispatch(productSlice.actions.setProducts(response.data))
      })
    .catch((e: Error) => {
    });
  }, [ location, selectedPage ]);
   
  return <>
    <div>
      {products.map(product => (
        ProductItem(product)
      ))}
    </div>
    
    {PaginationExample(Math.ceil(totalCount/ITEM_PER_PAGE), selectedPage)}
  </>
};

export default Body;