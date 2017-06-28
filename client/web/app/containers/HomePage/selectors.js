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

const makeSelectIsCreateDialogOpen = () => createSelector(
  selectProductListDomain,
  (homeState) => homeState.get('isCreateDialogOpen')
);

const makeSelectCreateAddress = () => createSelector(
  selectProductListDomain,
  (homeState) => homeState.get('createAddress')
);

const makeSelectProductCount = () => createSelector(
  selectProductListDomain,
  (homeState) => homeState.get('productCount')
);

/**
 * Default selector used by ProductList
 */

export {
  selectProductListDomain,
  makeSelectProducts,
  makeSelectIsCreateDialogOpen,
  makeSelectCreateAddress,
  makeSelectProductCount,
};
