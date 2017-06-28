/*
 *
 * ProductList
 *
 */

import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { TextField, DropDownMenu, MenuItem } from 'material-ui';
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow } from 'material-ui/Table';
import { makeSelectProducts, makeSelectProductCount } from '../HomePage/selectors';
import { getProducts } from '../HomePage/actions';
import Product from '../Product';


class ProductList extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function

  constructor(props, context) {
    super(props, context);

    this.state = {
      search: '',
      page: Number(0),
    };

    this.handleChangeSearch = this.handleChangeSearch.bind(this);
    this.handleChangePage = this.handleChangePage.bind(this);
    this.fetchProducts = this.fetchProducts.bind(this);
  }

  componentDidMount() {
    this.fetchProducts(this.state);
  }

  fetchProducts(state) {
    this.props.onFetchProduct(state.search, state.page);
  }

  handleChangeSearch(event) {
    const value = event.target.value;
    this.setState((state) => Object.assign({}, state, {
      search: value,
    }), () => {
      this.fetchProducts(this.state);
    });
  }

  handleChangePage(event, index, value) {
    this.setState((state) => Object.assign({}, state, {
      page: value,
    }), () => {
      this.fetchProducts(this.state);
    });
  }

  render() {
    const pageCount = Math.ceil(this.props.productCount / 10);
    const pages = Array(...{ length: pageCount }).map((value) => value);
    return (
      <div>
        <h2>Product list</h2>
        <TextField
          hintText="Search"
          type="text"
          onChange={this.handleChangeSearch}
        />
        <Table
          height={'600px'}
          fixedHeader
          fixedFooter={false}
          selectable
          multiSelectable={false}
        >
          <TableHeader
            displaySelectAll={false}
            adjustForCheckbox={false}
            enableSelectAll={false}
          >
            <TableRow>
              <TableHeaderColumn>ID</TableHeaderColumn>
              <TableHeaderColumn>Name</TableHeaderColumn>
              <TableHeaderColumn>Price</TableHeaderColumn>
              <TableHeaderColumn>Discount</TableHeaderColumn>
              <TableHeaderColumn>Options</TableHeaderColumn>
            </TableRow>
          </TableHeader>
          <TableBody
            displayRowCheckbox={false}
            deselectOnClickaway={false}
            showRowHover
            stripedRows={false}
          >
            {
              this.props.products ?
              this.props.products.map((item, index) => (
                <Product key={index} product={item} />
              ))
              :
                <TableRow />
            }
          </TableBody>
        </Table>
        <div>
          Page
          <DropDownMenu value={this.state.page} onChange={this.handleChangePage} >
            {
              pages.map((item, i) => (
                <MenuItem key={i} value={i} primaryText={`${i + 1}`} />
              ))
            }
          </DropDownMenu>
        </div>
      </div>
    );
  }
}

ProductList.propTypes = {
  products: PropTypes.oneOfType([
    React.PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.number,
        title: PropTypes.string,
        discount: PropTypes.number,
      })
    ),
    React.PropTypes.bool,
  ]),
  productCount: PropTypes.number,
  onFetchProduct: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  products: makeSelectProducts(),
  productCount: makeSelectProductCount(),
});

function mapDispatchToProps(dispatch) {
  return {
    onFetchProduct: (search, skip) => dispatch(getProducts(search, skip)),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(ProductList);
