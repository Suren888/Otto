import React from 'react';
import { List, Datagrid, TextField, EditButton, Edit, TextInput, SimpleForm, Create, 
  ReferenceField, FunctionField, ReferenceInput, SelectInput } from "react-admin";
import ICategoryData from '../../types/category-type';

export const CategoryList = () => (
  <List>
      <Datagrid>
          <TextField source="id" />
          <ReferenceField label="Parent Category" source="parentCategoryId" reference="admin_categories">
              <FunctionField render={(record: ICategoryData) => record && `${record.name}`} />
          </ReferenceField>
          <TextField source="name" />
          <EditButton />
      </Datagrid>
  </List>
);

export const CategoryEdit = () => (
  <Edit title="Edit Category">
      <SimpleForm>
          <TextInput disabled source="id" />
          <ReferenceInput source="parentCategoryId" reference="admin_categories">
            <SelectInput optionText="name" />
          </ReferenceInput>
          <TextInput source="name" />
      </SimpleForm>
  </Edit>
);

export const CategoryCreate = () => (
  <Create title="Create a Category">
      <SimpleForm>
          <TextInput disabled source="id" />
          <ReferenceInput source="parentCategoryId" reference="admin_categories">
            <SelectInput optionText="name" />
          </ReferenceInput>
          <TextInput source="name" />
      </SimpleForm>
  </Create>
);


