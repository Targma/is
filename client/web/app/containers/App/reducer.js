/*
 *
 * AppHeader reducer
 *
 */
import { fromJS } from 'immutable';
import {
  LOG_IN,
  TOGGLE_OPEN_BASKET,
  STORE_ADDRESSES,
  ADD_ADDRESSES,
  ADD_PRODUCT_IN_BASKET,
  UPDATE_PRODUCT_QUANTITY_IN_BASKET,
  REMOVE_PRODUCT_IN_BASKET,
  SELECT_ADDRESSES,
  CLEAR_BASKET,
} from './constants';

const initialState = fromJS({
  logged: false,
  user: false,
  addresses: false,
  productsInBasket: false,
  isBasketOpen: false,
  selectedAddress: false,
});

function appReducer(state = initialState, action) {
  switch (action.type) {
    case LOG_IN:
      return state
        .set('user', action.user)
        .set('logged', true);
    case TOGGLE_OPEN_BASKET:
      return state
        .set('isBasketOpen', !state.get('isBasketOpen'));
    case ADD_ADDRESSES:
      return state.set('addresses', [action.address, ...state.get('addresses')]);
    case STORE_ADDRESSES:
      return state.set('addresses', action.addresses);
    case SELECT_ADDRESSES:
      return state.set('selectedAddress', action.address);
    case REMOVE_PRODUCT_IN_BASKET:
      return state.set('productsInBasket', state.get('productsInBasket').filter((item, index) => index !== action.index));
    case ADD_PRODUCT_IN_BASKET:
      return state.set('productsInBasket', [{
        product: action.product,
        quantity: Number(1),
      }, ...state.get('productsInBasket')]);
    case UPDATE_PRODUCT_QUANTITY_IN_BASKET:
      return state.set('productsInBasket', state.get('productsInBasket').map((item, index) => {
        if (index !== action.index) {
          return item;
        }
        return Object.assign({}, item, {
          quantity: Number(action.quantity),
        });
      }));
    case CLEAR_BASKET:
      return state.set('productsInBasket', false);
    default:
      return state;
  }
}

export default appReducer;
