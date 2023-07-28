import React from 'react';

import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { IProductData } from '../types/product-type';

const ProductItem = ( props : IProductData) => {
  console.log(props);
  
 return <Card onClick={() => {
  window.location.href = `/otto/products/${props.id}`;
 }}
 style={{ width: '18rem', display: 'inline-block', margin: '5px' }}>
      <Card.Img variant="top" src={props.pictures[0].src} />
      <Card.Body>
        <Card.Title>{props.name}</Card.Title>
        <Card.Text>
          Some quick example text to build on the card title and make up the
          bulk of the card's content.
        </Card.Text>
        <Button variant="primary">Go somewhere</Button>
      </Card.Body>
    </Card>
  };



export default ProductItem;