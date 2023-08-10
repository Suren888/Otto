import React, { Dispatch } from 'react';
import Pagination from 'react-bootstrap/Pagination';
import { useDispatch } from "react-redux";
import productSlice from '../store/productSlice';
import { MIN_PAGE_NUMBER_BEFORE_AFTER_ELLIPSIS } from '../constants'

import "../styles/pagination.css";
import { number } from 'ra-core';
import { AnyAction } from 'redux';

const isPageRendered = (totalPages: number, selectedPage: number, consideredPage: number) => {
  if((consideredPage <= 2 || (totalPages - consideredPage) < MIN_PAGE_NUMBER_BEFORE_AFTER_ELLIPSIS) ||
    Math.abs(selectedPage-consideredPage) <=1){
    return true;
  }
  return false;
  
}

const getPages = (totalPages: number, selectedPage: number, dispatch: Dispatch<AnyAction>) => {
  const componentArray: any[] = [];
  let beforeAfterEllipse = false;
  for (let i = 1; i <= totalPages; ++i) {
    if(isPageRendered(totalPages, selectedPage, i)){
      beforeAfterEllipse = false;
      componentArray.push((i) === selectedPage ? 
      <Pagination.Item active>{i}</Pagination.Item> :
      <Pagination.Item onClick={() => {
        dispatch(productSlice.actions.setPage(i))
        }}>{i}</Pagination.Item>)
    } else if(!beforeAfterEllipse){
      beforeAfterEllipse = true;
      componentArray.push(<Pagination.Ellipsis />);
    }
  }
  return componentArray;
}

function PaginationExample(totalPages: number, selectedPage: number) {
  const dispatch = useDispatch();
  const arr: number[] = [...Array(totalPages + 1)];
  return (
    <Pagination>
      <Pagination.First onClick = {() => {
        dispatch(productSlice.actions.setPage(1))
      }}/>
      <Pagination.Prev onClick = {() => {
        dispatch(productSlice.actions.setPage(selectedPage - 1))
      }}/>
    
      {getPages(totalPages, selectedPage, dispatch)}
      <Pagination.Next onClick = {() => {
        if(selectedPage !== totalPages) {
          dispatch(productSlice.actions.setPage(selectedPage + 1))
        }
      }}/>
      <Pagination.Last onClick = {() => {
        dispatch(productSlice.actions.setPage(totalPages))
      }}/>
    </Pagination>
  );
}

export default PaginationExample;