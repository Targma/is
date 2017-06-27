import {
  LOG_IN,
  ADD_PRODUCT_IN_BASKET,
  TOGGLE_OPEN_BASKET,
  STORE_ADDRESSES,
  ADD_ADDRESSES,
  UPDATE_PRODUCT_QUANTITY_IN_BASKET,
  REMOVE_PRODUCT_IN_BASKET,
  SELECT_ADDRESSES,
  CLEAR_BASKET,
  PLACE_ORDER,
} from './constants';

export function login(user) {
  return {
    type: LOG_IN,
    user,
  };
}

export function storeAddresses(addresses) {
  return {
    type: STORE_ADDRESSES,
    addresses,
  };
}

export function addAddress(address) {
  return {
    type: ADD_ADDRESSES,
    address,
  };
}

export function selectAddress(address) {
  return {
    type: SELECT_ADDRESSES,
    address,
  };
}

export function addProductToBasket(product) {
  return {
    type: ADD_PRODUCT_IN_BASKET,
    product,
  };
}

export function clearBasket(product) {
  return {
    type: CLEAR_BASKET,
    product,
  };
}

export function toggleOpen() {
  return {
    type: TOGGLE_OPEN_BASKET,
  };
}

export function setProductQuantity(index, quantity) {
  return {
    type: UPDATE_PRODUCT_QUANTITY_IN_BASKET,
    index,
    quantity,
  };
}

export function removeProductInBasket(index) {
  return {
    type: REMOVE_PRODUCT_IN_BASKET,
    index,
  };
}

export function placeOrder() {
  return {
    type: PLACE_ORDER,
  };
}
