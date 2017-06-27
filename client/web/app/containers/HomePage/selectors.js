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

const makeSelectIsCreateDialogOpen = () => createSelector(
  selectProductListDomain,
  (homeState) => homeState.get('isCreateDialogOpen')
);

const makeSelectCreateAddress = () => createSelector(
  selectProductListDomain,
  (homeState) => homeState.get('createAddress')
);

const makeSelectPageNumber = () => createSelector(
  selectProductListDomain,
  (homeState) => homeState.get('pageNumber')
);

const makeSelectPageCount = () => createSelector(
  selectProductListDomain,
  (homeState) => homeState.get('pageCount')
);

/**
 * Default selector used by ProductList
 */

export {
  selectProductListDomain,
  makeSelectProducts,
  makeSelectSearchTitle,
  makeSelectIsCreateDialogOpen,
  makeSelectCreateAddress,
  makeSelectPageCount,
  makeSelectPageNumber,
};
