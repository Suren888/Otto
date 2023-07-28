import React from 'react';
import { List, Datagrid, TextField, EditButton, Edit, TextInput, SimpleForm, Create, 
  ReferenceField, FunctionField, ReferenceInput, SelectInput, ImageInput, ImageField } from "react-admin";
import ICategoryData from '../../types/category-type';

export const ProductList = () => (
  <List>
      <Datagrid>
          <TextField source="id" />
          <ReferenceField label="Category" source="categoryId" reference="admin_categories">
              <FunctionField render={(record: ICategoryData) => record && `${record.name}`} />
          </ReferenceField>
          <ImageField source="pictures" src="src" title="title" />
          <TextField source="name" />
          <TextField source="description" />
          <EditButton />
      </Datagrid>
  </List>
);

export const ProductEdit = () => (
  <Edit title="Edit Product">
      <SimpleForm>
          <TextInput disabled source="id" />
          <ReferenceInput source="categoryId" reference="admin_categories">
            <SelectInput optionText="name" />
          </ReferenceInput>
          <ImageInput source="pictures" label="Related pictures" multiple={true} >
            <ImageField source="src" title="title" />
          </ImageInput>
          <TextInput source="name" />
          <TextInput source="description" />
      </SimpleForm>
  </Edit>
);

export const ProductCreate = () => (
  <Create title="Create Product">
      <SimpleForm>
          <TextInput disabled source="id" />
          <ReferenceInput source="categoryId" reference="admin_categories">
            <SelectInput optionText="name" />
          </ReferenceInput>
          <ImageInput source="pictures" label="Related pictures" multiple={true} >
            <ImageField source="src" title="title" />
          </ImageInput>
          <TextInput source="name" />
          <TextInput source="description" />
      </SimpleForm>
  </Create>
);