import React from "react";
import Body from "./components/Body";
import FooterWrapper from "./components/FooterWrapper";
import { SideBarLeft } from "./components/SideBarLeft";
import TopBar from "./components/TopBar";
import ProductView from "./components/ProductView";
import './App.css';
import './styles/index.css'

import {
  Routes,
  Route
} from "react-router-dom";

const App = () => {
  return (<>
    <div className="App">
      <TopBar/>
      <SideBarLeft />
      <Body/>
        {/* <Routes>
          <Route path='/products/*' element={<Body />} />
          <Route path='/products/:id' element={<ProductView />} />
        </Routes> */}
        
    </div>
          <FooterWrapper/>
          </>

  );
}

export default App;