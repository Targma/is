/*
 *
 * ProductList
 *
 */

import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { TextField } from 'material-ui';
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow } from 'material-ui/Table';
import { makeSelectProducts, makeSelectSearchTitle, makeSelectPageCount, makeSelectPageNumber } from '../HomePage/selectors';
import { setSearchTitle } from '../HomePage/actions';
import Product from '../Product';


class ProductList extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function

  componentDidMount() {
    const event = { target: { value: '' } };
    this.props.onChangeSearchTitle(event);
  }

  render() {
    return (
      <div>
        <h2>Product list</h2>
        <TextField hintText="Search" type="text" value={this.props.searchTitle} onChange={this.props.onChangeSearchTitle} />
        <Table
          height={'300px'}
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
      </div>
    );
  }
}

ProductList.propTypes = {
  searchTitle: PropTypes.string,
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
  onChangeSearchTitle: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  products: makeSelectProducts(),
  searchTitle: makeSelectSearchTitle(),
  pageCount: makeSelectPageCount(),
  pageNumber: makeSelectPageNumber(),
});

function mapDispatchToProps(dispatch) {
  return {
    onChangeSearchTitle: (event) => dispatch(setSearchTitle(event.target.value)),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(ProductList);
