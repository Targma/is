import { createSelector } from 'reselect';

/**
 * Direct selector to the basket state domain
 */
const selectBasketDomain = () => (state) => state.get('basket');

/**
 * Other specific selectors
 */


/**
 * Default selector used by Basket
 */

const makeSelectBasket = () => createSelector(
  selectBasketDomain(),
  (substate) => substate.toJS()
);

export default makeSelectBasket;
export {
  selectBasketDomain,
};
