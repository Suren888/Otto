import React from 'react';
import Pagination from 'react-bootstrap/Pagination';
import { useSelector, useDispatch } from "react-redux";

import { RootState } from "../store";
import productSlice from '../store/productSlice';

import "../styles/pagination.css";

function PaginationExample(totalPages: number, selectedPage: number) {
  const dispatch = useDispatch();

  return (
    <Pagination>
      <Pagination.First onClick = {() => {
        dispatch(productSlice.actions.setPage(1))
      }}/>
      <Pagination.Prev onClick = {() => {
        dispatch(productSlice.actions.setPage(selectedPage - 1))
      }}/>
    
      <Pagination.Ellipsis />

      {[...Array(totalPages + 1)].map((x, i) =>
          i === 0 ? <></> : 
          (i) === selectedPage ? 
          <Pagination.Item active>{i}</Pagination.Item> :
          <Pagination.Item onClick={() => {
            dispatch(productSlice.actions.setPage(i))
            }}>{i}</Pagination.Item>
      )}
      <Pagination.Ellipsis />
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