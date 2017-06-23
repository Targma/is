/*
 *
 * ProductList reducer
 *
 */

import { fromJS } from 'immutable';
import {
  SEARCH_CHANGED,
  SET_FETCHED_PRODUCTS,
  ERROR_FETCHING_PRODUCTS,
} from './constants';

const initialState = fromJS({
  products: false,
  searchTitle: '',
  error: null,
});

function homePageReducer(state = initialState, action) {
  switch (action.type) {
    case ERROR_FETCHING_PRODUCTS:
      return state
        .set('error', action.error);
    case SET_FETCHED_PRODUCTS:
      return state
        .set('products', action.products);
    case SEARCH_CHANGED:
      return state
        .set('searchTitle', action.searchTitle);
    default:
      return state;
  }
}

export default homePageReducer;
