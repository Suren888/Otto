import React from 'react';

import { Admin, Resource } from "react-admin";
import {CategoryEdit, CategoryCreate, CategoryList} from './crud/Category';
import {ProductEdit, ProductCreate, ProductList } from './crud/Product';
import customDataProvider from './dataprovider/DataProvider';

const AdminOTTO = () => (
 <Admin dataProvider={customDataProvider}>
   <Resource name="admin_categories" list = { CategoryList } edit = { CategoryEdit } create = { CategoryCreate } />
   <Resource name="products" list = { ProductList } edit = { ProductEdit } create = { ProductCreate }/>
 </Admin>
);

export default AdminOTTO;



