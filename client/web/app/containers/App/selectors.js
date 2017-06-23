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
};
