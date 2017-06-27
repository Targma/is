/*
 *
 * Basket
 *
 */

import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { List, ListItem } from 'material-ui/List';
import { createStructuredSelector } from 'reselect';
import { Drawer, RaisedButton, TextField, Divider, DropDownMenu, MenuItem } from 'material-ui';
import { toggleOpen, setProductQuantity, removeProductInBasket, selectAddress, placeOrder } from '../App/actions';
import { makeSelectIsBasketOpen, makeSelectProductInBasket, makeSelectSelectedAddress, makeSelectAddresses, makeSelectUser } from '../App/selectors';


class Basket extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function

  constructor(props) {
    super(props);
    this.handleQuantityChange = this.handleQuantityChange.bind(this);
    this.handleRemoveProduct = this.handleRemoveProduct.bind(this);
  }

  handleQuantityChange(evt, index) {
    this.props.onProductQuantity(index, evt.target.value);
  }

  handleRemoveProduct(evt, index) {
    this.props.onRemoveProduct(index);
  }

  calculatePriceWithDiscount(item) {
    return Number(item.product.price * (1 - (item.product.discount / 100))).toFixed(2);
  }

  calculateTotalPrice(item) {
    return Number(this.calculatePriceWithDiscount(item) * item.quantity).toFixed(2);
  }

  calculateTotalPriceBasketPrice(productsInBasket) {
    let totalPrice = Number(0);
    if (productsInBasket) {
      productsInBasket.forEach((item) => {
        totalPrice = +totalPrice + +this.calculateTotalPrice(item);
      });
    }
    return Number(totalPrice).toFixed(2);
  }

  render() {
    return (
      <div>
        <Drawer width={'30%'} openSecondary open={this.props.isBasketOpen} >
          <div>
            <h2>Basket</h2>
            <RaisedButton label={'Close'} secondary onTouchTap={this.props.onToggleOpen} />
          </div>
          <List>
            {
              this.props.productsInBasket ?
                this.props.productsInBasket.map((item, index) => (
                  <ListItem key={index}>
                    <p>Title: {item.product.title}</p>
                    <p>Unit price: {item.product.price}&euro;</p>
                    <p>Discount: {item.product.discount} %</p>
                    <p>Price with discount: {this.calculatePriceWithDiscount(item)} &euro;</p>
                    <TextField
                      id={`${index}`}
                      value={item.quantity}
                      onChange={(evt) => this.handleQuantityChange(evt, index)}
                      floatingLabelText="Quantity"
                      floatingLabelFixed
                    />
                    <p><i>Price</i>: {this.calculateTotalPrice(item)} &euro;</p>
                    <RaisedButton label={'Remove'} secondary onTouchTap={(evt) => this.handleRemoveProduct(evt, index)} />
                  </ListItem>
                ))
                : ''
            }
          </List>
          <Divider />
          <p>
            <b>Total price</b>: {this.calculateTotalPriceBasketPrice(this.props.productsInBasket)}
          </p>
          <Divider />
          <div>
            Address
            <DropDownMenu value={this.props.selectedAddress} onChange={this.props.onSelectAddress} disabled={!this.props.user}>
              {
                this.props.addresses ?
                  this.props.addresses.map((item, index) => (
                    <MenuItem key={index} value={index} primaryText={`${item.street} ${item.city} ${item.code}`} />
                  ))
                  : ''
              }
            </DropDownMenu>
          </div>
          {
            (this.props.user &&
            this.props.selectedAddress !== false &&
            this.props.productsInBasket &&
            this.props.productsInBasket.length > 0) ?
              <div><RaisedButton label={'Place order'} primary onTouchTap={this.props.onPlaceOrder} /></div>
              : ''
          }
        </Drawer>
      </div>
    );
  }
}

Basket.propTypes = {
  isBasketOpen: PropTypes.bool,
  user: PropTypes.oneOfType([
    PropTypes.bool,
    PropTypes.object,
  ]),
  productsInBasket: PropTypes.oneOfType([
    PropTypes.bool,
    PropTypes.array,
    PropTypes.arrayOf(
      PropTypes.shape({
        quantity: PropTypes.number,
        product: PropTypes.shape({
          id: PropTypes.number,
          title: PropTypes.string,
          discount: PropTypes.number,
        }),
      })
    ),
  ]),
  addresses: PropTypes.oneOfType([
    PropTypes.bool,
    PropTypes.array,
  ]),
  selectedAddress: PropTypes.oneOfType([
    PropTypes.bool,
    PropTypes.number,
  ]),
  onToggleOpen: PropTypes.func,
  onProductQuantity: PropTypes.func,
  onRemoveProduct: PropTypes.func,
  onSelectAddress: PropTypes.func,
  onPlaceOrder: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  isBasketOpen: makeSelectIsBasketOpen(),
  productsInBasket: makeSelectProductInBasket(),
  selectedAddress: makeSelectSelectedAddress(),
  addresses: makeSelectAddresses(),
  user: makeSelectUser(),
});

function mapDispatchToProps(dispatch) {
  return {
    onToggleOpen: () => dispatch(toggleOpen()),
    onProductQuantity: (index, quantity) => dispatch(setProductQuantity(index, quantity)),
    onRemoveProduct: (index) => dispatch(removeProductInBasket(index)),
    onSelectAddress: (event, index, value) => dispatch(selectAddress(value)),
    onPlaceOrder: () => dispatch(placeOrder()),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(Basket);
