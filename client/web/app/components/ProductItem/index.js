/**
*
* ProductItem
*
*/
import React, { PropTypes } from 'react';
// import styled from 'styled-components';

class ProductItem extends React.Component { // eslint-disable-line react/prefer-stateless-function

  render() {
    return (
      <li>
        <p>Title: {this.props.product.title} Discount: {this.props.product.discount}</p>
      </li>
    );
  }
}

ProductItem.propTypes = {
  product: PropTypes.shape({
    title: PropTypes.string,
    discount: PropTypes.number,
  }),
};

export default ProductItem;
