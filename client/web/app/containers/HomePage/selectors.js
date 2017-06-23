import { createSelector } from 'reselect';

/**
 * Direct selector to the productList state domain
 */
const selectProductListDomain = (state) => state.get('home');

/**
 * Other specific selectors
 */

const makeSelectProducts = () => createSelector(
  selectProductListDomain,
  (homeState) => homeState.get('products')
);

const makeSelectSearchTitle = () => createSelector(
  selectProductListDomain,
  (homeState) => homeState.get('searchTitle')
);

/**
 * Default selector used by ProductList
 */

export {
  selectProductListDomain,
  makeSelectProducts,
  makeSelectSearchTitle,
};
