import React, { useState, useEffect } from 'react';
import productService from '../services/product-service';
import { IImage } from '../types/product-type';
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { Carousel } from 'react-responsive-carousel';

const ProductView = () => {
    const url: string = window.location.href;
    const urlArr: string[]= url.split("/");
    const selectedProductId: Number = +urlArr[ urlArr.length - 1];
    const [product, setProduct] = useState({name:'', description: ''});
    const [productImages, setProductImages] = useState([{url : ""}]);
    
    useEffect(() => {
        productService.getByProductId(selectedProductId)
        .then((response: any) => {
          setProduct(response.data)
          const pictures: IImage[] = response.data.pictures
          setProductImages(pictures.map(p => {return{ url: p.src}}))
        }).catch((e: Error) => {  
        });
    }, []);



    return (<>
      <div className="container">
        <h1>{product.name}</h1>
        <div className="row">
          <div className="col-lg-6 ">
            <div className="slide-container">
              <Carousel>
                {productImages.map((slideImage, index)=> (
                  <div key={index}>
                    <img src={slideImage.url} />
                    <p className="legend">Legend index</p>
                  </div>
                  ))}                
              </Carousel>
            </div>
          </div>
          <div className="col-lg-6" >
            <p>{product.description}</p>
          </div>
        </div>
    </div>
  </>
    )
}

export default ProductView;