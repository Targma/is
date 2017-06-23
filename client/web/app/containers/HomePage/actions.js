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
