/*
 *
 * ProductList
 *
 */

import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { makeSelectProducts, makeSelectSearchTitle } from '../HomePage/selectors';
import { setSearchTitle } from '../HomePage/actions';
import ProductItem from '../../components/ProductItem/index';

class ProductList extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function

  render() {
    console.log(this.props);
    let content = (<div></div>);
    if (this.props.products) {
      content = this.props.products.map((item, index) => (
        <ProductItem key={`item-${index}`} product={item} />
      ));
    }
    return (
      <div>
        <input type="text" value={this.props.searchTitle} onChange={this.props.onChangeSearchTitle} />
        <ul>
          { content }
        </ul>
      </div>
    );
  }
}

ProductList.propTypes = {
  searchTitle: PropTypes.string,
  products: PropTypes.oneOfType([
    React.PropTypes.array,
    React.PropTypes.bool,
  ]),
  onChangeSearchTitle: PropTypes.func,
  fetchProducts: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  products: makeSelectProducts(),
  searchTitle: makeSelectSearchTitle(),
});

function mapDispatchToProps(dispatch) {
  return {
    onChangeSearchTitle: (event) => dispatch(setSearchTitle(event.target.value)),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(ProductList);
