import { createSelector } from 'reselect';

const selectGlobal = (state) => state.get('global');

const makeSelectUser = () => createSelector(
  selectGlobal,
  (globalState) => globalState.get('user')
);

const makeSelectLogged = () => createSelector(
  selectGlobal,
  (globalState) => globalState.get('logged')
);

const makeSelectIsBasketOpen = () => createSelector(
  selectGlobal,
  (globalState) => globalState.get('isBasketOpen')
);

const makeSelectProductInBasket = () => createSelector(
  selectGlobal,
  (globalState) => globalState.get('productsInBasket')
);

const makeSelectSelectedAddress = () => createSelector(
  selectGlobal,
  (globalState) => globalState.get('selectedAddress')
);

const makeSelectAddresses = () => createSelector(
  selectGlobal,
  (globalState) => globalState.get('addresses')
);

const makeSelectLocationState = () => {
  let prevRoutingState;
  let prevRoutingStateJS;

  return (state) => {
    const routingState = state.get('route'); // or state.route

    if (!routingState.equals(prevRoutingState)) {
      prevRoutingState = routingState;
      prevRoutingStateJS = routingState.toJS();
    }

    return prevRoutingStateJS;
  };
};

export {
  selectGlobal,
  makeSelectUser,
  makeSelectLogged,
  makeSelectLocationState,
  makeSelectIsBasketOpen,
  makeSelectProductInBasket,
  makeSelectSelectedAddress,
  makeSelectAddresses,
};
