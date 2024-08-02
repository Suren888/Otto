import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import AppProvider from './AppProvider';
import AdminPost from './admin/'
import ProductView from './components/ProductView';
import reportWebVitals from './reportWebVitals';
import {
  createBrowserRouter,
  RouterProvider,
  Route,
  Link,
} from "react-router-dom";
// import {
//   BrowserRouter,
//   Routes,
//   Route
// } from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <div>
        <h1>Hello World</h1>
        <Link to="admin">admin</Link>
        <Link to="app">app</Link>
      </div>
    ),
  },
  {
    path: "app",
    element: <AppProvider />,
  },
  {
    path: "otto/products/category/*",
    element: <AppProvider />,
  },
  {
    path: "otto/products/*",
    element: <ProductView />,
  },
  {
    path: "/*",
    element: <AdminPost />,
  },
]);
ReactDOM.render(
  <React.StrictMode>

    {/* <BrowserRouter>
    <Routes>
      <Route path='app' element={<AdminPost />} />
      <Route path='/' element={<AppProvider />} />
      
    </Routes> 
    </BrowserRouter> */}
    <RouterProvider router={router} />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
