import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import AppProvider from './AppProvider';
import AdminPost from './admin/'
import reportWebVitals from './reportWebVitals';

import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

ReactDOM.render(
  <React.StrictMode>

    <BrowserRouter>
    <Routes>
      <Route path='/otto/*' element={<AppProvider />} />
      <Route path='/*' element={<AdminPost />} />
    </Routes> 
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
