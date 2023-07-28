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
      <Pagination.First />
      <Pagination.Prev />
    
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
      <Pagination.Next />
      <Pagination.Last />
    </Pagination>
  );
}

export default PaginationExample;