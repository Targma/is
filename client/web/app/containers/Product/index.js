/*
 *
 * Product
 *
 */
import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import FontAwesome from 'react-fontawesome';
import { TableHeaderColumn, TableRow } from 'material-ui/Table';
import { toast } from 'react-toastify';
import { addProductToBasket } from '../App/actions';

export class Product extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function

  constructor(props) {
    super(props);
    this.handleAddProduct = this.handleAddProduct.bind(this);
  }

  handleAddProduct() {
    this.props.onAddProduct(this.props.product);
    toast(`Added ${this.props.product.title} to basket.`);
  }

  render() {
    return (
      <TableRow>
        <TableHeaderColumn>{this.props.product.id}</TableHeaderColumn>
        <TableHeaderColumn>{this.props.product.title}</TableHeaderColumn>
        <TableHeaderColumn>{this.props.product.price}</TableHeaderColumn>
        <TableHeaderColumn>{this.props.product.discount}</TableHeaderColumn>
        <TableHeaderColumn>
          <FontAwesome onClick={this.handleAddProduct} name={'shopping-basket'} />
        </TableHeaderColumn>
      </TableRow>
    );
  }
}

Product.propTypes = {
  product: PropTypes.shape({
    id: PropTypes.number,
    title: PropTypes.string,
    discount: PropTypes.number,
    price: PropTypes.number,
  }),
  onAddProduct: PropTypes.func,
};


function mapDispatchToProps(dispatch) {
  return {
    onAddProduct: (event) => dispatch(addProductToBasket(event)),
  };
}

export default connect(null, mapDispatchToProps)(Product);
