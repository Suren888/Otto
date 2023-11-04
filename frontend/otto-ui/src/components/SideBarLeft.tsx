import React from "react";
import { useState, useEffect } from 'react';
import SlidingPane from "react-sliding-pane";
import { ProSidebarProvider, Sidebar, Menu } from 'react-pro-sidebar';
import CategoryDataService from "../services/category-service";
import { parseCategory } from "../utils/category-util";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../store";
import productSlice from '../store/productSlice';

import "react-sliding-pane/dist/react-sliding-pane.css";
import "../styles/sideBar.css";

export const SideBarLeft = () => {
  const isOpenLeftBar = useSelector((state: RootState) => state.product.isOpenLeftBar);
  const dispatch = useDispatch();
  const [categories, setCategories] = useState([]);
    
  useEffect(() => {
    CategoryDataService.getAll()
    .then((response: any) => {
      console.log(response.data)
      setCategories(response.data)
      
    })
    .catch((e: Error) => {
    });
  }, []);

  return <SlidingPane
    isOpen={isOpenLeftBar}
    from="left"
    width="300px"
    onRequestClose={() => dispatch(productSlice.actions.setToggleLeftBar(false))}>
      <ProSidebarProvider>
      <Sidebar>
        <Menu>
          {parseCategory(categories, 0)}
        </Menu>
      </Sidebar>
    </ProSidebarProvider>
  </SlidingPane>
}

