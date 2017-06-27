/*
 *
 * ProductList actions
 *
 */

import {
  SEARCH_CHANGED,
  SET_FETCHED_PRODUCTS,
  ERROR_FETCHING_PRODUCTS,
  FETCH_PRODUCT,
  OPEN_ADDRESS_DIALOG,
  CHANGED_ADDRESS_CITY,
  CHANGED_ADDRESS_CODE,
  CHANGED_ADDRESS_STREET,
  ADDRESS_DIALOG_CANCEL,
  ADDRESS_DIALOG_SUBMIT,
  ADDRESS_DIALOG_SUBMIT_SUCESSFUL,
} from './constants';

export function getProducts(search) {
  return {
    type: FETCH_PRODUCT,
    search,
  };
}

export function setSearchTitle(searchTitle) {
  return {
    type: SEARCH_CHANGED,
    searchTitle,
  };
}

export function setProducts(products) {
  return {
    type: SET_FETCHED_PRODUCTS,
    products,
  };
}

export function setProductsError(error) {
  return {
    type: ERROR_FETCHING_PRODUCTS,
    error,
  };
}

export function toggleAddressDialog() {
  return {
    type: OPEN_ADDRESS_DIALOG,
  };
}

export function changedAddressCity(city) {
  return {
    type: CHANGED_ADDRESS_CITY,
    city,
  };
}

export function changedAddressCode(code) {
  return {
    type: CHANGED_ADDRESS_CODE,
    code,
  };
}

export function changedAddressStreet(street) {
  return {
    type: CHANGED_ADDRESS_STREET,
    street,
  };
}

export function addressDialogCancel() {
  return {
    type: ADDRESS_DIALOG_CANCEL,
  };
}

export function addressDialogSubmit() {
  return {
    type: ADDRESS_DIALOG_SUBMIT,
  };
}

export function addressDialogSubmitSucessful() {
  return {
    type: ADDRESS_DIALOG_SUBMIT_SUCESSFUL,
  };
}
