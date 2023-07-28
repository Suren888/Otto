import React from "react";
import ICategoryData from "../types/category-type";
import { MenuItem, SubMenu } from 'react-pro-sidebar';
import { Link } from 'react-router-dom';
import {  useDispatch } from "react-redux";
import productSlice from '../store/productSlice';


export const parseCategory = (categories:Array<ICategoryData>, parentID: Number) => {
    const dispatch = useDispatch();
    return categories.filter((category) => category.parentCategoryId === parentID).map(paCat => {
        const childs = getChilds(paCat.id, categories);
        return childs.length === 0 ? 
        <MenuItem 
        onClick={() => { dispatch(productSlice.actions.setToggleLeftBarPage(false))}}
        component={<Link to={`/otto/products/category/${paCat.id}`} />}>{paCat.name}</MenuItem> : 
        <SubMenu label={paCat.name}>
            {parseCategory(categories, paCat.id)}
        </SubMenu>
    })  
}

export const getChilds = (parentCatID: Number, categories:Array<ICategoryData>) => {
    return categories.filter(cat => cat.parentCategoryId === parentCatID);
}